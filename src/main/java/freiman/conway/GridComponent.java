package freiman.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridComponent extends JComponent {
    private final Grid grid;
    private final int cellSize = 7;


    public GridComponent(Grid grid) {
        this.grid = grid;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int posX = e.getX() / cellSize;
                int posY = e.getY() / cellSize;
                if (!grid.isAlive(posX, posY)) {
                    grid.put(posX, posY);
                } else {
                    grid.remove(posX, posY);
                }

                repaint();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, grid.getWidth() * cellSize, grid.getHeight() * cellSize);
        //draw the boxes
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.isAlive(x, y)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    public int getCellSize() {
        return cellSize;
    }

}
