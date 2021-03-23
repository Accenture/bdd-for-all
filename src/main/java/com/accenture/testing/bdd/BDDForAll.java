package com.accenture.testing.bdd;

import com.accenture.testing.bdd.config.BDDConfig;
import com.accenture.testing.bdd.cucumber.BDDEventListener;
import io.cucumber.core.options.CommandlineOptionsParser;
import io.cucumber.core.options.Constants;
import io.cucumber.core.options.CucumberProperties;
import io.cucumber.core.options.CucumberPropertiesParser;
import io.cucumber.core.options.RuntimeOptions;
import io.cucumber.core.runtime.Runtime;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

@Data
@Slf4j
public class BDDForAll {

  private static final HierarchicalConfiguration<ImmutableNode> config = BDDConfig.getConfig();

  /**
   * lazy way to support all cuke props
   */
  static List<String> CUKE_PROPS;
  static {
    CUKE_PROPS = Arrays.stream(Constants.class.getDeclaredFields())
        .filter(field ->
            Modifier.isPublic(field.getModifiers()) &&
                Modifier.isStatic(field.getModifiers()))
        .map(field -> {
          try {
            return (String)field.get(Constants.class);
          }
          catch (IllegalAccessException ex) {
            log.error("Trying to access cucumber property", ex);
          }
          return null;
        })
        .collect(Collectors.toList());
  }

  BDDEventListener eventListener;
  byte exitStatus;

  /**
   * simple set up
   */
  public BDDForAll() {
    eventListener = new BDDEventListener();
  }

  /**
   * get the default runtime options for BDD for All
   * @param  overrides any overrides to the config file
   * @return the default options for the execution
   */
  public RuntimeOptions getDefaultOptions(Map<String,String> overrides) {

    // create a map from the props
    Map<String,String> bddStuff = CUKE_PROPS
        .stream()
        .filter(config::containsKey)
        .collect(
            Collectors.toMap(
                e -> e,
                config::getString
            )
        );

    if (overrides != null) bddStuff.putAll(overrides);

    RuntimeOptions bddConfig = new CucumberPropertiesParser().parse(bddStuff).build();
    RuntimeOptions propertiesFileOptions = new CucumberPropertiesParser().parse(CucumberProperties.fromPropertiesFile()).build(bddConfig);
    RuntimeOptions environmentOptions = new CucumberPropertiesParser().parse(CucumberProperties.fromEnvironment()).build(propertiesFileOptions);

    return new CucumberPropertiesParser()
        .parse(CucumberProperties.fromSystemProperties())
        .build(environmentOptions);
  }

  /**
   * get the default class loader for this implementation
   * @return default classloader
   */
  @Nullable
  public ClassLoader getDefaultLoader() {
    return this.getClass().getClassLoader();
  }

  /**
   * run based on default configuration
   */
  public void run() {
    run(getDefaultOptions(null));
  }

  /**
   * run based on default configuration with
   * any overrides wanted
   * @param overrides the overrides to the default configuration
   */
  public void run(Map<String, String> overrides) {
    run(getDefaultOptions(overrides));
  }

  /**
   * run based on default config + command line arguments
   * @param  args a list of command line arguments passed for running
   */
  public void run(Collection<String> args) {

    CommandlineOptionsParser commandlineOptionsParser = new CommandlineOptionsParser(System.out);
    RuntimeOptions runtimeOptions = commandlineOptionsParser.parse(args.toArray(new String[0]))
        .addDefaultGlueIfAbsent()
        .addDefaultFeaturePathIfAbsent()
        .addDefaultFormatterIfAbsent()
        .addDefaultSummaryPrinterIfAbsent()
        .enablePublishPlugin()
        .addDefaultSummaryPrinterIfAbsent()
        .build(getDefaultOptions(null));

    commandlineOptionsParser.exitStatus()
        .map(Optional::of)
        .orElseGet(() -> {
          run(runtimeOptions);
          return Optional.of(getExitStatus());
        });
  }

  /**
   * actually execute the runner
   * @param options the runtime options for the runner
   */
  public void run(RuntimeOptions options) {
    Runtime runtime = Runtime.builder()
        .withRuntimeOptions(options)
        .withAdditionalPlugins(getEventListener())
        .withClassLoader(this::getDefaultLoader)
        .build();

    runtime.run();
    setExitStatus(runtime.exitStatus());
  }


}
