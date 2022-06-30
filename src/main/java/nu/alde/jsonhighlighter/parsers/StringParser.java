package nu.alde.jsonhighlighter.parsers;

import java.io.PushbackReader;
import nu.alde.jsonhighlighter.TokenizationException;

public class StringParser extends AbstractParser<String> {

  public static final int ASCII_SUPPORTED_CHAR_START = 0x20;
  public static final int ASCII_SUPPORTED_CHAR_END = 0x7e;

  public StringParser(PushbackReader reader) {
    this.reader = reader;
  }

  @Override
  public String parse() {
    int read = next();
    if (read != '"') {
      throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }

    StringBuilder sb = new StringBuilder();

    boolean done = false;
    boolean escaped = false;

    int character;
    while (true) {
      character = next();

      if (character < ASCII_SUPPORTED_CHAR_START || character > ASCII_SUPPORTED_CHAR_END) {
        throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
      }

      if (escaped) {
        switch (character) {
          case 'b':
            character = '\b';
            break;
          case 'f':
            character = '\f';
            break;
          case 'n':
            character = '\n';
            break;
          case 'r':
            character = '\r';
            break;
          case 't':
            character = '\t';
            break;
          case 'u':
            character = expectUnicode();
            break;
          case '"':
          case '\\':
          case '/':
            break;
          default:
            throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
        }
        escaped = false;
      } else if (character == '\\') {
        escaped = true;
        continue;
      } else if (character == '"') {
        done = true;
      }

      if (done) {
        break;
      } else {
        sb.append((char) character);
      }
    }

    return sb.toString();
  }


  private int expectUnicode() {
    int remaining = 4;
    int value = 0;

    int ch;
    while (remaining > 0) {
      value = value * 16;

      ch = next();
      if (ch >= '0' && ch <= '9') {
        value = value + (ch - '0');
      } else if (ch >= 'a' && ch <= 'f') {
        value = value + 10 + (ch - 'a');
      } else if (ch >= 'A' && ch <= 'F') {
        value = value + 10 + (ch - 'A');
      } else {
        throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
      }

      remaining = remaining - 1;
    }

    return value;
  }
}
