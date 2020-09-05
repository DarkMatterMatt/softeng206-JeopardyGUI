package se206.a2;

import java.io.IOException;
import java.io.Serializable;

/**
 * Enables text-to-speech using multiple providers
 */
public class TextToSpeech implements Serializable {
    private static TextToSpeech _instance;
    private Command _command;
    private boolean _muted = false;

    private TextToSpeech() {
        // start looking for installed TTS providers in the background
        new Thread(this::getCommand).start();
    }

    public static TextToSpeech getInstance() {
        if (_instance == null) {
            _instance = new TextToSpeech();
        }
        return _instance;
    }

    private Process executeInBackground(String... cmd) {
        try {
            return new ProcessBuilder(cmd).start();
        }
        catch (IOException e) {
            return null;
        }
    }

    /**
     * Check if various TTS providers are installed
     * @return an installed TTS provider, or Command.NO_COMMAND_EXISTS
     */
    private Command getCommand() {
        synchronized (this) {
            if (_command == null) {
                Process espeak = executeInBackground("espeak", "-h");
                Process festival = executeInBackground("festival --version");

                // wait for espeak to finish, check return code
                if (espeak != null) {
                    try {
                        if (espeak.waitFor() == 0) {
                            _command = Command.ESPEAK;
                        }
                    }
                    catch (InterruptedException e) {
                        //
                    }
                }
                // wait for festival to finish, check return code
                if (festival != null && _command == null) {
                    try {
                        if (festival.waitFor() == 0) {
                            _command = Command.FESTIVAL;
                        }
                    }
                    catch (InterruptedException e) {
                        //
                    }
                }
                // no support commands
                if (_command == null) {
                    _command = Command.NO_COMMAND_EXISTS;
                }
            }
            return _command;
        }
    }

    public boolean isMuted() {
        return _muted;
    }

    public void setMuted(boolean muted) {
        _muted = muted;
    }

    /**
     * Speak text using the installed TTS provider
     */
    public void speak(String text) {
        if (_muted) {
            return;
        }

        switch (getCommand()) {
            case ESPEAK:
                executeInBackground("espeak", text);
                break;
            case FESTIVAL:
                //System.out.println("Festival detected.");
                break;
            case NO_COMMAND_EXISTS:
                //System.out.println("No supported TTS providers found.");
                break;
            default:
                System.out.println("Programming error. Missing command implementation.");
        }
    }

    public enum Command {
        NO_COMMAND_EXISTS,
        ESPEAK,
        FESTIVAL,
    }
}
