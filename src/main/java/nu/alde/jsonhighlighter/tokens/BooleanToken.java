package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public record BooleanToken(Boolean value) implements Token<Boolean> {

  @Override
  public TokenType type() {
    return value ? TokenType.TRUE : TokenType.FALSE;
  }

  @Override
  public String prefix() {
    return value ? AnsiColor.GREEN.toString() : AnsiColor.RED.toString();
  }
}
