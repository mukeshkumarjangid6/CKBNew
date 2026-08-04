package com.game_scheduler.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player and games they participate in. Maintains unique game
 * assignments per player.
 */

public class Player {

	private String name;
	private List<Game> games = new ArrayList<>();

	public Player() {
	}

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	/**
	 * Registers a new game for the player, ensuring business rule:
	 *
	 * “A player cannot be associated with the same game more than once.”
	 *
	 * This protects the integrity of Player → Game mappings, ensuring accurate
	 * reporting and avoiding inflated game rosters.
	 */

	public void addGame(Game game) {
		if (game != null && games.stream().noneMatch(x -> x.equals(game))) {
			games.add(game);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(name == null ? null : name.toLowerCase());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;

		return Objects.equals(name == null ? null : name.toLowerCase(),
				other.name == null ? null : other.name.toLowerCase());
	}

	@Override
	public String toString() {
		return "Player{name='" + name + "'}";
	}
}