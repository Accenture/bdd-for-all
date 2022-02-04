import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;


import com.accenture.testing.bdd.BDDForAll;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.cucumber.core.options.Constants;
import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;

public class RunCucumberTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(
      options()
          .port(8181)
          .extensions(new ResponseTemplateTransformer(false))
          .notifier(new Slf4jNotifier(true)),
      false);

  @Test
  public void runBDDForAll() throws InterruptedException {
    Map<String,String> overrides = new HashMap<>();
    overrides.put(Constants.FEATURES_PROPERTY_NAME, "src/test/resources/features");
    overrides.put(Constants.PLUGIN_PROPERTY_NAME, "pretty, json:target/cucumber/primary.json");
    new BDDForAll().run(overrides);
  }

}
