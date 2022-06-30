package nu.alde.jsonhighlighter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Objects;
import org.junit.jupiter.api.Test;

class ColorizerTest {

  @Test
  public void colorize() throws IOException {
    String actual = Colorizer.colorize("{\n"
        + "  \"Name\": \"Test\",\n"
        + "  \"Mobile\": 12345678,\n"
        + "  \"Boolean\": true,\n"
        + "  \"Pets\": [\"Dog\", \"cat\"],\n"
        + "  \"Address\": {\n"
        + "    \"Permanent address\": \"USA\",\n"
        + "   \"current Address\": \"AU\"\n"
        + "  }\n"
        + "}");
    System.out.println(actual);
    assertThat(actual).startsWith("\u001B[33m{");
  }

  @Test
  public void colorize_from_file() {
    final String actual = Colorizer.colorize(getFixture("sample.json"));
    System.out.println(actual);
    assertThat(actual).startsWith("\u001B[33m[");
  }

  @Test
  public void colorize_from_file_two() {
    final String actual = Colorizer.colorize(getFixture("sample_two.json"));
    System.out.println(actual);
    assertThat(actual).startsWith("\u001B[33m{");
  }

  private Path getFixture(final String fixture) {
    try {
      return Path.of(
          Objects.requireNonNull(getClass().getClassLoader().getResource(fixture)).toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}