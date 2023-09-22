package com.space.ui.saveload;

import java.io.*;

public class LoadSettings {

    private static String fileString = "data/settings.txt";

    public static int ASTEROID_SPAWN_INTERVAL = 60;
    public static int MAX_ASTEROIDS = 40;
    public static double DIFFICULTY_INCREMENT = 0.05;

    private LoadSettings() {
        // no-op
    }

    public static void loadSettings() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileString));
            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                if (line.startsWith("#")) {
                    line = br.readLine();
                    continue;
                }
                if (line.contains("ASTEROID_SPAWN_INTERVAL=")) {
                    line = line.substring(line.lastIndexOf('=') + 1);
                    ASTEROID_SPAWN_INTERVAL = Integer.parseInt(line);
                }
                if (line.contains("MAX_ASTEROIDS=")) {
                    line = line.substring(line.lastIndexOf('=') + 1);
                    MAX_ASTEROIDS = Integer.parseInt(line);
                }
                if (line.contains("DIFFICULTY_INCREMENT=")) {
                    line = line.substring(line.lastIndexOf('=') + 1);
                    DIFFICULTY_INCREMENT = Double.parseDouble(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
