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

package fr.imt.boomeuuuuh.entities;

import fr.imt.boomeuuuuh.players.Location;

public class Entity {
    //State Variables
    private final int id;
    private boolean isObstacle = false;

    //Dynamic Variables
    protected Location pos;

    public Entity(int id) {
        this.id = id;
    }

    //-------------------------GET-------------------------

    /**
     * @return if the entity is considered as an obstacle
     */
    public boolean isObstacle() {
        return isObstacle;
    }

    /**
     * @return if the entity can move
     */
    public boolean isMovable() {
        return false;
    }

    /**
     * Get entity location (blocs)
     *
     * @return bloc location
     */
    public Location getPos() {
        return pos;
    }

    /**
     * @return bloc X
     */
    public int getX() {
        return pos.getX();
    }

    /**
     * @return bloc Y
     */
    public int getY() {
        return pos.getY();
    }

    /**
     * @return UID of the entity
     */
    public int getId() {
        return id;
    }

    //-----------------------------------------------------
    //------------------------SET--------------------------

    /**
     * Set obstacle
     *
     * @param isObstacle or not
     */
    public void isObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    /**
     * Set location
     *
     * @param pos location
     */
    public void setPos(Location pos) {
        this.pos = pos;
    }
    //-----------------------------------------------------
    //--------------------BASIC METHODS--------------------
    //-----------------------------------------------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", pos=" + pos +
                '}';
    }
}
