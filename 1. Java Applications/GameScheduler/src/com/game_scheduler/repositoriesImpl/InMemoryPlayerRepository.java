package com.game_scheduler.repositoriesImpl;

import java.util.List;

import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.exceptions.ValidationException;
import com.game_scheduler.initializer.SchedularDB;
import com.game_scheduler.repositories.PlayerRepository;

public class InMemoryPlayerRepository implements PlayerRepository {

	@Override
	public Player save(Player player) {
		if (player == null || player.getName() == null || player.getName().isBlank()) {
			throw new ValidationException("Player must have a non-blank name.");
		}
		return SchedularDB.insertPlayer(player);
	}

	@Override
	public Player findByName(String playerName) {
		if (playerName == null || playerName.isBlank()) {
			throw new ValidationException("Player name must be non-blank.");
		}
		return SchedularDB.findPlayerByName(playerName);
	}

	@Override
	public List<Player> findPlayersByGame(Game game) {
		if (game == null || game.getName() == null || game.getName().isBlank()) {
			throw new ValidationException("Game must be provided.");
		}
		return SchedularDB.queryPlayersByGame(game);
	}
}