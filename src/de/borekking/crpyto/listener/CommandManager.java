package de.borekking.crpyto.listener;

import java.util.List;

public interface CommandManager {

    String getName();

    boolean execute(String command, String[] args);

    default boolean containsCommand(String command) {
        return this.getCommandNames().stream().anyMatch(command1 -> command1.equals(command));
    }

    List<String> getCommandNames();
}
