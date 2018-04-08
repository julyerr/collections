package com.julyerr.interviews.thread.ConcurrentProgramming.concepts;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//将多个对象委托给多个状态变量
public class VisualComponent {
    private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<>();
    private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<>();

    public void addKeyListener(KeyListener listener){
        keyListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener){
        mouseListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener){
        keyListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener){
        mouseListeners.remove(listener);
    }
}
