package com.accenture.testing.bdd.cucumber;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventHandler;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestRunFinished;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * hook into cucumber events
 */
@Data
@NoArgsConstructor
public class BDDEventListener implements EventListener {

  private TestCaseEventHandler testCaseEventHandler = new TestCaseEventHandler();
  private TestRunFinishedHandler testRunFinishedHandler = new TestRunFinishedHandler();

  @Override
  public void setEventPublisher(EventPublisher eventPublisher) {
    eventPublisher.registerHandlerFor(TestCaseFinished.class, getTestCaseEventHandler());
    eventPublisher.registerHandlerFor(TestRunFinished.class, getTestRunFinishedHandler());
  }

  /**
   * number of test cases run
   * @return the number of test cases run
   */
  public long getRunCount() {
    return testCaseEventHandler
        .getFinishedItems()
        .size();
  }

  /**
   * how many failures were there?
   * @return the number of failures
   */
  public long getFailureCount() {
    return testCaseEventHandler
        .getFinishedItems()
        .stream()
        .filter(item -> item.getResult().getStatus() == Status.FAILED)
        .count();
  }

  /**
   * get the result of the entire run
   * @return the result of the entire run
   */
  public Result getResult() {
    return testRunFinishedHandler.getResult();
  }

  @Data
  public static class TestCaseEventHandler
      implements EventHandler<TestCaseFinished> {

    List<TestCaseFinished> finishedItems = new ArrayList<>();

    @Override
    public void receive(TestCaseFinished testCaseFinished) {
      finishedItems.add(testCaseFinished);
    }

  }

  @Data
  public static class TestRunFinishedHandler
    implements EventHandler<TestRunFinished> {

    Result result;

    @Override
    public void receive(TestRunFinished testRunFinished) {
      result = testRunFinished.getResult();
    }

  }



}
