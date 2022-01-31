package de.borekking.crpyto;

import de.borekking.crpyto.frequencyAnalysis.frontEnd.FrequencyAnalysisFrontEnd;
import de.borekking.crpyto.listener.CommandManager;
import de.borekking.crpyto.shiftCipher.ShiftCypherDecryptionProgram;

import java.util.function.Supplier;

public enum Program {

    FREQUENCY_ANALYSIS("Frequency Analysis", "Analysis a text in a specific file based on it's letters", FrequencyAnalysisFrontEnd::new),
    SHIFT_CYPHER_DECRYPTION("Shift Cypher Decryption", "Decrypted a Caeser/Shift Cypher.", ShiftCypherDecryptionProgram::new);

    // Program's name
    private final String name, description;

    // Actual program
    private final IProgram program;

    // CommandManager Supplier
    private final Supplier<CommandManager> commandManagerSupplier;

    Program(String name, String description, Supplier<IProgram> programSupplier) {
        this.name = name;
        this.description = description;
        this.program = programSupplier.get();
        this.commandManagerSupplier = this.program::getCommandManager;
    }

    public void run() {
        this.program.run();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    // Using Supplier because CommandManager is created as run methode is called.
    public CommandManager getCommandManager() {
        return this.commandManagerSupplier.get();
    }
}
