package com.game.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Inputs extends JPanel {
    private SudokuUI sudokuBoard;
    private JPanel block;
    private JButton inputButton;
    private int numberOfInput;
    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;
    private JButton five;
    private JButton six;
    private JButton seven;
    private JButton eight;
    private JButton nine;

    public Inputs(SudokuUI board, JPanel block, JButton inButton, int numberOfInput) {
        this.sudokuBoard = board;
        this.block = block;
        this.inputButton = inButton;
        this.numberOfInput = numberOfInput;

        initComponents();

        if(numberOfInput < 7) {
            this.remove(seven);
            this.remove(eight);
            this.remove(nine);
        }
    }

    private void initComponents() {
        one = new JButton();
        two = new JButton();
        three = new JButton();
        four = new JButton();
        five = new JButton();
        six = new JButton();
        seven = new JButton();
        eight = new JButton();
        nine = new JButton();

        setLayout(new GridLayout(3, 3));

        configure(one, "1");
        add(one);

        configure(two, "2");
        add(two);

        configure(three, "3");
        add(three);

        configure(four, "4");
        add(four);

        configure(five, "5");
        add(five);

        configure(six, "6");
        add(six);

        configure(seven, "7");
        add(seven);

        configure(eight, "8");
        add(eight);

        configure(nine, "9");
        add(nine);
    }

    private void configure(JButton button, String text) {
        button.setBackground(new Color(255, 255, 255));
        button.setText(text);
        button.setAlignmentY(0.0F);
        button.setIconTextGap(1);
        button.setMargin(new Insets(2,2,2,2));
        button.setMinimumSize(new Dimension(15,15));
        button.addActionListener(this::setAns);
    }

    private void setAns(ActionEvent event) {
        this.sudokuBoard.setInput(event.getActionCommand(), block, inputButton);
    }

}
