package util;

/**
 * Thomas Daniellou & Amona Souliman
 */
public class Utils {

    /**
     * Window's width.
     */
    public static final int WINDOW_WIDTH = 700;

    /**
     * Window's height.
     */
    public static final int WINDOW_HEIGHT = 550;

    /**
     * Default board's size.
     */
    public static final int DEFAULT_BOARD_SIZE = 4;

    /**
     * Default power of 2 to win.
     * 9 for 512
     * 11 for 2048
     */
    public static final int DEFAULT_RANK_TO_WIN = 11;

    /**
     * Returns the corresponding tile's color.
     * @param tileValue
     * @return the corresponding tile's color
     */
    public static String getTileColor(int tileValue) {
        switch (tileValue) {
            case 0:
                return "#bbada0";
            case 2:
                return "#eee4da";
            case 4:
                return "#ede0c8";
            case 8:
                return "#f2b179";
            case 16:
                return "#f59563";
            case 32:
                return "#f67c5f";
            case 64:
                return "#f65e3b";
            case 128:
                return "#edcf72";
            case 256:
                return "#edcc61";
            case 512:
                return "#edc850";
            case 1024:
                return "#edc53f";
            case 2048:
                return "#edc22e";
            default:
                return "#ffffff";
        }
    }
}
