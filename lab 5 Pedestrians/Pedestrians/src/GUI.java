import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI extends JPanel implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Board board;
	private JButton start;
	private JButton clear;
	private JButton nei;
	private JComboBox<Integer> drawType;
	private JSlider pred;
	private JFrame frame;
	private int iterNum = 0;
	private final int maxDelay = 500;
	private final int initDelay = 100;
	private boolean running = false;

	private JTextPane type;

	public GUI(JFrame jf) {
		frame = jf;
		timer = new Timer(initDelay, this);
		timer.stop();
	}

	public void initialize(Container container) {
		container.setLayout(new BorderLayout());
		container.setSize(new Dimension(1024, 768));

		JPanel buttonPanel = new JPanel();

		start = new JButton("Start");
		start.setActionCommand("Start");
		start.addActionListener(this);

		clear = new JButton("Calc Field");
		clear.setActionCommand("clear");
		clear.addActionListener(this);

		nei = new JButton("Change neighborhood");
		nei.setActionCommand("change");
		nei.addActionListener(this);

		type = new JTextPane();
		type.setText("Moore's neighborhood");

		pred = new JSlider();
		pred.setMinimum(0);
		pred.setMaximum(maxDelay);
		pred.addChangeListener(this);
		pred.setValue(maxDelay - timer.getDelay());
		
		drawType = new JComboBox<Integer>(Point.types);
		drawType.addActionListener(this);
		drawType.setActionCommand("drawType");

		buttonPanel.add(start);
		buttonPanel.add(clear);
		buttonPanel.add(drawType);
		buttonPanel.add(pred);
		buttonPanel.add(nei);
		buttonPanel.add(type);

		board = new Board(1024, 768 - buttonPanel.getHeight());
		container.add(board, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(timer)) {
			iterNum++;
			frame.setTitle("Sound simulation (" + Integer.toString(iterNum) + " iteration)");
			board.iteration();

		} else {
			String command = e.getActionCommand();
			if (command.equals("Start")) {
				if (!running) {
					timer.start();
					start.setText("Pause");
					nei.setEnabled(false);

				} else {
					timer.stop();
					nei.setEnabled(true);
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
			}
			else if (command.equals("drawType")) {
				int newType = (Integer) drawType.getSelectedItem();
				board.editType = newType;
			}

			else if(command.equals("change")){

				board.neighborhood = (board.neighborhood + 1) % 2;
				if (board.neighborhood == 1) {
					type.setText("von Neumann's neighborhood");
				} else {
					type.setText("Moore's neighborhood");
				}

				board.changeNei();
				board.clear();
			}

		}
	}

	public void stateChanged(ChangeEvent e) {
		timer.setDelay(maxDelay - pred.getValue());
	}
}
