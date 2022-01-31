package de.borekking.crpyto.frequencyAnalysis.frontEnd.command;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisFrontEnd;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.HelpCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.PrintCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get.GetCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map.IsMappedCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map.MapCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.map.UnMapCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save.IsSavedCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save.SaveCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.save.UnSaveCommand;
import de.borekking.crpyto.listener.CommandManager;
import de.borekking.crpyto.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FrequencyAnalysisCommandManager implements CommandManager {

    private final FrequencyAnalysisConnector mainBackEnd;

    private final FrequencyAnalysisFrontEnd mainFrontEnd;

    private final List<AbstractMainCommand> commands;

    public FrequencyAnalysisCommandManager(FrequencyAnalysisConnector mainBackEnd, FrequencyAnalysisFrontEnd mainFrontEnd) {
        this.mainBackEnd = mainBackEnd;
        this.mainFrontEnd = mainFrontEnd;
        this.commands = new ArrayList<>();

        this.registerCommands(this.commands);
    }

    private void registerCommands(List<AbstractMainCommand> commands) {
        // Saving
        commands.add(new SaveCommand(this.mainBackEnd));
        commands.add(new UnSaveCommand(this.mainBackEnd));
        commands.add(new IsSavedCommand(this.mainBackEnd));

        // Printing
        commands.add(new PrintCommand(this.mainBackEnd, this.mainFrontEnd));

        // Mapping
        commands.add(new MapCommand(this.mainBackEnd));
        commands.add(new IsMappedCommand(this.mainBackEnd));
        commands.add(new UnMapCommand(this.mainBackEnd));

        // Help
        commands.add(new HelpCommand(this));

        // Getting
        commands.add(new GetCommand(this.mainBackEnd));
    }

    /**
     *
     * @param identifier Command name, can be null
     * @return A Command with given identifier, or null if there is no Command with this identifier
     */
    public AbstractMainCommand getCommand(String identifier) {
        return this.commands.stream().filter(command -> command.getName().equals(identifier)).findFirst().orElse(null);
    }

    public List<AbstractMainCommand> getCommands() {
        return commands;
    }

    @Override
    public String getName() {
        return "Frequency Analysis";
    }

    @Override
    public boolean execute(String commandName, String[] args) {
        // Get Command from commandManager
        AbstractMainCommand command = this.getCommand(commandName);

        // Check command being null
        if (command == null) {
            System.err.println("Couldn't find Command \"" + commandName + "\"!");
            return false;
        }

        // Use commands with args (where the first element is removed 'cause it's the command name)
        this.useCommand(command, args);
        return true;
    }

    @Override
    public List<String> getCommandNames() {
        return this.commands.stream().map(AbstractCommand::getName).collect(Collectors.toList());
    }

    // Command should not be null
    private void useCommand(AbstractCommand command, String[] args) {
        // Use command and get subCommand
        AbstractCommand subCommand = command.use(args);

        // Check command being null
        if (subCommand == null) return;

        if (args.length < 1) {
            command.printArgumentError();
            return;
        }

        this.useCommand(subCommand, ArrayUtils.removeFirst(args, 1));
    }
}
