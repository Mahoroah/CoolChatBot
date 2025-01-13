import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;



public class WordProcessor {
    ArrayList<WordPattern> patterns = new ArrayList<>();

    /**
     * constructs a new WordProcessor.
     * reads "flags" from `TextFiles/flags.txt`
     * and reads responses from `TextFiles/response.txt`.
     * they should be paired up
     * 
     * @see WordPattern
     */
    public WordProcessor() throws FileNotFoundException {
        File flagFile = new File("TextFiles/flags.txt");
        Scanner flagReader = new Scanner(flagFile);
        File responseFile = new File("TextFiles/responses.txt");
        Scanner responseReader = new Scanner(responseFile);

        ArrayList<String> flags = new ArrayList<>();
        ArrayList<String> responses = new ArrayList<>();

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

        for (int i=0; i < flags.size(); i++) {
            System.out.printf("%s => %s\n", flags.get(i), responses.get(i));
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
