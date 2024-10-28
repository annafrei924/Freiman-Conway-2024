package freiman.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GridFrame extends JFrame {
    private final Timer timer;
    private final JButton playAndPause;
    private final JButton next;
    private final JButton clear;
    private final JButton reset;
    private boolean isPlaying;

    private static final int gridSpacing = 7;

    private final Grid grid = new Grid(100, 100);
    private Timer timer;

    public GridFrame() {
        setSize(grid.getWidth() * gridSpacing, grid.getHeight() * gridSpacing);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridComponent gridComponent = new GridComponent(grid);
        RleParser parser = new RleParser(grid.getGrid());
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
                RleParser reader = new RleParser(grid, paste.toString());
                reader.loadFromRle();
                reader.fillGrid();
                repaint();
            } catch (UnsupportedFlavorException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

}
