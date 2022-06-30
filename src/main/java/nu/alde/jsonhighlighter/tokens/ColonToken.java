package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public class ColonToken implements Token<String> {

  @Override
  public String value() {
    return ":";
  }

  @Override
  public TokenType type() {
    return TokenType.COLON;
  }

  @Override
  public String suffix() {
    return " " + AnsiColor.RESET;
  }
}
