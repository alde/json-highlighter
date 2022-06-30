package nu.alde.jsonhighlighter;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import nu.alde.jsonhighlighter.tokens.Token;

public class Colorizer {

  public static String colorize(Path file) {
    try {
      return colorize(Files.readString(file));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static String colorize(String input) {
    List<Token<?>> tokens = tokens(input);
    StringBuilder result = new StringBuilder();
    AtomicInteger indent = new AtomicInteger();
    tokens.forEach(t -> {
      switch (t.type()) {
        case OBJECT_START, ARRAY_START -> result.append(t.colorize())
            .append('\n')
            .append(spacing(indent.addAndGet(2)));
        case OBJECT_END, ARRAY_END -> result
            .append('\n')
            .append(spacing(indent.addAndGet(-2)))
            .append(t.colorize());
        case COMMA -> result
            .append(t.colorize())
            .append('\n').append(spacing(indent.get()));
        case COLON, STRING, NUMBER, NULL, FALSE, TRUE, EOF -> result.append(t.colorize());
      }
    });

    return result.toString();
  }

  private static String spacing(int spaces) {
    return "\s".repeat(Math.max(0, spaces));
  }

  private static List<Token<?>> tokens(String input) {
    PushbackReader reader = new PushbackReader(new StringReader(input));
    Tokenizer tokenizer = new Tokenizer(reader);
    ArrayList<Token<?>> result = tokenizer.tokenize();

    try {
      reader.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

    return Collections.unmodifiableList(result);
  }
}