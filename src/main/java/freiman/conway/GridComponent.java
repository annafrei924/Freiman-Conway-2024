package freiman.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GridComponent extends JComponent {
    private Grid grid;
    private final int gridSpacing = 15;


    public GridComponent(Grid grid) {
        this.grid = grid;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                grid.put(e.getX()/gridSpacing, e.getY()/gridSpacing);
                System.out.println("Mouse clicked at: (" + e.getX()/gridSpacing + ", " + e.getY()/gridSpacing + ")");
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

        //draw the boxes
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                if (grid.isAlive(x, y)) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * gridSpacing, y * gridSpacing, gridSpacing, gridSpacing);
                }
                else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x*gridSpacing, y*gridSpacing,gridSpacing, gridSpacing);
                }
            }
        }

    }
}
