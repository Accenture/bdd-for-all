# Running

Adding BDD for All to your application is easy...

First off you'll need to [install BDD For All](INSTALLING.md).

Next, in the root of the directory you place your test classes in (typically src/test/java for JAVA projects, src/test/kotlin for Kotlin projects, etc...), place the following...

### Java

This should work with JUnit 4 or 5...

```java
import com.accenture.testing.bdd.BDDForAll;
import org.junit.jupiter.api.Test;

public class RunCucumberTests {

  /**
   * executes BDD tests, for reporting purposes
   * we grab all the events as a factory
   */
  @Test
  Stream<DynamicTest> testFeatures() {
    
    // initialize stuff

    // run the test
    BDDForAll bddForAll = new BDDForAll();
    bddForAll.run();
    
    // clean up stuff
    
  }
  
}
```

Or better yet in JUnit 5, simplify your reporting without extra plugins...

```java
import com.accenture.testing.bdd.BDDForAll;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class RunCucumberTests {

  /**
   * executes BDD tests, for reporting purposes
   * we grab all the events as a factory
   */
  @TestFactory
  Stream<DynamicTest> testFeatures() {

    // run the test
    BDDForAll bddForAll = new BDDForAll();
    bddForAll.run();

    // report each individually
    return bddForAll.getEventListener()
    .getTestCaseEventHandler()
    .getFinishedItems()
    .stream().map(item -> {
      return DynamicTest.dynamicTest(item.getTestCase().getName(),
          () -> Assertions.assertTrue(item.getResult().getStatus().isOk()));
        });
  }

}
```

The above reports all the cucumber cases back to JUnit in a really simple way.  This method utilizes the 
[../src/main/java/com/accenture/testing/bdd/cucumber/BDDEventListener.java](../src/main/java/com/accenture/testing/bdd/cucumber/BDDEventListener.java) 
which could come in handy in a few situations.

##### Kotlin

```kotlin
import com.accenture.testing.bdd.BDDForAll
import org.junit.jupiter.api.Test
class RunCucumberTests {
  /**
   * executes BDD tests, for reporting purposes
   * we grab all the events as a factory
   */
  @Test
  internal fun testFeatures():Stream<DynamicTest> {
    // initialize stuff
    // run the test
    val bddForAll = BDDForAll()
    bddForAll.run()
    // clean up stuff

  }
}
```

### Executing

You'll want to start up your mocks or server as the harness assumes a running server. This class can use standard JUnit annotations, @ClassRule, @BeforeClass, @AfterClass.

> For an example using wiremock, checkout our test runner - [src/test/java/RunCucumberTest.java](../src/test/java/RunCucumberTest.java)

Drop the application.yml and logback-test.xml files from [src/test/resources](../src/test/resources) into your test resources directory.

* If you already have logback, just add the configurations
* In the application.yml file...
  * Update the server.host to be your url (no need to specify port if normal HTTP/HTTPS)
  * Update the feature path (or remove and pass in command line
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
* checkout the "plugins" section for the paths to the reports.

### Running Stand Alone

You can just execute the JAR file as well.

* Download the jar from Maven https://mvnrepository.com/artifact/com.accenture.testing.bdd/bdd-for-all)
* Execute `java -Dbddforall.config=<PATH_TO_CONFIG> -jar bdd-for-all-1.0-SNAPSHOT-jar-with-dependencies.jar <OPTIONS> <PATH_TO_FEATURE_FILE(S)>`
  * PATH_TO_CONFIG - relative or full-qualified path to application.conf file (example [../src/test/resources/application.yml](../src/test/resources/application.yml))
  * OPTIONS - Pass the -h (or --help) switch here to see all available options
    * If specifying the name of a particular feature file you can also specify line numbers (e.g. My.feature:2:9) would load scenarios from lines 2 and 9

> Remember to pass the configuration file (e.g. *-Dbddforall.config=<PATH_TO_CONFIG>*) BEFORE providing the -jar command, otherwise you will get an unknown option error