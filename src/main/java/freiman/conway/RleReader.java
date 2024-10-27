package freiman.conway;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleParser {
    private Grid grid;
    private String input;

    private String[] arr;

    public RleParser(Grid grid, String input) {
        this.grid = grid;
        this.input = input;
        arr = new String[3];
    }

    public void loadFromRle(String data) throws IOException {
        StringBuilder rle = new StringBuilder();
        String regex = "x = (\\d+), y = (\\d+), rule = (.+)";
        Pattern pattern = Pattern.compile(regex);
        BufferedReader reader = null;

        if (isValidUrl(input)) {
            URL url = new URL(input);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
        } else if (isFilePath(input)) {
            reader = new BufferedReader(new FileReader(input));
        } else {
            reader = new BufferedReader(new StringReader(input.replace("\r\n", "\n")));
            System.out.println(input);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#") && !line.startsWith("x")) {
                rle.append(line);
            } else if (line.startsWith("x")) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    arr[0] = matcher.group(1);
                    arr[1] = matcher.group(2);
                }
            }
        }
        arr[2] = rle.toString();
        reader.close();
    }

    public void fillGrid() {

        int rleWidth = Integer.parseInt(arr[0]);
        int rleHeight = Integer.parseInt(arr[1]);
        String rleString = arr[2];

        int initialRow = 0;
        if (grid.getWidth() != rleWidth) {
            initialRow = grid.getWidth() / 2 - rleWidth / 2;
        }

        int initalCol = 0;
        if (grid.getHeight() != rleHeight) {
            initalCol = grid.getHeight() / 2 - rleHeight / 2;
        }

        int row = initialRow;
        int col = initalCol;
        int count = 0;

        for (int i = 0; i < rleString.length(); i++) {
            char c = rleString.charAt(i);
            if (Character.isDigit(c)) {
                count = Integer.parseInt(String.valueOf(c));
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
                            if (row < grid.getHeight() && col < grid.getWidth()) {
                                grid.put(col, row);
                                col++;
                            }
                        }
                        break;
                    case '$':
                        row += count;
                        col = initalCol;
                        ;
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

    private boolean isValidUrl(String input) {
        try {
            new URL(input).toURI();
            return true;
        } catch (URISyntaxException | IllegalArgumentException | MalformedURLException e) {
            return false;
        }
    }

    private boolean isFilePath(String input) {
        File file = new File(input);
        return file.exists() && file.isFile();
    }
}
