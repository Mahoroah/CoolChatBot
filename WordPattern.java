import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPattern {
  public final Pattern pattern;
  public final String response;

  /**
   * constructs a new WordPattern.
   * 
   * @param pattern the regex pattern to use. compiled using the java regex library.
   * @param response the response string template. $N means the Nth capturing group will be substituted there.
   */
  public WordPattern(String pattern, String response) {
    this.pattern = Pattern.compile(pattern);
    this.response = response;
  }

  /** tests if a char is a number. */
  private boolean isNumeric(char c) {
    return '0' <= c && c <= '9';
  }

  /** converts a char to an int. */
  private int intOf(char c) {
    return (int) c - (int) '0';
  }

  /**
   * substitutes the template string using a set of capturing groups
   * 
   * @param template the string template to use. $N means the Nth capturing group will be substituted there.
   * @param groups the capturing groups to use as substitutions.
   * @return the templated string
   */
  private String substitute(String template, String[] groups) {
    String output = "";

    for (int i=0; i < template.length(); i++) {
      char c = template.charAt(i);
      char next;

      if (i + 1 < template.length()) { output += c; continue; }
      else { next = template.charAt(i + 1); }

      // if escaping, push and skip the next character
      // if dollar sign with number, insert group and skip next character

      if (c == '\\') { output += next; i++; }
      else if (c == '$' && isNumeric(next)) { output += groups[intOf(next)]; i++; }
      else { output += c; }
    }

    return output;
  }

  /**
   * checks if this pattern matches, and if it does, returns the
   * formatted response.
   * 
   * @param sentence the sentence to match
   * @return the response, formatted with info from the match. if doesn't match, null.
   */
  public String recognize(String sentence) {
    Matcher matcher = pattern.matcher(sentence);
    if (!matcher.find()) { return null; }

    String[] groups = new String[matcher.groupCount()];

    for (int i=0; i < groups.length; i++) {
      groups[i] = matcher.group(i);
    }

    return substitute(response, groups);
  }
}
