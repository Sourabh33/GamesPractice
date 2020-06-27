package com.game.sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;

public class SudokuUI extends JFrame {
    private JButton[][] buttons;
    private ActionListener[][] actionListener;
    private JPanel[][] blocks;
    private Sudoku sudoku;
    private int gameMode;
    private int grid;
    private final StopWatch stopWatch;
    private boolean paused;

    // variables start
    private JPanel base;
    private JPanel options;
    private JButton newGameButton;
    private JButton resetGameButton;
    private JButton pauseButton;
    private JLabel timeLabel;
    private JButton resumeButton;
    private JButton submitButton;
    private JPanel holder;
    private JPanel board;
    private JMenuBar menu;
    private JMenu game;
    private JMenuItem newGame;
    private JMenuItem newGame6X6;
    private JMenuItem resetGame;
    private JPopupMenu.Separator jSeparator1;
    private JMenuItem exit;
    private JMenu jMenu2;
    private JRadioButtonMenuItem beginner;
    private JRadioButtonMenuItem intermediator;
    private JRadioButtonMenuItem expert;
    private JMenu help;
    private JMenuItem about;
    // variables end

    public SudokuUI() {
        sudoku = new Sudoku();
        gameMode = Sudoku.GAME_MODE_MEDIUM;
        grid = Sudoku.GRID_9X9;
        stopWatch = new StopWatch();
        paused = false;
        initComponents();
        initialize();
        startTimer();
    }

    private void initComponents() {
        // panels
        base = new JPanel();
        options = new JPanel();
        holder = new JPanel();
        board = new JPanel();

        // menu
        menu = new JMenuBar();
        game = new JMenu();
        newGame = new JMenuItem();
        newGame6X6 = new JMenuItem();
        resetGame = new JMenuItem();
        exit = new JMenuItem();

        jMenu2 = new JMenu();
        beginner = new JRadioButtonMenuItem();
        intermediator = new JRadioButtonMenuItem();
        expert = new JRadioButtonMenuItem();

        help = new JMenu();
        about = new JMenuItem();

        // buttons
        newGameButton = new JButton();
        resetGameButton = new JButton();
        pauseButton = new JButton();
        resumeButton = new JButton();
        submitButton = new JButton();

        // labels
        timeLabel = new JLabel();

        // separator
        jSeparator1 = new JPopupMenu.Separator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sudoku Game");
        setBounds(new Rectangle(0, 0, 0, 0));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setMinimumSize(new Dimension(500, 500));

        base.setBackground(new Color(204, 204, 204));
        base.setAlignmentX(0.0F);
        base.setAlignmentY(0.0F);

        options.setBackground(new Color(255, 255, 255));

        // new game button
        setButtonConfigs(newGameButton, "New Game");
        newGameButton.setMaximumSize(new Dimension(63, 19));
        newGameButton.setMinimumSize(new Dimension(63, 19));
        newGameButton.setPreferredSize(new Dimension(63, 19));
        newGameButton.addActionListener(this::newGameButtonAction);

        // reset button
        setButtonConfigs(resetGameButton, "Reset Game");
        resetGameButton.addActionListener(this::resetButtonAction);

        //pause button
        setButtonConfigs(pauseButton, "Pause");
        pauseButton.addActionListener(this::pauseButtonAction);

        // resume button
        setButtonConfigs(resumeButton, "Resume");
        resumeButton.addActionListener(this::resumeButtonAction);

        // submit button
        setButtonConfigs(submitButton, "Submit");
        submitButton.addActionListener(this::submitButtonAction);

        // time label
        timeLabel.setFont(new Font("Tahome", Font.PLAIN, 24));
        timeLabel.setForeground(new Color(51, 51, 51));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setText("00:00:000");
        timeLabel.setIconTextGap(0);

        // layout
        GroupLayout optionsLayout = new GroupLayout(options);
        options.setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(
                optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(optionsLayout.createSequentialGroup()
                                .addComponent(newGameButton, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetGameButton, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resumeButton, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(timeLabel)
                                .addContainerGap())
        );

        optionsLayout.setVerticalGroup(
                optionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(optionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(newGameButton, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addComponent(resetGameButton, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addComponent(pauseButton, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addComponent(resumeButton, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addComponent(timeLabel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
        );


        holder.setBackground(new Color(255, 255, 255));
        holder.setAlignmentX(0.0F);
        holder.setAlignmentY(0.0F);
        holder.setLayout(new GridLayout(1, 1));

        board.setBackground(new Color(255, 255, 255));
        board.setAlignmentX(0.0F);
        board.setAlignmentY(0.0F);
        board.setMinimumSize(new Dimension(100, 100));
        board.setPreferredSize(new Dimension(100, 100));
        board.setLayout(new GridLayout(9, 9));

        GroupLayout baseLayout = new GroupLayout(base);
        base.setLayout(baseLayout);
        baseLayout.setHorizontalGroup(
                baseLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, baseLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(baseLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(holder, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                                        .addComponent(options, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        baseLayout.setVerticalGroup(
                baseLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(baseLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(options, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(holder, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                                .addContainerGap())
        );

        game.setText("Game");

        newGame.setText("New Game 9 X 9");
        newGame.addActionListener(this::newGamePanelAction);
        game.add(newGame);

        newGame6X6.setText("New Game 6 X 6");
        newGame6X6.addActionListener(this::newGame6X6PanelAction);
        game.add(newGame6X6);

        resetGame.setText("Reset Game");
        resetGame.addActionListener(this::resetGamePanelAction);
        game.add(resetGame);
        game.add(jSeparator1);

        exit.setText("Exit");
        exit.addActionListener(this::exitAction);
        game.add(exit);

        menu.add(game);

        jMenu2.setText("Level");

        beginner.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        beginner.setText("Beginner");
        beginner.addActionListener(this::beginnerAction);
        jMenu2.add(beginner);

        intermediator.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        intermediator.setSelected(true);
        intermediator.setText("intermediator");
        intermediator.addActionListener(this::intermediateAction);
        jMenu2.add(intermediator);

        expert.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        expert.setText("Expert");
        expert.addActionListener(this::expertAction);
        jMenu2.add(expert);

        menu.add(jMenu2);

        help.setText("Help!");
        about.setText("About!");
        about.addActionListener(this::aboutAction);
        help.add(about);

        menu.add(help);

        setJMenuBar(menu);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(base, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(base, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }

    private void setButtonConfigs(JButton jButton, String text) {
        jButton.setBackground(new Color(255, 255, 255));
        jButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jButton.setText(text);
        jButton.setMargin(new Insets(0, 0, 0, 0));
    }

    private void newGameButtonAction(ActionEvent event) {
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);
    }

    private void resetButtonAction(ActionEvent event) {
        int[][] resetPuzzle = sudoku.resetPuzzle();
        createBoard(resetPuzzle);
    }

    private void pauseButtonAction(ActionEvent event) {
        stopWatch.pause();
        paused = true;
        showMessage("Paused");
    }

    private void resumeButtonAction(ActionEvent event) {
        stopWatch.resume();
        paused = false;
        holder.removeAll();
        holder.add(board);
        holder.repaint();
        this.setVisible(true);
    }

    private void submitButtonAction(ActionEvent event) {
        if (!isAnswerComplete()) {
            JOptionPane.showMessageDialog(this, "Please complete your answer");
        } else {
            stopWatch.stop();
            boolean isAnswerCorrect = sudoku.check(getAnswer());
            String message = "";
            if (isAnswerCorrect) {
                message = "Congratulation You have won the Game in " + timeLabel.getText();
            } else {
                message = "Sorry You have failed. ";
            }
            showMessage(message);
        }
    }

    private void newGamePanelAction(ActionEvent event) {
        grid = Sudoku.GRID_9X9;
        int[][] puzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(puzzle);
    }

    private void newGame6X6PanelAction(ActionEvent event) {
        grid = Sudoku.GRID_6X6;
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);
    }

    private void resetGamePanelAction(ActionEvent event) {
        int[][] puzzle = sudoku.resetPuzzle();
        createBoard(puzzle);
    }

    private void exitAction(ActionEvent event) {
        System.exit(0);
    }

    private void beginnerAction(ActionEvent event) {
        gameMode = Sudoku.GAME_MODE_EASIER;
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);
    }

    private void intermediateAction(ActionEvent event) {
        gameMode = Sudoku.GAME_MODE_MEDIUM;
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);
    }

    private void expertAction(ActionEvent event) {
        gameMode = Sudoku.GAME_MODE_EXPERT;
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);
    }

    private void aboutAction(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Version: 1.0.0");
    }

    private void createBoard(int[][] puzzle) {
        board.removeAll();
        grid = puzzle.length;
        blocks = new JPanel[grid][grid];
        buttons = new JButton[grid][grid];
        actionListener = new ActionListener[grid][grid];
        board.setLayout(new GridLayout(grid, grid, 3, 3));

        int rowsInGrid = grid == 9 ? 3 : 2;

        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                blocks[i][j] = new JPanel();
                buttons[i][j] = new JButton();
                String text = "";
                if (0 < puzzle[i][j] && puzzle[i][j] <= grid) {
                    text += puzzle[i][j];
                } else {
                    final JButton tempbutton = buttons[i][j];
                    final JPanel tempBlock = blocks[i][j];
                    actionListener[i][j] = new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            viewInputs(tempBlock, tempbutton, grid);
                        }
                    };
                    buttons[i][j].addActionListener(actionListener[i][j]);
                }
                buttons[i][j].setText(text);
                buttons[i][j].setFont(new Font("Tahoma", 0, 24));

                if (((0 <= i && i < rowsInGrid) || (rowsInGrid * 2 <= i && i < grid)) && (3 <= j && j < 6)) {
                    buttons[i][j].setBackground(new Color(204, 204, 204));
                } else if ((rowsInGrid <= i && i < rowsInGrid * 2) && ((0 <= j && j < 3) || (6 <= j && j < 9))) {
                    buttons[i][j].setBackground(new Color(204, 204, 204));
                } else {
                    buttons[i][j].setBackground(new Color(255, 255, 255));
                }
                blocks[i][j].setLayout(new GridLayout(1, 1));
                blocks[i][j].add(buttons[i][j]);
                board.add(blocks[i][j]);
            }
        }

        holder.removeAll();
        holder.add(board);
        board.repaint();
        holder.repaint();
        this.setVisible(true);
        stopWatch.start();

    }

    private void initialize() {
        int[][] newPuzzle = sudoku.getNewPuzzle(grid, gameMode);
        createBoard(newPuzzle);

    }

    private void viewInputs(JPanel block, JButton inputButton, int numOfInputs) {
        JPanel inputs = new Inputs(this, block, inputButton, numOfInputs);
        block.remove(inputButton);
        block.add(inputs);
        this.setVisible(true);
    }

    public void setInput(String answer, JPanel block, JButton inputButton) {
        block.removeAll();
        inputButton.setText(answer);
        inputButton.setFont(new Font("Tahoma", Font.BOLD, 24));
        block.add(inputButton);
        this.repaint();
    }

    private int[][] getAnswer() {
        int[][] answer = new int[grid][grid];
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                try {
                    answer[i][j] = Integer.parseInt(buttons[i][j].getText());
                } catch (NumberFormatException e) {
                    answer[i][j] = 0;
                }
            }
        }
        return answer;
    }

    private boolean isAnswerComplete() {
        boolean isAnsComplete = true;
        for (int i = 0; i < grid; i++) {
            for (int j = 0; j < grid; j++) {
                try {
                    Integer.parseInt(buttons[i][j].getText());
                } catch (NumberFormatException e) {
                    isAnsComplete = false;
                    break;
                }
            }
        }
        return isAnsComplete;

    }

    private void showMessage(String text) {
        JLabel messageLabel = new JLabel();
        messageLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        messageLabel.setText(text);
        holder.removeAll();
        holder.add(messageLabel);
        holder.repaint();
        this.setVisible(true);
    }

    private void startTimer() {
        Thread thread = new Thread(() -> {
            stopWatch.start();
            while (true) {
                if(!paused){
                    final String timeString = new SimpleDateFormat("mm:ss:SSS").format(stopWatch.getElapsedTime());
                    timeLabel.setText("" + timeString);
                }
            }
        });

        thread.start();
    }


}
