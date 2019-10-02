package jumptest;

import com.sun.javafx.scene.traversal.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Jumper extends ImageView {
    
    private final String fileName = "jumper.png";
    
    private boolean
            movingLeft = false,
            movingRight = false,
            isJumping = false;
    
    private double
            deltaX = 3,
            deltaY = 3,
            jumpForce = 6,
            gravForce = 0.15;
    
    public Jumper() {
        setImage(new Image(fileName));
    }
    
    public Jumper(double width, double height) {
        setImage(new Image(fileName));
        setFitWidth(width);
        setFitHeight(height);
    }
    
    public void startMove(Direction direction) {
        switch(direction) {
            case LEFT:
                movingLeft = true; break;
            case RIGHT:
                movingRight = true; break;
        }
    }
    
    public void stopMove(Direction direction) {
        switch(direction) {
            case LEFT:
                movingLeft = false; break;
            case RIGHT:
                movingRight = false; break;
        }
    }
    
    public void move() {
        if (movingLeft ^ movingRight) {
            if (movingLeft) {
                setX(getX() - deltaX);
            }
            
            if (movingRight) {
                setX(getX() + deltaX);
            }
        }
        
        if (isJumping) {
            setY(getY() - deltaY);
        }
    }
    
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            deltaY = jumpForce;
        }
    }
    
    public void fall() {
        isJumping = true;
        deltaY -= gravForce;
    }
    
    public void land() {
        isJumping = false;
        deltaY = 0;
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
    
    public double getDeltaX() {
        return deltaX;
    }
    
    public double getDeltaY() {
        return deltaY;
    }
    
    public void act() {
        move();
    }
}
