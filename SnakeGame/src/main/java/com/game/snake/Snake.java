package com.game.snake;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Snake extends JFrame implements ActionListener {
	JButton restartButton;

	public Snake() {
		restartButton = new JButton("Restart");
		restartButton.setVisible(false);
		restartButton.addActionListener(this);
		initUi(restartButton);
	}

	private void initUi(JButton restartButton) {
		
		add(new Board(restartButton));
		
		setResizable(false);
		pack();
		
		setTitle("Snake Game");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new Snake();
			frame.setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == restartButton) {
			this.dispose();
			JFrame newGame = new Snake();
			newGame.setVisible(true);
		}
	}
}
