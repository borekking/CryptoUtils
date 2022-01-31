package de.borekking.crpyto.shiftCipher;

import de.borekking.crpyto.listener.CommandManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShiftCipherCommandManager implements CommandManager {

    private final ShiftCypherDecryptionProgram program;

    public ShiftCipherCommandManager(ShiftCypherDecryptionProgram program) {
        this.program = program;
    }

    @Override
    public String getName() {
        return "Shift Cipher";
    }

    @Override
    public boolean execute(String command, String[] args) {
        if (command.equals("y")) {
            this.program.stop();
            return true;
        } else if (command.equals("n")) {
            this.program.nextKey();
            System.out.println();
            return true;
        }

        return false;
    }

    @Override
    public List<String> getCommandNames() {
        return new ArrayList<>(Arrays.asList("y", "n"));
    }
}
