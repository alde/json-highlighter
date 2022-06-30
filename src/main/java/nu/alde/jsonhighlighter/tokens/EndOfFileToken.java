package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.TokenType;

public class EndOfFileToken implements Token<Void> {

  @Override
  public Void value() {
    return null;
  }

  @Override
  public TokenType type() {
    return TokenType.EOF;
  }

  @Override
  public String colorize() {
    return "\n";
  }
}
