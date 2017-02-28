package mpiven.task.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculationServiceTest {

  @Autowired
  CalculationService calculationService;

  @Test
  public void determineGroupsAndCalculate() throws Exception {
    String FIRST_PRIORITY_OPERATION = "(^|\\-)(\\d+\\.\\d+)(\\*|\\/)(\\d+\\.\\d+)";
    String SECOND_PRIORITY_OPERATION = "(^|\\-)(\\d+\\.\\d+)(\\+|\\-)(\\d+\\.\\d+)";
    Pattern pattern = Pattern.compile(FIRST_PRIORITY_OPERATION);
    String formula = "100.50*20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("+2010.0"));

    formula = "100.50/20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("+5.025"));

    pattern = Pattern.compile(SECOND_PRIORITY_OPERATION);
    formula = "100.50-20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("80.5"));

    formula = "-100.50-20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("-120.5"));

    formula = "100.50+20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("120.5"));

    formula = "-100.50+20.0";
    assertTrue(calculationService.determineGroupsAndCalculate(formula, pattern).equals("-80.5"));

  }

  @Test
  public void calculateSimpleOperation() throws Exception {
    float firstNumeric = Float.valueOf("10");
    float secondNumeric = Float.valueOf("5");

    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '*', '+').equals("+50.0"));
    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '/', '+').equals("+2.0"));
    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '+', '+').equals("15.0"));
    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '+', '-').equals("-5.0"));
    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '-', '+').equals("5.0"));
    assertTrue(calculationService.calculateSimpleOperation(firstNumeric, secondNumeric, '-', '-').equals("-15.0"));

  }

}