package freiman.conway;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /**
     * @param width
     * @param height
     * resizes and clears the grid
     */
    public void clear(int width, int height){
        field = new int[height][width];
    }

    /**
     * @param filePath
     * reads from an RLE file
     */
    public void readRle(String filePath) {
        StringBuilder rle = new StringBuilder();
        String line;
        String regex = "x = (\\d+), y = (\\d+), rule = (.+)";
        Pattern pattern = Pattern.compile(regex);

        try {
            URL url = new URL(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("x")) {
                    rle.append(line);
                } else if (line.startsWith("x")) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        int x = Integer.parseInt(matcher.group(1));
                        int y = Integer.parseInt(matcher.group(2));
                        clear(x, y);
                    }
                }
            }
            reader.close();
            rleToGrid(rle.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param rle - string with meaningful content in a rle file
     * Goes through the rle and fills the grid accordingly
     */
    public void rleToGrid(String rle) {
        int row = 0;
        int col = 0;
        int count = 0;

        for (int i = 0; i < rle.length(); i++) {
            char c = rle.charAt(i);
            if (Character.isDigit(c)) {
                count = Integer.parseInt(String.valueOf(c));;
            } else {
                if (count == 0) {
                    count = 1;
                }
                switch (c) {
                    case 'b':
                        col += count;
                        break;
                    case 'o':
                        for (int j = 0; j < count; j++) {
                            if (row < getHeight() && col < getWidth()) {
                                field[row][col] = 1;
                                col++;
                            }
                        }
                        break;
                    case '$':
                        row += count;
                        col = 0;
                        break;
                    case '!':
                        return;
                    default:
                        System.out.println("Invalid character: " + c);
                        break;
                }
                count = 0;
            }
        }
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
