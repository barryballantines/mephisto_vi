package com.mavenbox.ui.projects;

import callete.api.Callete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating job objects that are added via JobNodes to the main scroller.
 */
public class Workspaces {
  private final static Logger LOG = LoggerFactory.getLogger(Workspaces.class);

  public static List<Workspace> getWorkspaces() {
    List<Workspace> workspaces = new ArrayList<>();
    String folder = Callete.getConfiguration().getString("workspace.root");

    File dir = new File(folder);

    File[] projects = dir.listFiles(pathname -> pathname.isDirectory());

    for(File project : projects) {
      if(isValidWorkspace(project)) {
        try {
          Workspace workspace = new Workspace(project);
          workspaces.add(workspace);
        } catch (Exception e) {
          LOG.error("Failed to load git repository for " + project.getAbsolutePath() + ": " + e.getMessage(), e);
        }
      }
    }
    return workspaces;
  }

  private static boolean isValidWorkspace(File folder) {
    String[] exclusions = Callete.getConfiguration().getStringArray("workspace.excludes");
    for(String exclusion : exclusions) {
      if(folder.getName().equals(exclusion)) {
        return false;
      }
    }
    boolean isMaven = new File(folder, "pom.xml").exists();
    boolean isVersioned = new File(folder, ".git").exists();
    return isMaven && isVersioned;
  }
}
