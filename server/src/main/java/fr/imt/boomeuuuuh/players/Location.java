package fr.imt.boomeuuuuh.players;

public class Location {

    final int x, y;

    /**
     * Class representing a location (2D vector)
     * @param x x component of the location
     * @param y y component of the location
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Generate the byte representation of the x and y components of the Location
     * @return array of (byte) x and (byte) y
     */
    public byte[] toByteArray() {
        byte[] array = new byte[2];
        array[0] = (byte) x;
        array[1] = (byte) y;
        return array;
    }

    /**
     * @return x component of Location
     */
    public int getX() {
        return x;
    }

    /**
     * @return y component of Location
     */
    public int getY() {
        return y;
    }

    /**
     * Check if the location is in a box excluding the borders of the box
     * @param minx bottom left x component of the box
     * @param maxx top right x component of the box
     * @param miny bottom left y component of the box
     * @param maxy top right x component of the box
     * @return true if the location is in the box excluding the borders of the box
     */
    public boolean comprisedInExcludingBorder(int minx, int maxx, int miny, int maxy){
        return (minx < x && x < maxx && miny < y && y < maxy);
    }

    /**
     * Check if this object represents the same coordinates as object o
     * @param o Object it is being compared to
     * @return true if the coordinates are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        return y == location.y;
    }

    /**
     * Make hash of location
     * @return hash of location
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * Make a location from its byte representation
     * @param array {byte representation of x, byte representation of y}
     * @return location represented by the array
     */
    public static Location fromBytesArray(byte[] array) {
        return new Location(array[0], array[1]);
    }
}
