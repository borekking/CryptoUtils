package de.borekking.crpyto;

import de.borekking.crpyto.listener.CommandManager;

public interface IProgram {

    void run();

    CommandManager getCommandManager();

    default void stop() {
        Main.reset();
    }
}
