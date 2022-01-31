package de.borekking.crpyto.frequencyAnalysis.frontEnd.command.commands;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisConnector;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisFrontEnd;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractCommand;
import de.borekking.crpyto.frequencyAnalysis.frontEnd.command.AbstractMainCommand;

public class PrintCommand extends AbstractMainCommand {

    private final FrequencyAnalysisConnector mainBackEnd;

    private final FrequencyAnalysisFrontEnd mainFrontEnd;

    public PrintCommand(FrequencyAnalysisConnector mainBackEnd, FrequencyAnalysisFrontEnd mainFrontEnd) {
        super("print", "print", "Reloads the programs guesses based on the users guesses, prints the mapping as well as the percentages and the translated text.");
        this.mainBackEnd = mainBackEnd;

        this.mainFrontEnd = mainFrontEnd;
    }

    @Override
    public AbstractCommand use(String[] args) {
        this.mainBackEnd.reloadGuesses();
        this.mainFrontEnd.print(this.mainBackEnd);
        return null;
    }
}
