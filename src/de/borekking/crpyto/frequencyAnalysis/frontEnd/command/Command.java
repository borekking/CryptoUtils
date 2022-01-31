package de.borekking.crpyto.frequencyAnalysis.frontEnd.command;

public interface Command {

    /**
     * @param args The users arguments without the identifier
     * @return If the command was successful (If it was false the usage will be print)
     */
    default AbstractCommand use(String[] args) {
        System.out.println("Not implemented yet");
        return null;
    }
}
