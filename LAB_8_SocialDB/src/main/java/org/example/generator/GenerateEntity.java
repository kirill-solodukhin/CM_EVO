package org.example.generator;

import java.util.List;

public interface GenerateEntity<T>
{
    void generate();
    List<T> get();
}
