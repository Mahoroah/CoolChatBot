import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * this class starts an http server in a new thread,
 * and when asked for input, will block until input is recieved
 * from the web server.
 */
public class WebsiteOutput implements OutputInterface {
    private HttpServer server;
    private static final String html;
    private static volatile String latestInput = null;
    private static volatile String latestOutput = null;

    static {
      try {
        html = Files.readString(Paths.get("./website/main.html"), StandardCharsets.UTF_8);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * creates a new web server, bound to localhost:8000.
     * the route "/main" returns the main html page.
     * the route "/respond" responds with the chatbot output.
     */
    public WebsiteOutput() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8080), 0);
        this.server.createContext("/main", new MainHandler());
        this.server.createContext("/respond", new ResponseHandler());
        this.server.setExecutor(null); // creates a default executor
        this.server.start();
    }

    @Override
    public String takeInput() {
      while (latestInput == null) {}

      String input = latestInput;
      latestInput = null;

      return input;
    }

    @Override
    public void putOutput(String output) {
      latestOutput = output;
    }

    static class MainHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = html;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ResponseHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            latestInput =
              new BufferedReader(
              new InputStreamReader(
              t.getRequestBody()))
              .lines()
              .collect(Collectors.joining("\n"));

            while (latestOutput == null) {}

            String response = latestOutput;
            latestOutput = null;

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
