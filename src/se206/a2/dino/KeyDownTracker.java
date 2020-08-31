package se206.a2.dino;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class KeyDownTracker {
    private static final KeyDownTracker _instance = new KeyDownTracker();
    private final HashMap<KeyCode, Long> _keyDownSince = new HashMap<>();
    // 'null' HashMap key is used to represent a listener for any keyboard key
    private final HashMap<KeyCode, ArrayList<Consumer<KeyEvent>>> _pressConsumers = new HashMap<>();
    private final HashMap<KeyCode, ArrayList<Consumer<KeyEvent>>> _releaseConsumers = new HashMap<>();

    private KeyDownTracker() {
    }

    public static KeyDownTracker getInstance() {
        return _instance;
    }

    private void addListener(HashMap<KeyCode, ArrayList<Consumer<KeyEvent>>> map, Consumer<KeyEvent> consumer, KeyCode key) {
        if (!map.containsKey(key)) map.put(key, new ArrayList<>());
        ArrayList<Consumer<KeyEvent>> consumers = map.get(key);
        if (!consumers.contains(consumer)) consumers.add(consumer);
    }

    private void removeListener(HashMap<KeyCode, ArrayList<Consumer<KeyEvent>>> map, Consumer<KeyEvent> consumer, KeyCode key) {
        ArrayList<Consumer<KeyEvent>> consumers = map.get(key);
        if (consumers == null) return;
        consumers.remove(consumer);
    }

    public void addPressListener(Consumer<KeyEvent> consumer, KeyCode ...keys) {
        Arrays.stream(keys).forEach(k -> addListener(_pressConsumers, consumer, k));
    }

    public void removePressListener(Consumer<KeyEvent> consumer, KeyCode ...keys) {
        Arrays.stream(keys).forEach(k -> removeListener(_pressConsumers, consumer, k));
    }

    public void addReleaseListener(Consumer<KeyEvent> consumer, KeyCode ...keys) {
        Arrays.stream(keys).forEach(k -> addListener(_releaseConsumers, consumer, k));
    }

    public void removeReleaseListener(Consumer<KeyEvent> consumer, KeyCode ...keys) {
        Arrays.stream(keys).forEach(k -> removeListener(_releaseConsumers, consumer, k));
    }

    public boolean isKeyDown(KeyCode key) {
        return _keyDownSince.getOrDefault(key, 0L) != 0;
    }

    public long keyDownDurationMs(KeyCode key) {
        long since = keyDownSince(key);
        return since == 0 ? 0 : System.currentTimeMillis() - since;
    }

    public long keyDownSince(KeyCode key) {
        return _keyDownSince.getOrDefault(key, 0L);
    }

    public void onKeyPress(KeyEvent ev) {
        _keyDownSince.put(ev.getCode(), System.currentTimeMillis());

        ArrayList<Consumer<KeyEvent>> consumers = _pressConsumers.get(ev.getCode());
        if (consumers != null) consumers.forEach(c -> c.accept(ev));

        consumers = _pressConsumers.get(null);
        if (consumers != null) consumers.forEach(c -> c.accept(ev));
    }

    public void onKeyRelease(KeyEvent ev) {
        _keyDownSince.put(ev.getCode(), 0L);

        ArrayList<Consumer<KeyEvent>> consumers = _releaseConsumers.get(ev.getCode());
        if (consumers != null) consumers.forEach(c -> c.accept(ev));

        consumers = _releaseConsumers.get(null);
        if (consumers != null) consumers.forEach(c -> c.accept(ev));
    }
}
