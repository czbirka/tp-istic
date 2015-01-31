package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Board;
import model.BoardImpl;
import util.Utils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Thomas Daniellou & Amona Souliman
 */
public class Controller implements Initializable {

    private Board board;

    @FXML
    private GridPane window;

    @FXML
    private Label points;

    @FXML
    private Label status;

    @FXML
    private Button replayBtn;

    private GridPane grid;

    @FXML
    public void onReplayAction() {
        setBoard(new BoardImpl(Utils.DEFAULT_BOARD_SIZE, Utils.DEFAULT_RANK_TO_WIN));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        window.add(grid, 0, 1);
    }

    public void setBoard(Board board) {
        this.board = board;
        status.setText("");
        update();
    }

    /**
     * Updates the view with the board's data.
     */
    public void update() {
        int tileValue;
        grid.getChildren().removeAll();
        points.setText("Points : " + board.getPoints());
        for (int i = 1; i <= board.getSideSizeInSquares(); i++) {
            for (int j = 1; j <= board.getSideSizeInSquares(); j++) {
                tileValue = 0;
                if (board.getTile(i, j) != null) {
                    tileValue = (int) Math.pow(2, board.getTile(i, j).getRank());
                }
                StackPane stack = new StackPane();
                Text text = new Text((tileValue > 0) ? "" + tileValue : "");
                text.setFont(Font.font(20));
                Circle shape = new Circle(40);
                shape.setFill(Paint.valueOf(Utils.getTileColor(tileValue)));
                shape.setStroke(Paint.valueOf("black"));
                stack.getChildren().addAll(shape, text);
                grid.add(stack, j, i);
            }
        }
        
        if (board.hasWon()) {
            status.setText("Congratulations! You've won!");
        }
        
        if (board.isGameOver()) {
            status.setText("You loose! Try again!");
        }
    }

    /**
     * Packs the lines into the corresponding direction.
     * @param key
     */
    public void handleKeyPressed(KeyCode key) {
        if (key == KeyCode.LEFT) {
            board.packIntoDirection(Board.Direction.LEFT);
            board.commit();
            update();
        }
        else if (key == KeyCode.RIGHT) {
            board.packIntoDirection(Board.Direction.RIGHT);
            board.commit();
            update();
        }
        else if (key == KeyCode.UP) {
            board.packIntoDirection(Board.Direction.TOP);
            board.commit();
            update();
        }
        else if (key == KeyCode.DOWN) {
            board.packIntoDirection(Board.Direction.BOTTOM);
            board.commit();
            update();
        }
    }
}
