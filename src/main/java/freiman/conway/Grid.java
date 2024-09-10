package freiman.conway;

import java.util.Random;

public class Grid {

    private int[][] field;

    public Grid(int width, int height) {
        field = new int[height][width];
    }

    public int getWidth() {
        return field[0].length;
    }

    public int getHeight() {
        return field.length;
    }

    /**
     * Creates next generation of cells
     */
    public void nextGen() {
        int[][] next = new int [getHeight()][getWidth()];
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                //finds neighbors
                int neighbors = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        if ((y + i >= 0 && y + i < getHeight()) && (x + j >= 0 && x + j < getWidth())) {
                            if (field[y + i][x + j] == 1) {
                                neighbors++;
                            }
                        }
                    }
                }

                //cell dies from loneliness or overpopulation
                if (field[y][x] == 1 && (neighbors < 2 || neighbors >= 4)) {
                    next[y][x] = 0;
                } else if (field[y][x] == 0 && neighbors == 3) {
                    next[y][x] = 1;
                } else {
                    next[y][x] = field[y][x];
                }
            }
        }

        field = next;
    }

    /**
     * Sets the value in field to be 1
     */
    public void put(int x, int y) {
        field[y][x] = 1;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[y].length; x++) {
                builder.append(field[y][x]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
