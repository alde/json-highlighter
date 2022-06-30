package nu.alde.jsonhighlighter.tokens;

import java.math.BigDecimal;
import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public record NumberToken(BigDecimal value) implements Token<BigDecimal> {

  @Override
  public TokenType type() {
    return TokenType.NUMBER;
  }

  @Override
  public String prefix() {
    return AnsiColor.PURPLE.toString();
  }
}
