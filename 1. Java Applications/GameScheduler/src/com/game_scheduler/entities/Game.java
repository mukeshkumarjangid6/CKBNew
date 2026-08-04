package com.game_scheduler.entities;

import java.util.Objects;

/**
 * Represents a game type (e.g., Cricket, Football).
 * Acts as a key entity across all scheduling associations.
 */
public class Game {

    private String name;

    public Game() {}

    public Game(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public int hashCode() {
        return Objects.hash(name == null ? null : name.toLowerCase());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Game)) return false;

        Game other = (Game) obj;
        return Objects.equals(
                name == null ? null : name.toLowerCase(),
                other.name == null ? null : other.name.toLowerCase()
        );
    }

    @Override
    public String toString() {
        return "Game{name='" + name + "'}";
    }
}