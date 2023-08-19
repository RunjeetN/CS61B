package knightworld;

import Jama.CholeskyDecomposition;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    private TETile[][] tiles;

    public KnightWorld(int width, int height, int holeSize) {
        generateEmptyWorld(width, height);
        makeBoardTopRightCorner(width - 1, height - 1, width, height, holeSize);
    }

    /**
     * Returns the tiles associated with this KnightWorld.
     */
    public TETile[][] getTiles() {
        return tiles;
    }

    private void generateEmptyWorld(int width, int height) {
        tiles = new TETile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Tileset.LOCKED_DOOR;
            }
        }
    }

    private void createSquare(int x, int y, int width, int height, int holeSize) {
        int doubleHoleSize = holeSize * 2;
        //top left
        makeHole(x, y + doubleHoleSize, width, height, holeSize);
        // right top
        makeHole(x + doubleHoleSize, y + holeSize, width, height, holeSize);
        // bottom right
        makeHole(x + holeSize, y - holeSize, width, height, holeSize);
        // left bottom
        makeHole(x - holeSize, y, width, height, holeSize);
    }

    private void makeHole(int x, int y, int width, int height, int holeSize) {
        for (int i = 0; i < holeSize; i++) {
            for (int k = 0; k < holeSize; k++) {
                if (isValid(x + i, y + k, width, height)) {
                    tiles[x + i][y + k] = Tileset.NOTHING;
                }
            }
        }
    }

    private void makeBoardTopRightCorner(int startX, int startY, int width, int height, int holeSize) {
        while (startX >= 0) {
            int x = startX;
            int y = startY;
            while (x >= 0) {
                createSquare(x, y, width, height, holeSize);
                y += holeSize;
                x -= holeSize * 2;
            }
            x = startX;
            y = startY;
            while (isValid(x, y, width, height)) {
                createSquare(x, y, width, height, holeSize);
                y -= holeSize;
                x += holeSize * 2;
            }
            startY -= holeSize * 2;
            startX -= holeSize;
        }
    }

    private boolean isValid(int x, int y, int width, int height) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = WIDTH;
        int height = HEIGHT;
        int holeSize = 3;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
