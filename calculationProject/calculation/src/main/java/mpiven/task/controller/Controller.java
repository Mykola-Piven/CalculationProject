package mpiven.task.controller;

import mpiven.task.helpers.PropertiesHelper;
import mpiven.task.helpers.RequestHelper;
import mpiven.task.service.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {
  private static final Logger log = LoggerFactory.getLogger(Controller.class);

  @Autowired
  RequestHelper requestHelper;

  @Autowired
  CalculationService calculateService;

  @Autowired
  private PropertiesHelper properties;

  @RequestMapping(value = "/calculate", method = RequestMethod.POST)
  public String doCalculation(@RequestBody Map<String, Float> parameters) {
    log.info("doCalculation method is calling");
    requestHelper.checkAndClearRequestParameters(parameters);
    return calculateService.calculate(parameters);
  }

  @RequestMapping(value = "/configuration", method = RequestMethod.GET)
  public Map<String, String> configuration() {
    log.info("getConfiguration method is calling");
    return properties.getActionsMap();
  }

}
