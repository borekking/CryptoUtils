package de.borekking.crpyto.frequencyAnalysis.frontEnd.command;

public abstract class AbstractMainCommand extends AbstractCommand {

    private final String description;

    public AbstractMainCommand(String name, String usage, String description) {
        super(name, usage);
        this.description = description;
    }

    public String[] getHelpMessage() {
        return new String[]{super.getHelpMessage()[0], this.description};
    }

    public String getDescription() {
        return description;
    }
}
