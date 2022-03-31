package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.Boomeuuuuh;
import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.PowerUp;
import fr.imt.boomeuuuuh.entities.SoftBlock;
import fr.imt.boomeuuuuh.entities.bombs.Bomb;
import fr.imt.boomeuuuuh.players.Location;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class MapLoader {
    //Map format -> Only represents entities
    //  - Entity number represents it
    // eg . 0,50,50,0,0,50,...
    //      0,50,0,0,0,...
    //      ...
    public Map LoadMap(String mapName, GameManager man) {
        URL rPath = getClass().getClassLoader().getResource("Maps/" + mapName + ".txt");
        File f;
        try{
            if (rPath != null)
                f = new File(rPath.toURI());
            else
                return null;
        }catch (URISyntaxException e){
            e.printStackTrace();
            return null;
        }

        FileReader fl;
        try {
            fl = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader bf = new BufferedReader(fl);

        Collection<Entity> E = new ArrayList<>();
        ArrayList<Location> spawn = new ArrayList<>();
        String st;
        int y = 0;
        int x = 0;
        while (true) {
            try {
                st = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            if (st == null)
                break;

            String[] s = st.split(",");
            x = 0;
            for (String p : s) {
                Entity e = null;
                int i = 0;
                try {
                    i = Integer.parseInt(p);
                }catch (Exception exception){ Boomeuuuuh.logger.severe(exception.toString());}

                switch (i) {
                    case 50 -> e = new SoftBlock(man.getNewID());
                    case 40 -> e = new HardBlock(man.getNewID());
                    case 20 -> e = new PowerUp(man.getNewID());
                    case 4 -> spawn.add(new Location(x, y));
                    default -> {
                    }
                }
                if (e != null) {
                    e.setPos(new Location(x, y));
                    E.add(e);
                }
                x += 1;
            }
            y += 1;
        }

        return new Map(x + 1, y + 1, E, spawn);
    }
}
