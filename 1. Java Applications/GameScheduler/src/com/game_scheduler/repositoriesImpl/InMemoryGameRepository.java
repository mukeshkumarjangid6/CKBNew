package com.game_scheduler.repositoriesImpl;

import com.game_scheduler.entities.Game;
import com.game_scheduler.exceptions.ValidationException;
import com.game_scheduler.initializer.SchedularDB;
import com.game_scheduler.repositories.GameRepository;

public class InMemoryGameRepository implements GameRepository {

	@Override
	public Game save(Game game) {
		if (game == null || game.getName() == null || game.getName().isBlank()) {
			throw new ValidationException("Game must have a non-blank name.");
		}
		return SchedularDB.insertGame(game);
	}

	@Override
	public Game findByName(String gameName) {
		if (gameName == null || gameName.isBlank()) {
			throw new ValidationException("Game name must be non-blank.");
		}
		return SchedularDB.findGameByName(gameName);
	}
}