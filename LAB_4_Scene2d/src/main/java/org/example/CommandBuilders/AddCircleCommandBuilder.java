package org.example.CommandBuilders;

import org.example.Commands.Command;

public class AddCircleCommandBuilder implements CommandBuilder
{
    @Override
    public boolean isCommandReady() {
        return false;
    }

    @Override
    public void appendLine(String commandLine) {

    }

    @Override
    public Command getCommand() {
        return null;
    }

    @Override
    public void ThrowIFBadCommand(String commandLine) {

    }
}
