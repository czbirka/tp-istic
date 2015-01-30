package model;

import java.util.logging.Logger;

/**
 * Created by plouzeau on 2014-10-09.
 * <p/>
 * A square board from hosting tiles.
 */
public interface Board {

    /**
     * Gets the board's size.
     * @return the board's size
     */
    int getSideSizeInSquares();

    /**
     * Checks if the player won.
     * @return true if the player won
     */
    boolean hasWon();

    /**
     * Checks if the player lost.
     * @return true if the player lost
     */
    boolean isGameOver();

    /**
     * Gets the player's points.
     * @return the player's points
     */
    int getPoints();
    
    /**
     * Return the tile at a given coordinate, or null if none exists there.
     *
     * @param lineNumber   must be >=1 and <= getSideSizeInSquares()
     * @param columnNumber must be >=1 and <= getSideSizeInSquares()
     * @return a tile or null if none
     * @throws java.lang.IllegalArgumentException if parameters are out of board's bounds
     */
    Tile getTile(int lineNumber, int columnNumber);

    public enum Direction {
        LEFT, RIGHT, TOP, BOTTOM
    }

    /**
     * Apply the only game action: packing tiles
     * @param direction  where to push the tiles
     */
    void packIntoDirection(Direction direction);

    /**
     * Validate the step effects
     * NOTE: do we need this in the interface?
     */
    void commit();
}
