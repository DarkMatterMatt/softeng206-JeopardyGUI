package se206.a2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        catch (IOException ex) {
            System.out.println("No save file found");
            return null;
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Failed deserializing GameModel, ClassNotFoundException");
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
            System.out.println(e.getMessage());
            return false;
        }
    }
}
