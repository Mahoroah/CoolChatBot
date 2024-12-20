import java.util.Scanner;

public class CommandLineOutput implements OutputInterface {
  private Scanner scanner = new Scanner(System.in);
  
  public CommandLineOutput() {
    
  }
  
  @Override
  public String takeInput() {
    System.out.print("User: ");
    return scanner.nextLine();
  }

  @Override
  public void putOutput(String response) {
    System.out.print("Chatbot: ");
    System.out.println(response);
  }
}
