# Running

Adding BDD for All to your application is easy...

First off you'll need to [install BDD For All](INSTALLING.md).

Next, in the root of the directory you place your test classes in (typically src/test/java for JAVA projects, src/test/kotlin for Kotlin projects, etc...), place the following...

##### Java

```java
    import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
    
    import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
    import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
    import com.github.tomakehurst.wiremock.junit.WireMockRule;
    import cucumber.api.CucumberOptions;
    import cucumber.api.junit.Cucumber;
    import org.junit.ClassRule;
    import org.junit.Rule;
    import org.junit.runner.RunWith;
    
    @RunWith(Cucumber.class)
    @CucumberOptions(
        features = {"classpath:features"},
        plugin = {"pretty", "html:target/cucumber"},
        glue = { "com.accenture.testing.bdd.api.steps" }
    )
    public class RunCucumberTest {
    
      // initialize your server or mocks

    }
```

##### Kotlin

```kotlin
    import cucumber.api.CucumberOptions
    import cucumber.api.junit.Cucumber
    import org.junit.runner.RunWith
    
    
    @RunWith(Cucumber::class)
    @CucumberOptions(
        features = ["src/test/resources/cucumber/features"],
        plugin = ["pretty", "html:target/cucumber"],
        glue = [ "com.accenture.testing.bdd.api.steps" ]
    )
    
    class RunKukesTest
  
    // initialize your server or mocks  
```

##### Scala
```scala
import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.ClassRule
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features"),
  plugin = Array("pretty", "html:target/cucumber"),
  glue = Array("com.accenture.testing.bdd.api.steps")
)
class RunCucumberTest

// initialize your server or mocks
```

You'll want to start up your mocks or server as the harness assumes a running server. This class can use standard JUnit annotations, @ClassRule, @BeforeClass, @AfterClass.

> For an example using wiremock, checkout our test runner - [src/test/java/RunCucumberTest.java](../src/test/java/RunCucumberTest.java)

Drop the application.conf and logback-test.xml files from [src/test/resources](../src/test/resources) into your test resources directory.

* If you already have logback, just add the configurations
* In the application.conf file...
  * Update the server.host to be your url (no need to specify port if normal HTTP/HTTPS)
  * Remove the sample header section, modify or add your own
  
###### Creating Your First Feature File
  
* Create a directory called features in your test/resources directory
* Create a file called Tests.feature with the following content...
```gherkin
Feature: My first feature file
Scenario: Check I can connect
  Given I am a JSON API consumer
    And I am executing test "CONN_TEST"
   When I request GET "/"
   Then I should get a status code of 200
```
Make sure to replace "/" with a valid path on your server.

Finally execute your test (e.g. `mvn test`, `sbt test`, `gradle test`)

You should see the test execute on screen and whether it passed or failed.  You can also look at...

* logs/bdd-testrun.log and curl.log for their outputs
* target/cucumber there should be an index.html for a cucumber report

For more configuration options, checkout https://docs.cucumber.io/cucumber/api/#junit

### Running Stand Alone

You can just execute the JAR file as well.

* Download the jar [releases/releases/bdd-for-all-1.0-SNAPSHOT-jar-with-dependencies.jar](../releases/bdd-for-all-1.0-SNAPSHOT-jar-with-dependencies.jar)
* Execute `java -Dconfig.file=<PATH_TO_CONFIG> -jar bdd-for-all-1.0-SNAPSHOT-jar-with-dependencies.jar <OPTIONS> <PATH_TO_FEATURE_FILE(S)>`
  * PATH_TO_CONFIG - relative or full-qualified path to application.conf file (example [src/test/resources/application.conf](../src/test/resources/application.conf))
  * OPTIONS - Pass the -h (or --help) switch here to see all available options
  * PATH_TO_FEATURE_FILE(S) - relative or full-qualified path to a directory, a feature file
    * If specifying the name of a particular feature file you can also specify line numbers (e.g. My.feature:2:9) would load scenarios from lines 2 and 9
  
> Remember to pass the configuration file (e.g. *-Dconfig.file=<PATH_TO_CONFIG>*) BEFORE providing the -jar command, otherwise you will get an unknown option error