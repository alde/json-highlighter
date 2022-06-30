package nu.alde.jsonhighlighter.parsers;

import java.io.IOException;
import java.io.PushbackReader;
import nu.alde.jsonhighlighter.TokenizationException;

public abstract class AbstractParser<T> {

  public static final String ERR_UNEXPECTED_READ = "Unexpected read error";
  public static final String ERR_UNEXPECTED_UNREAD = "Unexpected unread error";
  public static final String ERR_UNEXPECTED_SYMBOL = "Unexpected symbol";
  PushbackReader reader;

  public abstract T parse();

  int next() {
    try {
      return reader.read();
    } catch (IOException e) {
      throw new TokenizationException(ERR_UNEXPECTED_READ, e);
    }
  }

  void pushBack(int value) {
    if (value == -1) {
      return;
    }
    try {
      reader.unread(value);
    } catch (IOException e) {
      throw new TokenizationException(ERR_UNEXPECTED_UNREAD, e);
    }
  }
}
