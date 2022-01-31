package de.borekking.crpyto.shiftCipher;

import de.borekking.crpyto.IProgram;
import de.borekking.crpyto.listener.CommandManager;
import de.borekking.crpyto.util.FrontEndUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class ShiftCypherDecryptionProgram implements IProgram {

    private ShiftCypherDecryption decryption;

    private CommandManager commandManager;

    // Array of all keys and the current key as pointer
    private Integer[] keys;
    private int pointer; // Start = 0

    public ShiftCypherDecryptionProgram() {
    }

    // Package Protected
    void nextKey() {
        int key = this.keys[this.pointer];

        // Print current decrypted Cypher
        String current = this.decryption.useCaeserKey(key);
        System.out.println(current);
        System.out.println("Please answer if this encryption makes sense (y/n)");

        this.pointer++;
    }

    @Override
    public void run() {
        List<String> lines = null;
        try {
            lines = FrontEndUtils.getLines();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.commandManager = new ShiftCipherCommandManager(this);

        this.decryption = new ShiftCypherDecryption(lines);

        this.keys = this.decryption.getKeys();

        this.nextKey();
    }

    @Override
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}
