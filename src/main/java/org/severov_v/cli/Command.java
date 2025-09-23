package org.severov_v.cli;

public interface Command {
    void execute();
    String getCommandName();
}
