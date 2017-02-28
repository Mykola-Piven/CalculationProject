package mpiven.task.helpers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PropertiesHelper {
  private static final Logger log = LoggerFactory.getLogger(PropertiesHelper.class);
  private final CalculationProperties properties;

  @Autowired
  public PropertiesHelper(CalculationProperties properties) {
    this.properties = properties;
    log.debug("Calculation Properties are set: " + properties);
    this.properties.setActionsMap(this.properties.getActions());
  }

  public List<String> getActions() {
    return properties.getActions();
  }

  public Set<String> getValues() {
    return properties.getValues();
  }

  public Map<String, String> getActionsMap() {
    return properties.getActionsMap();
  }

}
