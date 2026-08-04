package com.game_scheduler.initializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.exceptions.ValidationException;

/**
 * This class functions as the system’s **in-memory database**.
 *
 * It centralizes and maintains the core domain relationships:
 *
 * 1. Game → Players (Which players participate in each game?) 2. Game → Days
 * (On which days is each game scheduled?)
 *
 * These two mappings form the heart of all report generation: - Game-wise
 * reports use both maps - Player-wise reporting starts from Game → Days -
 * Day-wise reporting reverses Game → Players
 *
 * The DB is purely static and process-local, ideal for: • Testing • Prototyping
 * • Demonstrating workflow behavior without a real DB
 *
 * It simulates how a relational or document database would behave, while
 * keeping logic simple and accessible for demos and POCs.
 */

public class SchedularDB {
	private SchedularDB() {
	}

	/**
	 * Map storing player rosters per game.
	 *
	 * Key = Game Value = List of Players participating in that game
	 *
	 * This is essential for: - Game-wise reporting - Day-wise: Day → Games →
	 * Players
	 */
	private static Map<Game, List<Player>> gamesThatPlayerPlay = new HashMap<>();

	/**
	 * Map of game schedules.
	 *
	 * Key = Game Value = List of Days the game is scheduled on
	 *
	 * Supports: - Game-wise reporting - Player-wise reporting (through Game → Days)
	 */
	private static Map<Game, List<Day>> gamesAndDays = new HashMap<>();

	/**
	 * Inserts a new Game into the scheduling database. Ensures that the new Game
	 * has both: - A place to store its players - A place to store its scheduled
	 * days
	 *
	 * This establishes the **two foundational lists** required to build all future
	 * cross-entity associations.
	 */
	public static Game insertGame(Game game) {
		if (game == null)
			return null;

		gamesThatPlayerPlay.putIfAbsent(game, new ArrayList<>());
		gamesAndDays.putIfAbsent(game, new ArrayList<>());

		return game;
	}

	/**
	 * Adds a new player into the system *and* updates all relevant associations
	 * based on the games the player participates in.
	 *
	 * For each game the player plays: • Ensure the game exists in DB • Remove
	 * duplicates of this player (business rule) • Add the player to the roster for
	 * that game • Ensure the game has an initialized days list
	 *
	 * This maintains a **bidirectional consistency** where the DB knows “Players
	 * per Game,” even though Player holds “Games per Player.”
	 */

	public static Player insertPlayer(Player player) {
		if (player == null)
			return null;

		for (Game game : player.getGames()) {
			if (game == null)
				continue;

			// Ensure game-to-player list exists
			gamesThatPlayerPlay.computeIfAbsent(game, g -> new ArrayList<>())
					// Remove any pre-existing references to avoid duplicates
					.removeIf(p -> p.equals(player));

			// Add the player to the game roster
			gamesThatPlayerPlay.get(game).add(player);

			// Ensure game-to-day mapping exists as well
			gamesAndDays.putIfAbsent(game, new ArrayList<>());
		}
		return player;
	}

	/**
	 * Adds a new Day and updates all Game → Day associations.
	 *
	 * For each game scheduled on this day: • Ensure the game's day list exists •
	 * Remove duplicates (business rule) • Add the day to the game’s schedule •
	 * Ensure the game also has a player list entry
	 *
	 * This ensures **schedule integrity**, keeping both maps aligned.
	 */

	public static Day insertDay(Day day) {
		if (day == null)
			return null;

		for (Game game : day.getGames()) {
			if (game == null)
				continue;

			// Ensure list exists and remove duplicate entries for the day
			gamesAndDays.computeIfAbsent(game, g -> new ArrayList<>()).removeIf(d -> d.equals(day));

			// Add this day to the game's schedule
			gamesAndDays.get(game).add(day);

			// Ensure the Game → Players list exists
			gamesThatPlayerPlay.putIfAbsent(game, new ArrayList<>());
		}

		return day;
	}

	/**
	 * BUSINESS PURPOSE: ------------------ Returns the player roster for a given
	 * game.
	 *
	 * Used heavily in: - Game-wise reporting - Day-wise reporting (via Game →
	 * Players)
	 */
	public static List<Player> queryPlayersByGame(Game game) {
		return Collections.unmodifiableList(gamesThatPlayerPlay.getOrDefault(game, List.of()));
	}

	/**
	 * BUSINESS PURPOSE: ------------------ Returns all days a game is scheduled on.
	 *
	 * Used in: - Game-wise reporting - Player-wise reporting (Player → Games →
	 * Days)
	 */
	public static List<Day> queryDaysByGame(Game game) {
		return Collections.unmodifiableList(gamesAndDays.getOrDefault(game, List.of()));
	}

	/**
	 * BUSINESS PURPOSE: ------------------ Locates a Game by its business key
	 * (name). Performs case-insensitive matching to avoid duplicates such as:
	 * “Cricket” vs “cricket”
	 *
	 * Supports validation for: - Add operations - Reporting workflows
	 */
	public static Game findGameByName(String gameName) {
		if (gameName == null || gameName.isBlank()) {
			throw new ValidationException("Game name must be non-blank.");
		}

		return gamesThatPlayerPlay.keySet().stream().filter(g -> g.getName().equalsIgnoreCase(gameName)).findFirst()
				.orElse(null);
	}

	/**
	 * BUSINESS PURPOSE: ------------------ Retrieves a Player by name
	 * (case-insensitive).
	 *
	 * Useful for: - Player-wise reporting - Validating uniqueness or existence
	 */
	public static Player findPlayerByName(String playerName) {
		if (playerName == null || playerName.isBlank()) {
			throw new ValidationException("Player name must be non-blank.");
		}

		return gamesThatPlayerPlay.values().stream().flatMap(List::stream)
				.filter(p -> p.getName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
	}

	/**
	 * 
	 * Locates a Day entity by its business key.
	 *
	 * Used when: - Generating day-wise reports - Validating day insertions -
	 * Ensuring scheduling consistency
	 */
	public static Day findDayByName(String dayName) {
		if (dayName == null || dayName.isBlank()) {
			throw new ValidationException("Day name must be non-blank.");
		}

		return gamesAndDays.values().stream().flatMap(List::stream).filter(d -> d.getName().equalsIgnoreCase(dayName))
				.findFirst().orElse(null);
	}
}