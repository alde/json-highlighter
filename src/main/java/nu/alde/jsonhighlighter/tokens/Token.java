package nu.alde.jsonhighlighter.tokens;

import nu.alde.jsonhighlighter.AnsiColor;
import nu.alde.jsonhighlighter.TokenType;

public interface Token<T> {

  ObjectStartToken OBJECT_START = new ObjectStartToken();

  ObjectEndToken OBJECT_END = new ObjectEndToken();

  ArrayStartToken ARRAY_START = new ArrayStartToken();

  ArrayEndToken ARRAY_END = new ArrayEndToken();

  NullToken NULL = new NullToken();

  CommaToken COMMA = new CommaToken();

  ColonToken COLON = new ColonToken();

  EndOfFileToken EOF = new EndOfFileToken();

  T value();

  TokenType type();

  default String prefix() {
    return AnsiColor.YELLOW.toString();
  }

  default String suffix() {
    return AnsiColor.RESET.toString();
  }

  default String colorize() {
    return prefix() + value().toString() + suffix();
  }

  ;
}
