package nu.alde.jsonhighlighter;

import static nu.alde.jsonhighlighter.parsers.AbstractParser.ERR_UNEXPECTED_READ;
import static nu.alde.jsonhighlighter.parsers.AbstractParser.ERR_UNEXPECTED_SYMBOL;
import static nu.alde.jsonhighlighter.parsers.AbstractParser.ERR_UNEXPECTED_UNREAD;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import nu.alde.jsonhighlighter.parsers.BooleanParser;
import nu.alde.jsonhighlighter.parsers.NullParser;
import nu.alde.jsonhighlighter.parsers.NumberParser;
import nu.alde.jsonhighlighter.parsers.StringParser;
import nu.alde.jsonhighlighter.tokens.BooleanToken;
import nu.alde.jsonhighlighter.tokens.NumberToken;
import nu.alde.jsonhighlighter.tokens.StringToken;
import nu.alde.jsonhighlighter.tokens.Token;

public record Tokenizer(PushbackReader reader) {

  public ArrayList<Token<?>> tokenize() {
    ArrayList<Token<?>> result = new ArrayList<>();
    Token<?> tok;
    while ((tok = nextToken()) != Token.EOF) {
      result.add(tok);
    }
    result.add(tok);
    return result;
  }


  private Token<?> nextToken() {
    removeWhitespaces();

    int ch = next();

    switch (ch) {
      case -1:
        return Token.EOF;
      case '[':
        return Token.ARRAY_START;
      case ']':
        return Token.ARRAY_END;
      case '{':
        return Token.OBJECT_START;
      case '}':
        return Token.OBJECT_END;
      case ',':
        return Token.COMMA;
      case ':':
        return Token.COLON;
      case '"':
        pushBack(ch);
        return new StringToken(new StringParser(reader).parse());
      case 't':
        pushBack(ch);
        return new BooleanToken(new BooleanParser(reader, "true").parse());
      case 'f':
        pushBack(ch);
        return new BooleanToken(new BooleanParser(reader, "false").parse());
      case 'n':
        pushBack(ch);
        new NullParser(reader, "null").parse();
        return Token.NULL;
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        pushBack(ch);
        return new NumberToken(new NumberParser(reader).parse());
      default:
        throw new TokenizationException(ERR_UNEXPECTED_SYMBOL);
    }
  }

  private void removeWhitespaces() {
    int character;
    while (true) {
      character = next();
      switch (character) {
        case ' ':
        case '\t':
        case '\r':
        case '\n':
          continue;
        default:
          pushBack(character);
          return;
      }
    }
  }

  private int next() {
    try {
      return reader.read();
    } catch (IOException e) {
      throw new TokenizationException(ERR_UNEXPECTED_READ, e);
    }
  }

  private void pushBack(int value) {
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
