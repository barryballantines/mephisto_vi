package com.mavenbox.ui.projects;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 */
public class BranchNavigation extends HBox {

  private Branch branch;

  public BranchNavigation(Branch branch) {
    this.branch = branch;
    init();
  }

  public Branch getBranch() {
    return branch;
  }

  private void init() {
    getStyleClass().add("branch-node");
    setMinHeight(30);
    Label branchName = new Label(branch.getName());
    getChildren().add(branchName);
  }

  public void select(boolean selected) {
    if(selected) {
      getStyleClass().add("branch-node--selected");
    }
    else {
      getStyleClass().remove("branch-node--selected");
    }
  }
}
