package model;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by plouzeau on 2014-10-09.
 *
 * Thomas Daniellou & Amona Souliman
 */
public class BoardImpl implements Board {

    private Tile[][] currentBoard;
    private Tile[][] nextBoard;
    private final int sideSizeInSquares;
    private final int rankToWin;
    private int points;
    private boolean wonTheGame;
    private boolean gameOver;
    private Direction directionToPackInto;

    public BoardImpl(int sideSizeInSquares, int valueToWin) {
        if (sideSizeInSquares <= 1) {
            throw new IllegalArgumentException("sideSizeInSquares");
        }
        this.sideSizeInSquares = sideSizeInSquares;
        this.rankToWin = valueToWin;
        points = 0;
        wonTheGame = false;
        gameOver = false;
        currentBoard = createStartingBoard();
        nextBoard = new Tile[sideSizeInSquares][sideSizeInSquares];
    }

    /**
     * Creates a new board with 2 tiles set randomly.
     *
     * @return
     */
    private Tile[][] createStartingBoard() {
        Tile[][] newBoard = new Tile[sideSizeInSquares][sideSizeInSquares];

        addNewTile(newBoard);
        addNewTile(newBoard);
        
        return newBoard;
    }

    /**
     * Randomly adds a new tile to the board at an available place.
     *
     * @param board
     */
    private void addNewTile(Tile[][] board) {
        Random rand = new Random();
        int y = rand.nextInt(sideSizeInSquares);
        int x = rand.nextInt(sideSizeInSquares);

        while(board[x][y] != null) {
            x = rand.nextInt(sideSizeInSquares);
            y = rand.nextInt(sideSizeInSquares);
        }

        board[x][y] = new TileImpl(1);
    }

    /**
     * Compares 2 boards.
     *
     * @param board1
     * @param board2
     * @return true if board1 and board2 are the same
     */
    private boolean compare(Tile[][] board1, Tile[][] board2) {
        for (int i = 0; i < sideSizeInSquares; i++) {
            for (int j = 0; j < sideSizeInSquares; j++) {
                if (board1[i][j] == null && board2[i][j] != null) {
                    return false;
                }
                if (board1[i][j] != null && board2[i][j] == null) {
                    return false;
                }
                if (board1[i][j] != null && board2[i][j] != null) {
                    if (board1[i][j].getRank() != board2[i][j].getRank()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if the player lost the game.
     *
     * @return true if game over
     */
    private boolean checkGameOver() {
        for (int i = 0; i < sideSizeInSquares; i++) {
            for (int j = 0; j < sideSizeInSquares; j++) {
                // Check for empty spaces
                if (nextBoard[i][j] == null) {
                    return false;
                }
                // Check the neighbors
                if (hasEqualNeighbor(nextBoard, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a Tile has a neighbor with the same rank.
     *  
     * @param board
     * @param i
     * @param j
     * @return
     */
    private boolean hasEqualNeighbor(Tile[][] board, int i, int j) {
        int rank = board[i][j].getRank();

        // Check the top neighbor (if there is any)
        if (isInbound(i-1) && board[i-1][j] != null) {
            if (board[i-1][j].getRank() == rank) {
                return true;
            }
        }
        // Check the bottom neighbor (if there is any)
        if (isInbound(i+1) && board[i+1][j] != null) {
            if (board[i+1][j].getRank() == rank) {
                return true;
            }
        }
        // Check the left neighbor (if there is any)
        if (isInbound(j-1) && board[i][j-1] != null) {
            if (board[i][j-1].getRank() == rank) {
                return true;
            }
        }
        // Check the right neighbor (if there is any)
        if (isInbound(j+1) && board[i][j+1] != null) {
            if (board[i][j+1].getRank() == rank) {
                return true;
            }
        }

        return false;
    }

    /**  
     * Checks if an index is in the board bounds.
     *  
     * @param i
     * @return
     */
    private boolean isInbound(int i) {
        return ((0 <= i) && (i < sideSizeInSquares));
    }

    @Override
    public int getSideSizeInSquares() {
        return this.sideSizeInSquares;
    }

    @Override
    public boolean hasWon() {
        return this.wonTheGame;
    }
    
    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    /**
     * Return the tile at a given coordinate, or null if none exists there.
     *
     * @param lineNumber   must be >=1 and <= getSideSizeInSquares()
     * @param columnNumber must be >=1 and <= getSideSizeInSquares()
     * @return a tile or null if none
     * @throws IllegalArgumentException if parameters are out of board's bounds
     */
    @Override
    public Tile getTile(int lineNumber, int columnNumber) {
        return currentBoard[lineNumber - 1][columnNumber - 1];
    }

    /**
     * Apply the only game action: packing tiles
     * @param direction  where to push the tiles
     */
    @Override
    public void packIntoDirection(Direction direction) {
        this.directionToPackInto = direction;
        for (int i = 1; i <= sideSizeInSquares; i++) {
            packLine(i);
        }

    }

    /**
     * Validate the step effects
     * NOTE: do we need this in the interface?
     */
    @Override
    public void commit() {
        
        // If any tile has moved, we add a new one
        if (!compare(currentBoard, nextBoard)) {
            addNewTile(nextBoard);
        }
        
        // Check if game is over
        gameOver = checkGameOver();
        
        // Update the boards
        currentBoard = nextBoard;
        nextBoard = new Tile[sideSizeInSquares][sideSizeInSquares];
    }

    private void packLine(int lineNumber) {
      /*
      * Scan the current board line looking for two consecutive tiles
      * with the same rank
      * When this case is encountered, write a single tile with rank+1
      * Otherwise just copy the tile (in practice packing it in the nex board)
      * Remember that indices are 1-based in this code
      * Conversion to Java arrays indices is done in computeLineIndex and computeColumnIndex
      */
        int readIndex = 1; // Position of the tile to be read
        int writeIndex = 0; // Position of the last tile written

        while (readIndex <= sideSizeInSquares) {
            // Find next tile
            while ((readIndex <= sideSizeInSquares)
                    && (readTile(currentBoard, lineNumber, readIndex) == null)) {
                readIndex++;
            }
            if (readIndex > sideSizeInSquares) {
                break; // Done with the line
            }
            // Try to merge with previous tile
            if ((writeIndex > 0) &&
                    (readTile(nextBoard, lineNumber, writeIndex).getRank()
                            == readTile(currentBoard, lineNumber, readIndex).getRank())) {
                // Merge previously written tile and currently read one
                readTile(nextBoard, lineNumber, writeIndex).incrementRank();
                
                // Update points and check for win
                int rank = readTile(nextBoard, lineNumber, writeIndex).getRank();
                points +=  (int) Math.pow(2, rank);
                if (rank == rankToWin) {
                    wonTheGame = true;
                }
            } else {
                // Advance write index and copy currently read tile
                writeIndex++;
                writeTile(nextBoard, readTile(currentBoard, lineNumber, readIndex), lineNumber, writeIndex);
            }
            // Done with the current tile read, move forward
            readIndex++;
        }


    }

    /**
     * Writes a tile into a matrix (board) using indices transformation
     * @param board       destination
     * @param tile        what to write at the given coordinates
     * @param lineIndex   coordinate
     * @param columnIndex  coordinate
     */
    private void writeTile(Tile[][] board, Tile tile, int lineIndex, int columnIndex) {
        board[computeLineIndex(lineIndex, columnIndex)][computeColumnIndex(lineIndex, columnIndex)] = tile;
    }

    /**
     * Returns a tile  from a matrix (board) using indices transformation
     * @param board      origin
     * @param lineIndex   coordinate
     * @param columnIndex  coordinate
     * @return    tile at the given coordinates or null if no tile there
     */
    private Tile readTile(Tile[][] board, int lineIndex, int columnIndex) {
        int boardLineIndex = computeLineIndex(lineIndex, columnIndex);
        int boardColumnIndex = computeColumnIndex(lineIndex, columnIndex);
        Tile currentTile = board[boardLineIndex][boardColumnIndex];
        return currentTile;
    }

    /**
     * Adds a level of indirection in the index computation
     * In practice provides a rotation/symmetry so that we need
     * to deal with one packing directionToPackInto only.
     * This operation also takes care of the conversion from (1..N) board
     * coordinates to the (0..N-1) Java array coordinates.
     *
     * NOTE: <b>NO CHECKS are made on parameter bounds.</b>
     *
     * @param lineIndex   must be in [1..sideSizeInSquares]
     * @param columnIndex must be in [1..sideSizeInSquares]
     * @return the columnIndex after rotation/symmetry
     */
    private int computeColumnIndex(int lineIndex, int columnIndex) {
        switch (directionToPackInto) {
            case RIGHT:
                return sideSizeInSquares - columnIndex;     //Symmetry on a vertical axis
            case LEFT:
                return columnIndex - 1;      //
            case TOP:
                return lineIndex - 1;
            case BOTTOM:
                return lineIndex - 1;
        }
        return 0; // NOT REACHED
    }

    /**
     * Adds a level of indirection in the index computation
     * In practice provides a rotation/symmetry so that we need
     * to deal with one packing directionToPackInto only.
     * This operation also takes care of the conversion from (1..N) board
     * coordinates to the (0..N-1) Java array coordinates.
     *
     * NOTE: <b>NO CHECKS are made on parameter bounds.</b>
     *
     * @param lineIndex   must be in [1..sideSizeInSquares]
     * @param columnIndex must be in [1..sideSizeInSquares]
     * @return the lineIndex after rotation/symmetry
     */
    private int computeLineIndex(int lineIndex, int columnIndex) {
        switch (directionToPackInto) {
            case LEFT:
                return lineIndex - 1;
            case RIGHT:
                return lineIndex - 1;
            case BOTTOM:
                return sideSizeInSquares - columnIndex;
            case TOP:
                return columnIndex - 1;
        }
        return 0; // NOT REACHED
    }

    /**
     * For testing purposes only.
     * Creates a board configuration using a matrix of ranks
     *
     * @param rankMatrix a non null matrix reference, must match board size
     */
    public void loadBoard(int[][] rankMatrix) {
        for (int i = 0; i < sideSizeInSquares; i++) {
            for (int j = 0; j < sideSizeInSquares; j++) {
                if (rankMatrix[i][j] > 0) {
                    currentBoard[i][j] = new TileImpl(rankMatrix[i][j]);
                }
            }
        }
    }

    /**
     * For testing purposes only.
     * Writes the ranks of contents of the matrix into a logger
     *
     * @param logger  where to write into
     * @param message the message to write first before writing the contents of the board
     */
    public void printBoard(Logger logger, String message) {

        logger.info(message);
        for (int i = 0; i < sideSizeInSquares; i++) {
            StringBuffer outputBuffer = new StringBuffer();
            outputBuffer.append(i + 1);
            outputBuffer.append(":{");
            for (int j = 0; j < sideSizeInSquares; j++) {
                if (currentBoard[i][j] != null) {
                    outputBuffer.append(currentBoard[i][j].getRank());
                } else {
                    outputBuffer.append("0");
                }
            }
            outputBuffer.append("}");
            logger.info(outputBuffer.toString());
        }
    }
}