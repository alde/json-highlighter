package nu.alde.jsonhighlighter.parsers;

import java.io.PushbackReader;
import java.math.BigDecimal;
import nu.alde.jsonhighlighter.TokenizationException;

public class NumberParser extends AbstractParser {

  private final StringBuilder beforePoint = new StringBuilder();
  private final StringBuilder afterPoint = new StringBuilder();
  private final StringBuilder power = new StringBuilder();
  private int ch;
  private boolean negative;
  private boolean negativePower;

  public NumberParser(PushbackReader reader) {
    this.reader = reader;
  }

  @Override
  public BigDecimal parse() {
    ch = next();
    if (ch == '-') {
      negative = true;
    } else {
      pushBack(ch);
    }

    afterSign();

    return constructNumber();
  }

  private void afterSign() {
    ch = next();
    if (ch >= '0' && ch <= '9') {
      beforePoint.append((char) ch);
      digitsBeforePoint();
    } else {
      throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }
  }

  private void digitsBeforePoint() {
    while (true) {
      ch = next();
      if (ch >= '0' && ch <= '9') {
        beforePoint.append((char) ch);
      } else {
        break;
      }
    }

    if (ch == '.') {
      digitsAfterPoint();
    } else {
      pushBack(ch);
      beforeExponent();
    }
  }

  private void digitsAfterPoint() {
    boolean found = false;
    while (true) {
      ch = next();
      if (ch >= '0' && ch <= '9') {
        found = true;
        afterPoint.append((char) ch);
      } else {
        break;
      }
    }

    if (found) {
      pushBack(ch);
      beforeExponent();
    } else {
      throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }
  }

  private void beforeExponent() {
    ch = next();
    if (ch == 'e' || ch == 'E') {
      afterExponent();
    } else {
      pushBack(ch);
    }
  }

  private void afterExponent() {
    ch = next();
    if (ch == '-') {
      negativePower = true;
    } else if (ch == '+') {
      negativePower = false;
    } else if (ch >= '0' && ch <= '9') {
      pushBack(ch);
    } else {
      throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }

    powerDigits();
  }

  private void powerDigits() {
    boolean found = false;
    while (true) {
      ch = next();
      if (ch >= '0' && ch <= '9') {
        found = true;
        power.append((char) ch);
      } else {
        break;
      }
    }

    if (found) {
      pushBack(ch);
    } else {
      throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }
  }

  private BigDecimal constructNumber() {
    StringBuilder num = new StringBuilder();

    if (negative) {
      num.append('-');
    }
    num.append(beforePoint);

    if (afterPoint.length() > 0) {
      num.append('.');
      num.append(afterPoint);
    }

    if (power.length() > 0) {
      num.append('e');
      if (negativePower) {
        num.append('-');
      }
      num.append(power);
    }

    if (num.isEmpty()) {
      num.append("0");
    }
    return new BigDecimal(num.toString());
  }
}