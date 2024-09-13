package freiman.conway;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridFrame extends JFrame {
    int gridSpacing = 15;

    private final Grid grid = new Grid(50, 50);
    private Timer timer;  // Class-level Timer field


    public GridFrame() {
        setSize(grid.getWidth()*gridSpacing, grid.getHeight()*gridSpacing);
        setTitle("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        GridComponent gridComponent = new GridComponent(grid);
        add(gridComponent, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton clearButton = new JButton("Clear");
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

        pauseButton.addActionListener(e -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        });

        clearButton.addActionListener(e -> {
            grid.clear();
            repaint();
        });

        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

}
