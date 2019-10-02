package jumptest;

import com.sun.javafx.scene.traversal.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JumpTest extends Application {

    private Jumper jumper;
    private Blocker blocker;

    private final ImageView cage = new ImageView();

    private final double
            screenX = 400,
            screenY = 400;

    private final double
            jumperWidth = 20,
            jumperHeight = 20;
    
    private final double
            blockerWidth = 80,
            blockerHeight = 80;

    private Pane pane;
    private Scene scene;
    private Timeline loop;

    @Override
    public void start(Stage stage) {

        // Jumper
        jumper = new Jumper(jumperWidth, jumperHeight);
        jumper.setX(screenX / 4 - jumper.getFitWidth() / 2);
        jumper.setY(screenY - jumper.getFitHeight());
        
        // Blocker
        blocker = new Blocker(blockerWidth, blockerHeight);
        blocker.setX(screenX / 2 - blocker.getFitWidth() / 2);
        blocker.setY(screenY - blocker.getFitHeight());

        cage.setFitWidth(screenX - jumper.getFitWidth());
        cage.setFitHeight(screenY - jumper.getFitHeight());

        pane = new Pane();
        scene = new Scene(pane, screenX, screenY);
        stage.setScene(scene);
        stage.show();

        pane.getChildren().addAll(jumper, blocker, cage);

        initializeKeybinds();
        initializeTimelines();
    }

    public void initializeKeybinds() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                case LEFT:
                    jumper.startMove(Direction.LEFT);
                    break;
                case D:
                case RIGHT:
                    jumper.startMove(Direction.RIGHT);
                    break;
                case W:
                case UP:
                    jumper.jump();
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case A:
                case LEFT:
                    jumper.stopMove(Direction.LEFT);
                    break;
                case D:
                case RIGHT:
                    jumper.stopMove(Direction.RIGHT);
                    break;
            }
        });
    }

    public void initializeTimelines() {
        loop = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            
            if (jumper.intersects(cage.getX(), cage.getY(), cage.getFitWidth(), cage.getFitHeight())) {
                jumper.act();
                
                if (jumper.getY() + jumper.getFitHeight() < cage.getX() + cage.getFitHeight()) {
                    jumper.fall();
                }
                
                // If colliding with blocker
                if (jumper.intersects(blocker.getX(), blocker.getY(), blocker.getFitWidth(), blocker.getFitHeight())) {
                    
                    // If jumper is above blocker
                    if (jumper.getY() < blocker.getY() && jumper.getY() + jumper.getFitHeight() >= blocker.getY()) {
                        
                        // If either of jumper's left or right edges are over the corners of blocker
                        if (
                                
                                (jumper.getLeftEdge() < blocker.getRightEdge() && jumper.getLeftEdge() > blocker.getLeftEdge()) ||
                                
                                (jumper.getRightEdge() > blocker.getLeftEdge() && jumper.getRightEdge() < blocker.getRightEdge())) {
                            
                            // If jumper is on the descent
                            if (jumper.getDeltaY() < 0) {
                                jumper.land();
                                jumper.setY(blocker.getY() - jumper.getFitHeight());
                            }
                        }
                    }
                    
                    // Otherwise, if not approaching from the side
                    else {
                        // If approaching from the right
                        if (jumper.getLeftEdge() < blocker.getRightEdge() && jumper.getRightEdge() > blocker.getRightEdge()) {
                            jumper.setX(blocker.getRightEdge());
                        }
                        // Or if approaching from the left
                        else if (jumper.getRightEdge() > blocker.getLeftEdge() && jumper.getLeftEdge() < blocker.getRightEdge()) {
                            jumper.setX(blocker.getLeftEdge() - jumper.getFitWidth());
                        }
                    }
                }
                
                if (jumper.getX() < cage.getX()) {
                    jumper.setX(cage.getX());
                }
                
                if (jumper.getX() > cage.getFitWidth()) {
                    jumper.setX(cage.getFitWidth());
                }
                
                if (jumper.getY() > cage.getFitHeight()) {
                    if (jumper.getDeltaY() < 0) {
                        jumper.land();
                        jumper.setY(cage.getFitHeight());
                    }
                }
            }
            

            /*
            if (jumper.intersects(0, 0, cage.getFitWidth() - jumper.getFitWidth(), cage.getFitHeight() - jumper.getFitHeight())) {
                jumper.act();
            }
            if (jumper.getX() < 0) {
                jumper.setX(0);
            }
            
            if (jumper.getX() > cage.getFitWidth() - jumper.getFitWidth()) {
                jumper.setX(cage.getFitWidth() - jumper.getFitWidth());
            }

            if (jumper.getY() > cage.getFitHeight() - jumper.getFitHeight()) {
                jumper.land();
                jumper.setY(cage.getFitHeight() - jumper.getFitHeight());
            }
            */
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public static void main(String[] args) {
        launch();
    }

}
