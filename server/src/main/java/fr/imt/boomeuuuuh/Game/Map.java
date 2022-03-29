package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.Entity;

import java.util.Collection;

public class Map {

    public final int Width;
    public final int Height;
    public final Collection<Entity> mapEntities;

    public Map(int width, int height, Collection<Entity> entities){
        Width = width;
        Height = height;
        mapEntities = entities;
    }
}
