package com.hahsm.frame;

import com.hahsm.datastructure.LinkedList;
import com.hahsm.datastructure.adt.Stack;

public class FrameManager {
    private Stack<Frame> frames;

    public FrameManager() {
        frames = new LinkedList<>();
    }

    public void addFrame(final Frame frame) {
        assert frames != null;
        frame.setFrameManager(this);
        frames.add(frame);
    }

    public void addFrameAndDisplay(final Frame frame) {
        addFrame(frame);
        frame.display();
    }

    public void back() {
        assert frames != null && !frames.isEmpty();
        frames.top().setFrameManager(null);
        frames.remove();
        if (!frames.isEmpty()) {
            frames.top().display();
        }
    }
}
