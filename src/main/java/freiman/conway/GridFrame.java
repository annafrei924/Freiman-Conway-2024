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
    private static final int gridSpacing = 7;

    private final Grid grid = new Grid(100, 100);
    private Timer timer;

    public GridFrame() {
        setSize(grid.getWidth() * gridSpacing, grid.getHeight() * gridSpacing);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridComponent gridComponent = new GridComponent(grid);
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



        JTextField rleField = new JTextField(50);
        JButton pasteButton = new JButton("Paste");
        JPanel rlePanel = new JPanel();
        rlePanel.add(rleField);
        rlePanel.add(pasteButton);
        add(rlePanel, BorderLayout.NORTH);

        pasteButton.addActionListener(e -> {
            try {
                Object paste = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                RleReader reader = new RleReader(grid, paste.toString());
                reader.readFile();
                reader.fillGrid();
                if (paste.toString().endsWith(".rle")) {
                    rleField.setText(paste.toString());
                }
                repaint();
            } catch (UnsupportedFlavorException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        rleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    RleReader rleReader = new RleReader(grid, rleField.getText());
                    try {
                        rleReader.readFile();
                        rleReader.fillGrid();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    repaint();
                }
            }
        });


    }

}
