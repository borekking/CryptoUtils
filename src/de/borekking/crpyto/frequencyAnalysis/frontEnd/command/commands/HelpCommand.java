package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractSubCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.FrequencyAnalysisCommandManager;
import de.borekking.crpyto.util.TextUtils;

import java.util.List;

public class HelpCommand extends AbstractMainCommand {

    private final FrequencyAnalysisCommandManager commandManager;

    public HelpCommand(FrequencyAnalysisCommandManager commandManager) {
        super("help", "help [command]", "Provides general help or help to a specified command.");

        this.commandManager = commandManager;
    }

    @Override
    public AbstractSubCommand use(String[] args) {
        // General help
        if (args.length == 0) {
            System.out.println("You can use following commands:");

            List<AbstractMainCommand> commands = this.commandManager.getCommands();
            for (AbstractMainCommand command : commands) this.printCommand(command);
        }
        // Command help
        else {
            String commandName = args[0];
            AbstractMainCommand command = this.commandManager.getCommand(commandName);

            if (command == null) {
                System.err.println("Command " + commandName + " was not found! Use \"help\" to list all available commands.");
                return null;
            }

            System.out.println("Help for Command " + commandName + ":");
            this.printCommand(command);
        }

        return null;
    }

    private void printCommand(AbstractMainCommand command) {
        String prefix = "    ";
        String[] helpMSG = command.getHelpMessage();
        System.out.println(prefix + helpMSG[0]);
        System.out.println(TextUtils.repeat(prefix, 2) + helpMSG[1]);
    }
}
