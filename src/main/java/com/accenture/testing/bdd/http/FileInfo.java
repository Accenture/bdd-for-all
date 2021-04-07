package com.accenture.testing.bdd.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@EqualsAndHashCode
public class FileInfo {

  String mediaType;
  String file;

  public static final FileInfo newInstance(String mediaType, String filePath) {
    Path path = Paths.get(filePath);
    log.info("Reading file: {}", path.toAbsolutePath());
    return new FileInfo(mediaType, path.toAbsolutePath().toString());
  }

  /**
   * get the file contents
   * @return the contexts of the file
   * @throws IOException when not found
   */
  public String getFileBody() throws IOException {
    return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8).stream().collect(Collectors.joining());
  }

}
