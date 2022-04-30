/*
 * Copyright (c) 2022.
 * Authors : Storaï R, Faure B, Mathieu A, Garry A, Nicolau T, Bregier M.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package fr.imt.boomeuuuuh.utils;

public class Location {

    final int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Convert a byte array to Location
     *
     * @param array location
     * @return converted Location
     */
    public static Location fromBytesArray(byte[] array) {
        return new Location(array[0], array[1]);
    }

    /**
     * Convert a location to a byte array
     *
     * @return bytes
     */
    public byte[] toByteArray() {
        byte[] array = new byte[2];
        array[0] = (byte) x;
        array[1] = (byte) y;
        return array;
    }

    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (x != location.x) return false;
        return y == location.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
