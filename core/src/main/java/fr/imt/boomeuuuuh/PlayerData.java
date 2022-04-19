package fr.imt.boomeuuuuh;

import fr.imt.boomeuuuuh.utils.Skin;

public class PlayerData {

    public int gold;
    public int level;
    public String currentSkin;
    public String[] unlockedSkins;

    public Skin getCurrentSkin() {
        return Skin.getByDataName(currentSkin);
    }

    public boolean hasSkin(Skin skin) {
        for (String unlockedSkin : unlockedSkins) {
            if (unlockedSkin.equalsIgnoreCase(skin.getDataName()))
                return true;
        }
        return false;
    }

    public void selectSkin(Skin skin) {
        this.currentSkin = skin.getDataName();
        // TODO send packet
    }

    public void unlockSkin(Skin skin) {
        // TODO send packet
    }
}
