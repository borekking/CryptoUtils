package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get.subCommands.GetCipherFromGuessSubCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands.get.subCommands.GetGuessFromCipherSubCommand;

public class GetCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector main;

    public GetCommand(FrequencyAnalysisConnector main) {
        super("get", "get <cipher/guess> <char>", "Prints the guess to a cipher-character (guess), or the cipher-character to a guess (cipher). (This does not show characters you just mapped. Use print to reload.)");

        this.main = main;
    }

    @Override
    public AbstractCommand use(String[] args) {
        if (args.length < 1) {
            this.printArgumentError();
            return null;
        }

        switch(args[0]) {
            case "cipher":
                return new GetCipherFromGuessSubCommand(this, this.main);
            case "guess":
                return new GetGuessFromCipherSubCommand(this, this.main);
            default: {
                this.printArgumentError();
                return null;
            }
        }
    }
}
