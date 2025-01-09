import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
public class WordProcessor {
    Scanner scan = new Scanner(System.in);
    Pattern pattern;
    Matcher matcher;

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
     * This is the method for the chatbot to identify keywords that abide by regex Identifiers in the text file
     * @return flags: List of flags to refer to in responses text file
     */
    @SuppressWarnings("static-access")
    public List<Integer> processInput() {
        String value = scan.nextLine();
        List<Integer> flags = new ArrayList<Integer>();
        boolean valid;
        int counter = 0;
    
        pattern.compile(value, Pattern.CASE_INSENSITIVE);

        try {
        File file = new File("TextFiles/flags.txt");
        Scanner flagReader = new Scanner(file);
        String line; 
        while(flagReader.hasNext()) {
            line = flagReader.next();
            pattern.matcher(line);
            valid = matcher.find();
            counter++;
            if(valid) {
                flags.add(counter);
            }
        }

        flagReader.close();

        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }

        return flags;
    }

}
