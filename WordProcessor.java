import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
public class WordProcessor {
    ArrayList<WordPattern> patterns;

    public WordProcessor() {
        File flagFile = new File("TextFiles/flags.txt");
        Scanner flagReader = new Scanner(flagFile);
        File responseFile = new File("TextFiles/response.txt");
        Scanner responseReader = new Scanner(responseReader);

        ArrayList<String> flags;
        ArrayList<String> responses;

        // read flag reader into an array
        while (flagReader.hasNext()) {
            String line = flagReader.nextLine();
            flags.add(line);
        }

        while (responseReader.hasNext()) {
            String line = responseReader.nextLine();
            responses.add(line);
        }

        if (flags.size() != responses.size()) {
            System.err.println("you suck");
            System.exit(1);
        }

        for (i=0; i < flags.size(); i++) {
            patterns.add(new WordPattern(flags.get(i), responses.get(i)));
        }
    }

    /**
     * tries to get the response of the first matching pattern in the
     * patterns list.
     * 
     * @param sentence the sentence to match on
     * @return formatted response, or null if didn't match any
     */
    public String tryRecognizeAll(String sentence) {
        for (WordPattern pattern : this.patterns) {
            String patResponse = pattern.recognize(sentence);
            if (patResponse != null) {
                return patResponse;
            }
        }

        return null;
    }
}
