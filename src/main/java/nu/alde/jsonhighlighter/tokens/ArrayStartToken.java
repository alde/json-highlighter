package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.TokenType;

public class ArrayStartToken implements Token<String> {

  @Override
  public String value() {
    return "[";
  }

  @Override
  public TokenType type() {
    return TokenType.ARRAY_START;
  }
}
