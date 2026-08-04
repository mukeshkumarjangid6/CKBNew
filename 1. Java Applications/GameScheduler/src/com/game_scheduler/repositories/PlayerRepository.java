package com.game_scheduler.repositories;

import java.util.List;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;

/**
 * Defines data access operations for Player entities. Supports business
 * workflows: - Player registration - Player-wise reporting - Resolving game
 * rosters (Game → Players view)
 */
public interface PlayerRepository {

	/**
	 * Saves a player along with their associated games. Used during onboarding and
	 * schedule initialization.
	 */
	Player save(Player player);

	/**
	 * Fetches a player by name — the key identifier in all Player-centric reporting
	 * flows.
	 */
	Player findByName(String playerName);

	/**
	 * Returns all players participating in a given game.
	 */
	List<Player> findPlayersByGame(Game game);
}