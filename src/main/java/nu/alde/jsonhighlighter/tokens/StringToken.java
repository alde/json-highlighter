package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public record StringToken(String value) implements Token<String> {

  @Override
  public TokenType type() {
    return TokenType.STRING;
  }

  @Override
  public String prefix() {
    return AnsiColor.BLUE + "\"" + AnsiColor.CYAN;
  }

  @Override
  public String suffix() {
    return AnsiColor.BLUE + "\"" + AnsiColor.RESET;
  }
}
