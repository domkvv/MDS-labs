import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class containing GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Board board;
    private JButton start;
    private JButton clear;
    private JButton selection;
    private JSlider pred;
    private JFrame frame;
    private int iterNum = 0;
    private final int maxDelay = 500;
    private final int initDelay = 100;
    private boolean running = false;
    private int mode;

    public GUI(JFrame jf, int mode) {
        frame = jf;
        timer = new Timer(initDelay, this);
        timer.stop();
        this.mode = mode;
    }

    /**
     * @param container to which GUI and board is added
     */
    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JPanel buttonPanel = new JPanel();

        start = new JButton("Start");
        start.setActionCommand("Start");
        start.setToolTipText("Starts clock");
        start.addActionListener(this);

        clear = new JButton("Clear");
        clear.setActionCommand("clear");
        clear.setToolTipText("Clears the board");
        clear.addActionListener(this);

        selection = new JButton("Back to selection");
        selection.setActionCommand("back");
        selection.addActionListener(this);

        pred = new JSlider();
        pred.setMinimum(0);
        pred.setMaximum(maxDelay);
        pred.setToolTipText("Time speed");
        pred.addChangeListener(this);
        pred.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(selection);
        buttonPanel.add(start);
        buttonPanel.add(clear);
        buttonPanel.add(pred);

        this.board = new Board(1024, 768 - buttonPanel.getHeight());
        container.add(this.board, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
        this.board.setSelection(mode);
    }

    /**
     * handles clicking on each button
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            String title;
            iterNum++;
            if (mode == 1) {
                title = "Game of Life - classic (" + Integer.toString(iterNum) + " iteration)";
            } else if (mode == 2) {
                title = "Game of Life - cities (" + Integer.toString(iterNum) + " iteration)";
            } else if (mode == 3) {
                title = "Game of Life - coral (" + Integer.toString(iterNum) + " iteration)";
            } else {
                title = "Rain (" + Integer.toString(iterNum) + " iteration)";
            }
            frame.setTitle(title);
            board.iteration();

        } else {
            String command = e.getActionCommand();
            if (command.equals("Start")) {
                if (!running) {
                    timer.start();
                    start.setText("Pause");

                } else {
                    timer.stop();
                    start.setText("Start");
                }
                running = !running;
                clear.setEnabled(true);

            } else if (command.equals("clear")) {
                iterNum = 0;
                timer.stop();
                start.setEnabled(true);
                board.clear();
                frame.setTitle("Cellular Automata Toolbox");
            } else if (command.equals("back")) {
                frame.dispose();
            }

        }
    }

    /**
     * slider to control simulation speed
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - pred.getValue());
    }
}
