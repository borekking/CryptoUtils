package de.borekking.crpyto.listener;

import de.borekking.crpyto.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandListener {

    private final List<CommandManager> commandManagers;

    public CommandListener() {
        this.commandManagers = new ArrayList<>();
    }

    public void start() {
        this.awaitInput();
    }

    private void awaitInput() {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            String input = sc.nextLine();
            String[] args = input.split(" ");

            if (args.length == 0) {
                System.err.println("This input is not a valid command");
                return;
            }

            String command = args[0].toLowerCase();

            // Check help Command
            if (command.equals("generalhelp")) {
                this.printHelp();
                return;
            }

            CommandManager commandManager = this.getCommandManager(command);

            if (commandManager == null) {
                System.err.println("Command \"" + command + "\" could not be found. Use \"generalhelp\" for general help.");
            } else {
                commandManager.execute(command, ArrayUtils.removeFirst(args, 1));
            }
        }
    }

    private CommandManager getCommandManager(String command) {
        return this.commandManagers.stream().filter(manager -> manager.containsCommand(command)).findFirst().orElse(null);
    }

    private void printHelp() {
        // General help
        System.out.println("You can use following commands:");

        for (CommandManager manager : this.commandManagers) {
            System.out.println(manager.getName() + ":");

            for (String command : manager.getCommandNames())
                System.out.println("    " + command + ":");
        }
    }

    public void addCommandManager(CommandManager manager) {
        this.commandManagers.add(manager);
    }

    public void removeCommandManager(CommandManager manager) {
        this.commandManagers.remove(manager);
    }
}
