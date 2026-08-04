package com.game_scheduler.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a day on which games can be scheduled. Maintains a unique set of
 * games associated with the day.
 */

public class Day {

	private String name;
	private List<Game> games = new ArrayList<>();

	public Day() {
	}

	public Day(String name) {
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
	 * Adds a game to the day while ensuring the business rule:
	 *
	 * “A game can appear on a day only once.”
	 *
	 * Enforces uniqueness to prevent duplicate schedule entries and maintains
	 * accuracy in day‑wise reporting.
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
		if (!(obj instanceof Day))
			return false;
		Day other = (Day) obj;
		return Objects.equals(name == null ? null : name.toLowerCase(),
				other.name == null ? null : other.name.toLowerCase());
	}

	@Override
	public String toString() {
		return "Day{name='" + name + "'}";
	}
}