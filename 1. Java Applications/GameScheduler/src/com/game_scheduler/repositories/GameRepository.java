package com.game_scheduler.repositories;

import com.game_scheduler.entities.Game;

/**
 * Defines data operations for Game entities. Enables the service layer to
 * remain independent of how the game records are stored or retrieved.
 *
 * Central to: - Game creation workflows - Game-wise reporting - Player/day
 * associations using Game as the key
 */
public interface GameRepository {

	/**
	 * Saves a new game into the repository. Used during both initialization and
	 * admin-driven additions.
	 */
	Game save(Game game);

	/**
	 * Looks up a game by name. This is the main entry point for all Game‑centric
	 * reports.
	 */
	Game findByName(String gameName);
}