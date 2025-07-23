package org.example.Commands;

import org.example.Scene.Scene;

public interface Command
{
    String friendlyResultMessage();
    void apply(Scene scene);
}
