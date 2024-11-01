
package freiman.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;

public class GridFrame extends JFrame {
    private  Timer timer;

    public GridFrame(Grid grid) {
        setSize(800, 800);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int cellsize = Math.min(getHeight() / grid.getHeight(), getWidth() / grid.getWidth());

        GridComponent gridComponent = new GridComponent(grid, cellsize);
        RleParser parser = new RleParser(grid);
        GridController controller = new GridController(grid, gridComponent, parser);
        gridComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }
        });

        gridComponent.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                controller.toggleCell(e.getX(), e.getY());
            }
        });

        add(gridComponent, BorderLayout.CENTER);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> {
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    grid.nextGen();
                    repaint();
                }
            });
            timer.start();
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
            grid.clear();
            repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JButton pasteButton = new JButton("Paste");
        JPanel rlePanel = new JPanel();
        rlePanel.add(pasteButton);
        add(rlePanel, BorderLayout.NORTH);

        pasteButton.addActionListener(e -> {
            try {
                Object paste = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                parser.loadFromRle(paste.toString());
                parser.fillGrid();
                repaint();
            } catch (UnsupportedFlavorException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

}