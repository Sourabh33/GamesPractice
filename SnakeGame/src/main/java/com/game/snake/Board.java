package com.game.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 7667534964739375983L;
	
	private static final int B_WIDTH = 500;
	private static final int B_HEIGHT = 500;
	private static final int DOT_SIZE = 10;
	private static final int ALL_DOTS = 900;
	private static final int RAND_POS = 29;
	private static final int DELAY = 140;

	private final int X[] = new int[ALL_DOTS];
	private final int Y[] = new int[ALL_DOTS];
	
	private int dots;
	private int apple_x;
	private int apple_y;
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	private boolean isPaused = false;
	
	private Timer timer;
	private Image ball;
	private Image apple;
	private Image head;

	JButton restartButton;

	public Board(JButton restartButton) {
		this.restartButton = restartButton;
		initBoard();
	}

	private void initBoard() {
		add(restartButton);
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
		
	}
	
	private void loadImages() {
		URL dotUrl = getImageUrl("/dot.png");
		ImageIcon dotImage = dotUrl != null ? new ImageIcon(dotUrl) : new ImageIcon("src/main/resources/dot.png");
		ball = dotImage.getImage();

		URL appleUrl = getImageUrl("/apple.png");
		ImageIcon appleImage = appleUrl != null ? new ImageIcon(appleUrl) : new ImageIcon("src/main/resources/apple.png");
		apple = appleImage.getImage();

		URL headUrl = getImageUrl("/head.png");
		ImageIcon headImage = headUrl != null ? new ImageIcon(headUrl) : new ImageIcon("src/main/resources/head.png");
		head = headImage.getImage();
	}

	private URL getImageUrl(String path) {
		return Board.class.getResource(path);
	}
	
	private void initGame() {
		dots = 3;
		
		for(int z = 0; z < dots; z++){
			X[z] = 50 - z * 10;
			Y[z] = 50;
		}
		
		locateApple();
		
		timer = new Timer(DELAY, this);
		timer.start();
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private void doDrawing(Graphics g) {
		if(inGame && !isPaused){
			g.drawImage(apple, apple_x, apple_y, this);
			for(int z = 0; z < dots ;z++){
				if(z == 0){
					g.drawImage(head, X[z], Y[z], this);
				}else{
					g.drawImage(ball, X[z], Y[z], this);
				}				
			}
			
			Toolkit.getDefaultToolkit().sync();
		} else if(isPaused) {
			inGame = false;
			pauseGame(g);
		} else {
			gameOver(g);
		}
		
	}

	private void pauseGame(Graphics g) {
		String message = "Paused";
		Font small = new Font("HalVetica", Font.BOLD, 14);


		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (B_WIDTH - metr.stringWidth(message)) / 2, B_HEIGHT / 2);
	}

	private void gameOver(Graphics g) {
		restartButton.setVisible(true);
		String message = "Game Over " + "You have touched : " + (dots - 3);
		Font small = new Font("HalVetica", Font.BOLD, 14);
		
		
		FontMetrics metr = getFontMetrics(small);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (B_WIDTH - metr.stringWidth(message)) / 2, B_HEIGHT / 2);
		
	}

	private void checkApple(){
		if((X[0] == apple_x) && (Y[0] == apple_y)){
			dots++;
			locateApple();
		}
	}
	
	private void move(){
		
		for(int z = dots; z > 0; z--){
			X[z] = X[z - 1];
			Y[z] = Y[z - 1];
		}
		
		if(leftDirection){
			X[0] -= DOT_SIZE;
		}
		
		if(rightDirection){
			X[0] += DOT_SIZE;
		}
		
		if(upDirection){
			Y[0] -= DOT_SIZE;
		}
		
		if(downDirection){
			Y[0] += DOT_SIZE;
		}
	}
	
	private void checkCollision(){
		
		for(int z = dots; z > 0; z--){
			if((z > 4) && (X[0] == X[z]) && (Y[0] == Y[z])){
				inGame = false;
			}
		}
		
		if(Y[0] >= B_HEIGHT){
			inGame = false;
		}
		
		if(Y[0] < 0){
			inGame = false;
		}
		
		if(X[0] >= B_WIDTH){
			inGame = false;
		}
		
		if(X[0] < 0){
			inGame = false;
		}
		
		if(!inGame){
			timer.stop();
		}
	}
	
	private void locateApple() {
		int r = (int)(Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));
		
		r = (int)(Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(inGame){
			
			checkApple();
			checkCollision();
			move();
		}
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

//			System.out.println(e.getSource());
//			if(e.getSource() == restartButton) {
//				System.out.println();
//				JFrame newGame = new Snake();
//				System.out.println(newGame);
//				newGame.setVisible(true);
//			}

			if(key == KeyEvent.VK_SPACE) {
				inGame = !inGame;
				isPaused = !isPaused;
			}
			
			if((key == KeyEvent.VK_LEFT) && (!rightDirection)){
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if((key == KeyEvent.VK_RIGHT) && (!leftDirection)){
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if((key == KeyEvent.VK_UP) && (!downDirection)){
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			
			if((key == KeyEvent.VK_DOWN) && (!upDirection)){
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
	
	

}
