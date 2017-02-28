package mpiven.task.helpers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConfigurationProperties(prefix="calculation")
public class CalculationProperties {
  private List<String> actions = new ArrayList<>();
  private Set<String> values = new HashSet<>();
  private Map<String,String> actionsMap = new TreeMap<>();

  public List<String> getActions() {
    return actions;
  }

  public void setActions(List<String> actions) {
    this.actions = actions;
  }

  public Set<String> getValues() {
    return values;
  }

  public void setValues(Set<String> values) {
    this.values = values;
  }

  public Map<String, String> getActionsMap() {
    return actionsMap;
  }

  public void setActionsMap(Map<String, String> actionsMap) {
    this.actionsMap = actionsMap;
  }

  public void setActionsMap(List<String> actionsList) {
    for (int i = 0; i < actionsList.size(); i++) {
      actionsMap.put(i + " entered inputs", actionsList.get(i));
    }
  }
}
