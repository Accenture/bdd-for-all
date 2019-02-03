# Configuration & Logging

For application configuration we use typesafe config - https://github.com/lightbend/config - that has some features we particularly like.  Our logging is done with slf4j - https://www.slf4j.org/ - and backed by logback - https://logback.qos.ch/.

# Basic Application Configuration

Our default application configuration is fairly simple...

```
bddcore {

  request {
    server {
      /** the default host to execute requests against */
      host = "http://localhost:8080"
    }
    /** the user agent sent with each request */
    userAgent = "ACN-BDD-CUCUMBER"
    /** the MDC token sent with each request whose value is set from the step "I am executing test {string}" */
    correlationIdName = "X-Correlation-ID"
  }

  http {
    /** connection timeout settings */
    connection {
      managerTimeout = 10000
      requestTimeout = 10000
      socketTimeout = 10000
    }
  }
  
  consumers {

    "Mobile device" = "JSON"

  }

}
```

If you want to override any of these, just add a application.conf file to your test/resources folder.

```
bddcore {

  request {

    server {
      host = "http://example.com"
    }

  }

}
```

In the above example we override the default host, we can also do this using the step definition `I request (GET|POST) {string} on {string}`, but doing it here let's us easily change from environment to environment.

In addition to the default settings, you can also add default headers to each request sent.  This is good if you need to add things like Auth headers to each request sent...

```
bddcore {

    defaults {

      headers = [
        {"Authorization": "Basic QWxhZGRpbjpPcGVuU2VzYW1l"},
        {"From": "tester@test.com"}
      ]

    }

  }

}
```

The following line let's you create custom "initial contexts" for scenario's.  You'll use this to map your custom context (e.g. [Step 1a](GRAMMAR.md)) to one of the parsers.  If there's no mapping in the configuration file, will default to JSON parser.  You can see a working example in our test configuration - [/src/test/resources/application.conf](/src/test/resources/application.conf) - and test case - [/src/test/resources/features/BasicSteps.feature](/src/test/resources/features/BasicSteps.feature) in test BS4.

```

bddcore {

  consumers {

    "Mobile device" = "JSON"

  }
  
}

```

As you can see, headers are an array, so you can add as many as you want.

## Basic Logging

By default, all application logging is to stdout, including test information.  This can make things a little hard to read, but we assume most already have logging configurations set up for their tests.  If not, and you're using logback - [use ours](../src/test/resources/logback.xml)...

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

  ...

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <layout class="ch.qos.logback.classic.PatternLayout">
      ...
    </layout>
  </appender>

  <root level="debug">
    <appender-ref ref="STDOUT"/>
  </root>
  
  ...

</configuration>
```

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