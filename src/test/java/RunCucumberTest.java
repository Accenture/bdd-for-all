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
    plugin = {"pretty", "json:target/cucumber/cucumber.json"}
)
public class RunCucumberTest {

  @ClassRule @Rule
  public static WireMockRule wireMockRule =
      new WireMockRule(
          options()
              .port(8181)
              .extensions(new ResponseTemplateTransformer(false))
              .notifier(new Slf4jNotifier(true)),
          false);
}
