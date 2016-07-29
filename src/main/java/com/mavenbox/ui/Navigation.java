package com.mavenbox.ui;

import com.mavenbox.model.Workspace;
import com.mavenbox.model.Workspaces;
import com.mavenbox.ui.util.TransitionQueue;
import com.mavenbox.ui.util.TransitionUtil;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the project controller
 */
public class Navigation extends HBox {

  public static final double ZOOM_FACTOR = 1.3;
  private HBox scroller;
  private TransitionQueue transitionQueue;
  private int index = 0;
  private List<WorkspaceNavigation> jobNodes = new ArrayList<>();
  private WorkspaceNavigation activeNode;

  public Navigation() {
    setAlignment(Pos.CENTER);
    setId("root");
    init();
  }

  public void scrollRight() {
    if(index < Workspaces.getWorkspaces().size()-1) {
      index++;
      scroll(-WorkspaceNavigation.WIDTH);
    }
  }

  public void scrollLeft() {
    if(index > 0) {
      index--;
      scroll(WorkspaceNavigation.WIDTH);
    }
  }

  private void scroll(int width) {
    List<Transition> transitions = new ArrayList<>();
    transitions.add(TransitionUtil.createScaler(activeNode.getTitle(), 1.0));
    activeNode = jobNodes.get(index);
    transitions.add(TransitionUtil.createScaler(activeNode.getTitle(), ZOOM_FACTOR));
    transitions.add(TransitionUtil.createTranslateByXTransition(scroller, 200, width));
    transitionQueue.addTransition(new ParallelTransition(transitions.toArray(new Transition[transitions.size()])));
    transitionQueue.play();
  }

  private void init() {
    VBox verticalRoot = new VBox();
    StackPane rootStack = new StackPane();
    verticalRoot.getChildren().addAll(createTitle(), rootStack);
    rootStack.getChildren().addAll(createBackground());

    scroller = new HBox();
    transitionQueue = new TransitionQueue(scroller);

    HBox spacer = new HBox();
    spacer.setAlignment(Pos.BASELINE_CENTER);
    spacer.getStyleClass().addAll("job-node");
    double offset = (Workspaces.getWorkspaces().size() - 1) * WorkspaceNavigation.WIDTH;
    spacer.setMinWidth(offset);
    scroller.getChildren().add(spacer);


    for(Workspace workspace : Workspaces.getWorkspaces()) {
      jobNodes.add(new WorkspaceNavigation(workspace));
    }
    scroller.getChildren().addAll(jobNodes);
    rootStack.getChildren().addAll(scroller);

    getChildren().add(verticalRoot);

    activeNode = jobNodes.get(0);
    TransitionUtil.createScaler(activeNode.getTitle(), ZOOM_FACTOR).play();
  }

  private Node createBackground() {
    VBox background = new VBox();
    background.getChildren().addAll(createLogos(), createProjects());
    return background;
  }

  private Node createTitle() {
    HBox box = new HBox();
    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    box.setMinWidth(primaryScreenBounds.getWidth());
    box.setAlignment(Pos.BASELINE_CENTER);
    box.getStyleClass().addAll("title-box");
    Label title = new Label("PROJECT CONTROL");
    title.getStyleClass().addAll("font-default", "title-font");
    box.getChildren().add(title);
    return box;
  }

  private Node createLogos() {
    HBox box = new HBox();
    box.setPadding(new Insets(5, 0, 5, 0));
    box.setMinHeight(66);
    box.setAlignment(Pos.BASELINE_CENTER);
    box.getStyleClass().addAll("logo-scroller");
    return box;
  }

  private Node createProjects() {
    HBox box = new HBox();
    box.setMinHeight(70);
    box.setAlignment(Pos.CENTER);
    box.getStyleClass().addAll("project-scroller");
    return box;
  }
}
