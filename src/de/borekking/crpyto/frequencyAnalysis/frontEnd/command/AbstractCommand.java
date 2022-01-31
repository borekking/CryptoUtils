package de.borekking.crpyto.frequencyAnalysis.frontEnd.command;

public class AbstractCommand implements Command, Cloneable {

    private final String identifier, usage;

    AbstractCommand(String name, String usage) {
        this.identifier = name;
        this.usage = usage;
    }

    public void printArgumentError() {
        System.err.println("Wrong (amount of) arguments! Usage: " + this.getUsage());
    }

    public String[] getHelpMessage() {
        String nameAndUsage = String.format("%s: %s", this.identifier, this.usage);
        return new String[]{nameAndUsage};
    }

    public String getName() {
        return identifier;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public AbstractCommand clone() {
        return new AbstractCommand(this.identifier, this.usage);
    }
}
