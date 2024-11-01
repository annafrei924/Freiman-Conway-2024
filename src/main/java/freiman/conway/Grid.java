package freiman.conway;


public class Grid {

    private int[][] field;
    private int[][] start;

    public Grid(int width, int height) {
        field = new int[height][width];
    }

    public int getWidth() {
        return field[0].length;
    }

    public int getHeight() {
        return field.length;
    }


    public boolean isAlive(int x, int y) {
        return field[y][x] == 1;
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
                if (neighbors == 3 || (field[y][x] == 1 && neighbors == 2)) {
                    next[y][x] = 1;
                }
            }
        }

        field = next;
    }

    /**
     * Sets the value in field to be 1
     */
    public void put(int x, int y)
    {
        field[y][x] = 1;
    }

    /**
     * Sets the value in the field to be 0
     */
    public void remove(int x, int y) {
        field[y][x] = 0;
    }

    /**
     * Clears the field
     */
    public void clear() {
        field = new int [getHeight()][getWidth()];
    }
    
    public void setStart() {
        start = field;
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
