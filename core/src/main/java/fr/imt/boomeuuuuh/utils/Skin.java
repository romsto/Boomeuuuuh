package fr.imt.boomeuuuuh.utils;

public enum Skin {

    KAREDAS(1),
    APOCALISTE(2),
    ATLANTAS(3),
    EMAZTEK(4);

    private final int number;

    Skin(int number) {
        this.number = number;
    }

    public static Skin getByDataName(String dataName) {
        return values()[Integer.parseInt(dataName.replaceAll("skin", "")) - 1];
    }

    public int getNumber() {
        return number;
    }

    public String getDataName() {
        return "skin" + number;
    }
}
