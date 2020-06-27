package com.game.sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuMain {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new SudokuUI();
            frame.setVisible(true);
        });
    }
}
