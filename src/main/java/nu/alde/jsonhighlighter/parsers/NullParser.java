package nu.alde.jsonhighlighter.parsers;

import java.io.PushbackReader;
import nu.alde.jsonhighlighter.TokenizationException;

public class NullParser extends AbstractParser<String> {

  private final CharSequence seq;

  public NullParser(PushbackReader reader, CharSequence seq) {
    this.reader = reader;
    this.seq = seq;
  }

  @Override
  public String parse() {
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

    return "null";
  }
}
