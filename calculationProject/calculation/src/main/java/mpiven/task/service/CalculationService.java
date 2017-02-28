package mpiven.task.service;

import mpiven.task.helpers.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculationService {
  private static final Logger log = LoggerFactory.getLogger(CalculationService.class);
  private static final String FIRST_PRIORITY_OPERATION = "(^|\\-|\\+)(\\d+\\.\\d+)(\\*|\\/)(\\d+\\.\\d+)";
  private static final String SECOND_PRIORITY_OPERATION = "(^|\\-|\\+)(\\d+\\.\\d+)(\\+|\\-)(\\d+\\.\\d+)";
  private static final String INTEGER_NUMERIC = "(^|\\*|\\/|\\+|\\-)(\\d+)(\\*|\\/|\\+|\\-|$)";
  @Autowired
  private PropertiesHelper properties;

  public String calculate(Map<String, Float> parameters) {

    String formula = properties.getActions().get(parameters.size());
    log.info("According to size of parameters ({}), will be calculated following formula: {}", parameters.size(), formula);

    formula = prepareFormulaForCalculation(formula, parameters);
    log.info("Will be calculated following: " + formula);

    Pattern pattern = Pattern.compile(FIRST_PRIORITY_OPERATION);
    formula = determineGroupsAndCalculate(formula, pattern);

    pattern = Pattern.compile(SECOND_PRIORITY_OPERATION);
    formula = determineGroupsAndCalculate(formula, pattern);

    log.info("Result: " + formula);
    return formula;
  }

  protected String prepareFormulaForCalculation(String formula, Map<String, Float> parameters) {

    Pattern pattern = Pattern.compile(INTEGER_NUMERIC);
    Matcher matcher = pattern.matcher(formula);
    while (matcher.find()) {
      String integerNumeric = matcher.group(2);
      formula = formula.replaceFirst(integerNumeric, integerNumeric + ".0");
      log.debug("Replaced {} with {}.0", integerNumeric, integerNumeric);
    }
    for (Map.Entry entry : parameters.entrySet()) {
      formula = formula.replaceAll(entry.getKey().toString(), entry.getValue().toString());
      log.debug("Replaced {} with {}", entry.getKey(), entry.getValue());
    }
    return formula;
  }

  protected String determineGroupsAndCalculate(String inputString, Pattern pattern) {
    Matcher matcher = pattern.matcher(inputString);
    if (matcher.find()) {
      String group = matcher.group(0);
      char firstNumericSign = !matcher.group(1).isEmpty() ? matcher.group(1).charAt(0) : '+';
      float firstNumeric = Float.valueOf(matcher.group(2));
      char sign = matcher.group(3).charAt(0);
      float secondNumeric = Float.valueOf(matcher.group(4));
      String resultOfGroup = calculateSimpleOperation(firstNumeric, secondNumeric, sign, firstNumericSign);
      log.debug("Determinated group: {}, replaced with result: {}", group, resultOfGroup);
      inputString = inputString.replace(group, resultOfGroup);
      log.debug("Input string: {}", inputString);
    } else {
      return inputString;
    }
    return determineGroupsAndCalculate(inputString, pattern);
  }

  protected String calculateSimpleOperation(float firstNumeric, float secondNumeric, char sign, char firstNumericSign) {
    String stringResult = null;
    float result = 0;
    switch (sign) {
      case '*': {
        result = firstNumeric * secondNumeric;
        stringResult = firstNumericSign + String.valueOf(result);
        break;
      }
      case '/': {
        result = firstNumeric / secondNumeric;
        stringResult = firstNumericSign + String.valueOf(result);
        break;
      }
      case '+': {
        if (firstNumericSign == '+') {
          result = firstNumeric + secondNumeric;
        } else {
          result = -firstNumeric + secondNumeric;
        }
        stringResult = String.valueOf(result);

        break;
      }
      case '-': {
        if (firstNumericSign == '+') {
          result = firstNumeric - secondNumeric;
        } else {
          result = -firstNumeric - secondNumeric;
        }
        stringResult = String.valueOf(result);
        break;
      }
    }
    return stringResult;
  }
}
