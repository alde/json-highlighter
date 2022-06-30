package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public class NullToken implements Token<Void> {

  @Override
  public Void value() {
    return null;
  }

  @Override
  public TokenType type() {
    return TokenType.NULL;
  }

  @Override
  public String colorize() {
    return AnsiColor.RED + "null" + AnsiColor.RESET;
  }
}
