package freiman.conway;

import javax.swing.*;
import java.awt.*;


public class GridComponent extends JComponent {
    private final Grid grid;
    private final int cellSize;

    public GridComponent(Grid grid, int cellSize) {
        this.grid = grid;
        this.cellSize = cellSize;

        grid.setStart();
    }

@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.isAlive(x, y)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public int getCellSize()
    {
        return cellSize;
    }
}
