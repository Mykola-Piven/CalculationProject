package mpiven.task.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RequestHelper {
  private static final Logger log = LoggerFactory.getLogger(RequestHelper.class);

  @Autowired
  private PropertiesHelper propertiesHelper;

  public void checkAndClearRequestParameters(Map<String, Float> parameters) {
    log.info("checkAndClearRequestParameters method is calling");
    parameters.entrySet().removeIf((entry) -> {
      if (entry.getKey() == null || entry.getValue() ==null || !propertiesHelper.getValues().contains(entry.getKey())) {
        log.warn("Input parameter: {} : {} is NOT valid, will be removed", entry.getKey(), entry.getValue());
        return true;
      }
      log.info("Input parameter: {} : {} is valid", entry.getKey(), entry.getValue());
      return false;

    });
  }
}
