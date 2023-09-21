package com.space.frames;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SaveHandler {

    // static fields
    private static final String dataFilePath = "data/highscore.dat";

    // constructors
    private SaveHandler() {
        // no-op, full static method
    }

    // action methods
    public static int load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SaveHandler.dataFilePath))) {
            return in.readInt();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return 0;
    }

    public static void save(int score) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SaveHandler.dataFilePath))) {
            out.writeInt(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
