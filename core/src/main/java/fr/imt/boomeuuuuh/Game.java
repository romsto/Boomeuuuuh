package fr.imt.boomeuuuuh;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.imt.boomeuuuuh.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Entity> entities = new ArrayList<>();
    public int sizeX;
    public int sizeY;
    public int nbrJoueur;

    public Game() {


    }
    public void draw(SpriteBatch batch, float temps){
        for(int i=0;i<entities.size();i++){
            entities.get(i).draw(batch, temps);
        }
    }
}
