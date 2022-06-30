package nu.alde.jsonhighlighter.parsers;

import java.io.PushbackReader;
import nu.alde.jsonhighlighter.TokenizationException;

public class BooleanParser extends AbstractParser<Boolean> {

  private CharSequence seq;

  public BooleanParser(PushbackReader reader, CharSequence seq) {
    this.seq = seq;
    this.reader = reader;
  }

  @Override
  public Boolean parse() {
    int index = 0;
    int remaining = seq.length();

    while (remaining > 0) {
      int readValue = next();
      if (readValue == seq.charAt(index)) {
        remaining = remaining - 1;
        index = index + 1;
      } else {
        throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
      }
    }

    return seq == "true";
  }
}
