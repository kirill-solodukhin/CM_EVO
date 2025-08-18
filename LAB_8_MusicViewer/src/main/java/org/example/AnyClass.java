package org.example;

import org.example.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnyClass
{
    @Autowired
    private AlbumRepository repository;

    public void out()
    {
        System.out.println(repository.count());
    }
}
