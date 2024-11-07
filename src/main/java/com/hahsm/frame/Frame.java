package com.hahsm.frame;

import java.util.function.Consumer;

import com.hahsm.common.ioconsole.ConsoleHelper;

public class Frame {
    private FrameManager frameManager;
    private final Consumer<FrameManager> action;

    public Frame(Consumer<FrameManager> action) {
        this.action = action;
    }

    public void display() {
        assert frameManager != null;
        action.accept(frameManager);

        ConsoleHelper.waitForEnter("Press enter to back");
        frameManager.back();
    }

    public void setFrameManager(final FrameManager frameManager) {
        this.frameManager = frameManager;
    }
}
