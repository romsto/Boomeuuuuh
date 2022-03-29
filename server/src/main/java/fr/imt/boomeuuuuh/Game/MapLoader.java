package fr.imt.boomeuuuuh.Game;

import fr.imt.boomeuuuuh.entities.Entity;
import fr.imt.boomeuuuuh.entities.HardBlock;
import fr.imt.boomeuuuuh.entities.PowerUp;
import fr.imt.boomeuuuuh.entities.SoftBlock;
import fr.imt.boomeuuuuh.entities.bombs.Bomb;
import fr.imt.boomeuuuuh.players.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class MapLoader {
    //Map format -> Only represents entities
    //  - Entity number represents it
    // eg . 0,50,50,0,0,50,...
    //      0,50,0,0,0,...
    //      ...
    static public Map LoadMap(String mapName, GameManager man) {
        File f = new File(mapName); //TODO : Put proper path
        FileReader fl;
        try {
            fl = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        BufferedReader bf = new BufferedReader(fl);

        Collection<Entity> E = new ArrayList<>();
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
                switch (Integer.parseInt(p)) {
                    case 50 -> e = new SoftBlock(man.getNewID());
                    case 40 -> e = new HardBlock(man.getNewID());
                    case 20 -> e = new PowerUp(man.getNewID());
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

        return new Map(x + 1, y + 1, E);
    }
}
