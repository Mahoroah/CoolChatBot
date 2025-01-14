import java.io.FileNotFoundException;

 /*
   1) It should take input from the user...some how.
   2) It must make a decision based on your input and provide a response.
   4) It should uses classes, objects, multiple data types, private instance variables, etc.
   5) Good programming etiquette.
   6) YOU MUST USE GITHUB and document your use as a team
   7) You must be able to provide proof of your planning and group management/conversations/conflict resolution
   8) You must give a presentation/demonstration of your project during finals (Professional Dress is required to get credit for the presentation)
   9) you must comment your code using javadoc comments AND create the javadoc files, packaged in a nice folder :)
   3) It should read from a text file in your program
*/

public class Main {
    public static void main(String[] args) {
      try {
        OutputInterface io = new WebsiteOutput();
        WordProcessor processor = new WordProcessor();

        System.out.println();
        System.out.println("open up localhost:8000/main in your browser");

        while (true) {
          String input = io.takeInput();
          String output = processor.tryRecognizeAll(input);

          if (output == null) {
            io.putOutput("erm what the sigma");
          } else {
            io.putOutput(output);
          }
        }
      } catch (Exception e) {
        System.err.println(e);
        System.exit(1);
      }
    }
}
