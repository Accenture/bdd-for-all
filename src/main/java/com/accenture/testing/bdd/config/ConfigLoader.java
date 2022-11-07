package com.accenture.testing.bdd.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.OverrideCombiner;

@Slf4j
@ToString
public class ConfigLoader {

  /**
   * system property (non-spring)
   */
  static final String[] USER_DEFINED_PATHS = {
      "bddforall.config",
      "spring.config.additional-location"
  };

  /**
   * classpath based file
   */
  static final String CLASSPATH_START = "classpath:";

  /**
   * default spring paths
   */
  static final String[] DEF_FILE_PATHS = {
      "config",
      "",
      CLASSPATH_START + "config" + File.separator,
      CLASSPATH_START
  };

  /**
   * default extensions to look for
   */
  static final String[] DEF_EXTENSIONS = {
      "properties",
      "yml",
      "yaml"
  };

  /**
   * load the configuration from all potential sources
   * @return
   */
  final CombinedConfiguration loadConfiguration() {
    CombinedConfiguration config = new CombinedConfiguration(new OverrideCombiner());
    getPaths().forEach(path -> loadConfig(path, config));
    return config;
  }

  /**
   * mimics spring path configuration loading
   * @link https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files
   * @return list of potential paths (files and directories) to look for configuration
   */
  final List<String> getPaths() {

    // defaults
    List<String> ret = Arrays.stream(DEF_FILE_PATHS)
        .flatMap(path -> {
          List<String> paths = getPaths(path);
          log.debug("Searching Path: {} ", paths);
          return getPaths(path).stream();
        })
        .collect(Collectors.toList());

    // user provided
    Arrays.stream(USER_DEFINED_PATHS).forEach(name -> {

      String xtra = System.getProperty(name);
      if (xtra != null) {
        Arrays.stream(xtra
            .split(","))
            .forEach(str -> {
              log.debug("Searching Custom Path: {} ", str);
              List<String> adtl = getPaths(str);
              if (!adtl.isEmpty()) ret.addAll(adtl);
            });
      }

    });

    return ret;
  }

  /**
   * get a list of valid configuration files based on a given location
   * @param pathStr the file or directory
   * @return any matching configurations
   */
   final List<String> getPaths(String pathStr) {

    String actual = getFilePath(pathStr);
    File f = actual == null ? null : new File(actual);

    log.debug("BDD For All attempting to load config(s) from {}", pathStr);

    if (f == null || !f.exists()) {
      log.debug("Configuration not found at {}", f != null ? f.getAbsolutePath() : pathStr);
      return new ArrayList<String>();
    }

    if (f.isFile()) {
      log.info("BDD For All configuration found at {}", f.getAbsolutePath());
      return Collections.singletonList(f.getAbsolutePath());
    }

    log.debug("{} is a directory, scanning for configs", f.getAbsolutePath());

    return Arrays.stream(DEF_EXTENSIONS)
        .flatMap(ext -> {
          String fPath = f.getAbsolutePath().concat(File.separator).concat(getConfigName()).concat(".").concat(ext);
          log.debug("Attempting to discover {}", fPath);
          return getPaths(fPath).stream();
        })
        .collect(Collectors.toList());
  }

  /**
   * return the actual path of the resource
   * @param path input path to be discovered
   * @return actual resource path
   */
  @Nullable
  final String getFilePath(String path) {

    if (path.startsWith(CLASSPATH_START)) {
      Class clz = ConfigLoader.class;
      ClassLoader cl = clz.getClassLoader();
      String loc = path.replaceAll(CLASSPATH_START, "");
      URL url = cl.getResource(loc);
      log.debug("Found {} in classpath", url != null ? url.getFile() : path);
      return url != null ? url.getFile() : null;
    }

    return new File(path).getAbsolutePath();
  }

  /**
   * the name of the configuration file minus the extension
   * @return the name of the configuration file minus the extension
   */
  final String getConfigName() {
    return System.getProperty("spring.config.name", "application");
  }

  /**
   * load the actual configuration each load will override the next
   * @param path
   */
  final void loadConfig(String path, CombinedConfiguration config) {

    FileBasedConfiguration configuration = isYAML(path) ? new YAMLConfiguration() : new PropertiesConfiguration();
    try (InputStreamReader reader =
             new InputStreamReader(
                 Files.newInputStream(Paths.get(path)),
                 StandardCharsets.UTF_8)) {
      configuration.read(reader);
      config.addConfiguration(configuration);
      log.info("Loaded configuration for {}", path);
    }
    catch (ConfigurationException ce) {
      log.error("Couldn't load the configuration {}", path, ce);
    }
    catch (FileNotFoundException e) {
      log.error("Couldn't find the configuration file {}", path, e);
    }
    catch(IOException ex) {
      log.error("Issue closing the file {}", path, ex);
    }

  }

  /**
   * determines if it's a yaml file by extension
   * @param path the file path or name
   * @return whether or not it's a yaml file (by extension only)
   */
  final boolean isYAML(String path) {
    return path.endsWith("yaml") || path.endsWith("yml");
  }

}
