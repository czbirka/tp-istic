
import controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Board;
import model.BoardImpl;
import util.Utils;

import java.net.URL;

/**
 * Thomas Daniellou & Amona Souliman
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL location = getClass().getResource("sample.fxml");
        Parent root = fxmlLoader.load(location.openStream());

        // Get the Controller
        final Controller controller = fxmlLoader.getController();
        
        Board board = new BoardImpl(Utils.DEFAULT_BOARD_SIZE, Utils.DEFAULT_RANK_TO_WIN);

        controller.setBoard(board);

        primaryStage.setTitle("GLI - 2048");
        Scene scene = new Scene(root, Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
        
        // Handle the keyboard events
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controller.handleKeyPressed(event.getCode());
            }
        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
