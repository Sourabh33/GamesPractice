package com.spacemaster.game.utils;

import com.spacemaster.game.utils.GameHelper;

import java.io.*;

public class HighScoreUtil {

    String path = System.getProperty(GameHelper.PATH) + "/Documents";

    public void setScore(int score) {
        try {
            PrintWriter printWriter = new PrintWriter(path + "/highScoreLog.txt", "UTF-8");
            printWriter.println(score);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScore() throws IOException {
        String line = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path + "/highScoreLog.txt"));
        } catch (IOException e) {
            PrintWriter writer = new PrintWriter(path + "/highScoreLog.txt", "UTF-8");
            writer.println(0);
            writer.close();
            br = new BufferedReader(new FileReader(path + "/highScoreLog.txt"));
        }
        line = br.readLine();
        br.close();
        return Integer.parseInt(line);
    }
}
