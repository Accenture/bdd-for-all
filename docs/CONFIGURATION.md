# Configuration & Logging

Our default application configuration is fairly simple...

```yaml
bdd-for-all:
  request:
    server:
      host: "http://localhost:8181"
    userAgent: "ACN-BDD-CUCUMBER"
    correlationIdName: "X-Correlation-ID"
   ## the cucumber properties
  cucumber:
     plugin: "pretty, json:target/cucumber/primary-cucumber.json"
     glue: "com.accenture.testing.bdd.api.steps"
     ### Could pass through JAVA file too
     features: "src/test/resources/features"
     ### keeps that annoying message off screen
     ### around publisher
     publish:
        quiet: true
```

> You'll notice the cucumber setting are stored here as well, instead of using the @CucumberOptions annotation.  
> See [src/main/resources/application.yml](src/main/resources/application.yml) for the latest options.

This is usually stored in application.yml file and for your convenience we use Spring's loading preferences.  That means...

1. You can run by creating your own application.yml (or if you like properties better application.properties)
2. You can include the "bdd-for-all" section in your spring application configuration
3. You can use [Spring conventions](https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files) like...
    * spring.config.name to change the name of the config file
    * spring.config.additional-location to add custom locations with overrides
   
> NOTE: At this time we don't support spring profiles, but this is a test harness not production app :)

In addition to the default settings, you can also add default headers to each request sent.  This is good if you need to add things like Auth headers to each request sent...

```yaml
bdd-for-all:
  defaults:
    headers:
       - "Authorization": "Basic QWxhZGRpbjpPcGVuU2VzYW1l"
       - "From": "tester@test.com"
```

And even change the "initial contexts" for scenario's.  You'll use this to map your custom context (e.g. [Step 1a](GRAMMAR.md)) to one of the parsers.  If there's no mapping in the configuration file, will default to JSON parser.  You can see a working example in our test configuration - [/src/test/resources/application.yml](/src/test/resources/application.yml) - and test case - [/src/test/resources/features/BasicSteps.feature](/src/test/resources/features/BasicSteps.feature) in test BS4.

```yaml

bdd-for-all:
  consumers:
    - "Mobile device" = "JSON"
```

For the latest configuration options, just check out our test config at [../src/test/resources/application.yml](../src/test/resources/application.yml)

> You can add your own custom vars and have them be available to your steps as well here.  See [Generating Data (and Variables)](DATAGEN.md)


## Basic Logging

By default, all application logging is to a logs directory, including test information.  We're using Sl4j which is likely to be compatible from a configuration standpoint with your Java app.  You can see our default configuration [here](../src/test/resources/logback.xml), it's pretty straight forward except for one thing...

### cURL logging

In addition to application logging, each request will also be logged as a cURL statement.  This, by default, is actually sent to logs/curl.log.  The logger for this is named "curl", you can easily turn this off, change the file, etc... by updating the configuration.  Below is ours...

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  <appender name="CURL" class="ch.qos.logback.core.FileAppender">
    <file>logs/curl.log</file>
    <append>true</append>
    <encoder>
      <pattern>%msg%n%n</pattern>
    </encoder>
  </appender>

  <logger name="curl" additivity="false">
    <appender-ref ref="CURL"/>
    <filter class="ch.qos.logback.classic.filter.LevelFilter">
      <level>DEBUG</level>
    </filter>
  </logger>

</configuration>
```

The above logback configuration, simplifies the log output (removing all date/time and thread info) and logs anything from the "curl" logger to logs/curl.log.

This configuration DOES NOT overwrite previous runs, so it will continue to append to with each run.