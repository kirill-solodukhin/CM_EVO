package org.example.CommandBuilders;

import org.example.Commands.Command;

public interface CommandBuilder
{
     boolean isCommandReady();
     void appendLine(String commandLine);
     Command getCommand();
     void ThrowIFBadCommand(String commandLine);
}
