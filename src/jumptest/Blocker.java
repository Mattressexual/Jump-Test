package jumptest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Blocker extends ImageView {
    
    private final String fileName = "blocker.png";
    
    public Blocker() {
        setImage(new Image(fileName));
    }
    
    public Blocker(double width, double height) {
        setImage(new Image(fileName));
        setFitWidth(width);
        setFitHeight(height);
    }
    
    public double getLeftEdge() {
        return getX();
    }
    
    public double getRightEdge() {
        return getX() + getFitWidth();
    }
    
    public double getTopEdge() {
        return getY();
    }
    
    public double getBottomEdge() {
        return getY() + getFitHeight();
    }
}
