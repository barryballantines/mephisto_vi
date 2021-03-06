package com.mavenbox.ui.monitoring;

import callete.api.Callete;

import java.util.ArrayList;
import java.util.List;

/**
 * Central point for monitoring actions.
 */
public class MonitoringService {

  private List<Pipeline> pipelines = new ArrayList<>();

  public void init() {
    int index = 1;
    while(true) {
      String nameKey = "monitoring." + index + ".name";
      String hostKey = "monitoring." + index + ".host";
      String linkKey = "monitoring." + index + ".link";

      if(Callete.getConfiguration().containsKey(nameKey)) {
        String name = Callete.getConfiguration().getString(nameKey);
        String host = Callete.getConfiguration().getString(hostKey);
        String link = Callete.getConfiguration().getString(linkKey);

        Pipeline pipeline = new Pipeline(index, name, host, link);
        new HostWatchDog(pipeline).start();
        pipelines.add(pipeline);
      }
      else {
        break;
      }
      index++;
    }
  }

  public List<Pipeline> getPipelines() {
    return pipelines;
  }
}
