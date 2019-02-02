# Other (Cool) Features

We have some other cool features included as well...

#### JaCoCo Code Coverage

If you are already using JaCoCo instrumentation with JUnit or TestNG, you should notice your test coverage increase.

As an example, check out the current coverage for this library [here](samples/jacoco.csv).

With only 6 traditional unit tests, we achieve 94% test coverage because of the scenarios we've written.

#### Running Select Tests (aka Tagging)

Both features and scenarios can be marked up with tags (look like annotations).  They can be whatever you want, but what they do is allow you to group and filter tests that you want you run.

You can read a little more here - https://docs.cucumber.io/cucumber/api/#tags

To do this with maven...

```sbtshell
mvn test -Dcucumber.options='--tags "@smoke and @fast"'
```

Another option, is to do this in your code.  The example below adds the "tags" attribute to CucumberOptions, telling it to run just the @Smoke tests.  There's a good article at http://toolsqa.com/cucumber/cucumber-tags/ that describes the syntax for and'ing and or'ing.

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
    tags = { "@Smoke" }
)
public class RunCucumberTest {


}
```

Or if you're using this as a command line app, just add the -t and the (expression)[https://docs.cucumber.io/cucumber/api/#tag-expressions]

```sbtshell
java -Dconfig.file=<PATH_TO_CONFIG> -jar bdd-for-all-1.0-SNAPSHOT.jar -t @Json and @ResponseMatch  <PATH_TO_FEATURE_FILE(S)>
```

We recommend using them, but making sure to not get too crazy.  We typically run the following against our feature files to see what folks are doing...

```sbtshell
grep -ERh "^\@.*" src/test/resources/features | xargs printf '%s\n' | sort -u
```

The output, provides us a unique list of the tags in use...

```sbtshell
@Advanced
@BasicSteps
@Beta
@Faker
@Headers
@Params
@Regression
@ResponseMatch
@Smoke
@Xml
```

Remember, big brother is watching!

#### cURL Logging

We know a lot of shops use Postman, SoapUI, and the like.  We do too!  By default, every time you run the application, a curl.log is output to the logs/ directory.

This will output a cURL request for every test run.  These can be loaded into your favorite app or used to provide others with some samples for them to learn how to use.

Check [this sample](samples/curl.log) out to see what is generated from our test runs.

For information on configuring the location (or turning it off), checkout out our [Configuration](CONFIGURATION.md#curlog)