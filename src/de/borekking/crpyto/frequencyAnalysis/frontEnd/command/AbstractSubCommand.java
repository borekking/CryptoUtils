package de.borekking.crpyto.frequencyAnalysis.frontEnd.command;

import java.util.ArrayList;
import java.util.List;

public class AbstractSubCommand extends AbstractCommand {

    private final AbstractCommand parent;

    public AbstractSubCommand(String name, String usage, AbstractCommand parent) {
        super(name, usage);

        if (parent == null) {
            throw new IllegalArgumentException("Parent can not be null!");
        }

        this.parent = parent;
    }

    @Override
    public String getUsage() {
        StringBuilder usage = new StringBuilder();
        List<AbstractCommand> parents = this.getParents();

        for (AbstractCommand command : parents)
            usage.append(command.getName()).append(" ");

        return usage.append(super.getUsage()).toString();
    }

    public List<AbstractCommand> getParents() {
        List<AbstractCommand> parents = new ArrayList<>();

        AbstractCommand current = this.parent.clone();
        parents.add(current);

        while (current instanceof AbstractSubCommand) {
            current = ((AbstractSubCommand) current).getParent();
            parents.add(current);
        }

        return parents;
    }

    public AbstractCommand getParent() {
        return parent;
    }
}
