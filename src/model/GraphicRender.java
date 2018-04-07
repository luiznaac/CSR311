package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphicRender {

  private Group graphicLines;
  private Group auxLines;
  private Integer stepX;
  private Integer stepY;
  private Integer stateX;
  private Integer stateY;
  private char state;
  private final Integer sizeY = 160;
  private IntegerProperty stateSizeX;
  private Integer position;
  
  public GraphicRender(Group graphicLines, Group auxLines) {
    this.graphicLines = graphicLines;
    this.auxLines = auxLines;
    stateSizeX = new SimpleIntegerProperty();
    initialize();
  }
  
  private void initialize() {
    auxLines.getChildren().clear();
    graphicLines.getChildren().clear();
    stepX = 30;
    stepY = -30;
    stateX = 10;
    stateY = 80;
    state = '1';
    position = 0;
    stateSizeX.set(stateX);
  }
  
  public void render(String signal) {
    char signalAt;
    initialize();
    for(int i = 0 ; i < signal.length() ; i++, position++) {
      signalAt = signal.charAt(i);
      stateSizeX.set(stateSizeX.get() + stepY);
      renderAux();
      if(signalAt != state) 
        renderY(signalAt);
      renderX(signalAt);
      state = signalAt;
    }
    graphicLines.toFront();
  }
  
  private void renderX(char signalAt) {
    Line line = new Line();
    line.setStroke(Color.BLACK);
    line.setStrokeWidth(2);
    line.setStartX(stateX);
    line.setStartY(stateY);
    line.setEndX(stateX += stepX);
    line.setEndY(stateY);
    graphicLines.getChildren().add(line);
  }
  
  private void renderY(char signalAt) {
    Integer mulStepY;
    Line line = new Line();
    mulStepY = Character.getNumericValue(signalAt) - Character.getNumericValue(state);
    line.setStroke(Color.BLACK);
    line.setStrokeWidth(2);
    line.setStartX(stateX);
    line.setStartY(stateY);
    line.setEndX(stateX);
    line.setEndY(stateY += mulStepY * stepY);
    graphicLines.getChildren().add(line);
  }
  
  private void renderAux() {
    Line lineX = new Line(stateX, (Integer)(sizeY / 2), stateX + stepX, (Integer)(sizeY / 2));
    lineX.setStroke(Color.GRAY);
    lineX.getStrokeDashArray().addAll(2d);
    Line lineY = new Line(stateX + stepX, sizeY / 2 + stepY, stateX + stepX, sizeY / 2 - stepY);
    lineY.setStroke(Color.GRAY);
    Text positionText = new Text(stateX + 5, sizeY / 2 + stepY - 5, Integer.toString(position));
    auxLines.getChildren().addAll(lineX, lineY, positionText);
  }
  
}
