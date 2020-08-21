package se206.a2;

import java.io.*;

public class GameModelPersistence {
    private final String _saveFile;

    public GameModelPersistence(String saveFile) {
        _saveFile = saveFile;
    }

    public GameModel load() {
        try {
            FileInputStream file = new FileInputStream(_saveFile);
            ObjectInputStream in = new ObjectInputStream(file);

            GameModel model = (GameModel) in.readObject();

            in.close();
            file.close();

            return model;
        }
        catch (FileNotFoundException e) {
            System.out.println("No save file found");
            return null;
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves the GameModel to disk
     *
     * @param model model to save
     * @return true if saving successful, else false
     */
    public boolean save(GameModel model) {
        try {
            FileOutputStream file = new FileOutputStream(_saveFile);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(model);

            out.close();
            file.close();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
