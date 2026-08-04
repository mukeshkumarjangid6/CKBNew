package com.game_scheduler.servicesImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.exceptions.NotFoundException;
import com.game_scheduler.exceptions.ValidationException;
import com.game_scheduler.initializer.SampleRecordDB;
import com.game_scheduler.repositories.DayRepository;
import com.game_scheduler.repositories.GameRepository;
import com.game_scheduler.repositories.PlayerRepository;
import com.game_scheduler.repositoriesImpl.InMemoryDayRepository;
import com.game_scheduler.repositoriesImpl.InMemoryGameRepository;
import com.game_scheduler.repositoriesImpl.InMemoryPlayerRepository;
import com.game_scheduler.services.SchedularService;
import com.game_scheduler.vo.GameVO;

public class SchedularServiceImpl implements SchedularService {
	private final GameRepository gameRepo = new InMemoryGameRepository();
	private final PlayerRepository playerRepo = new InMemoryPlayerRepository();
	private final DayRepository dayRepo = new InMemoryDayRepository();

	public SchedularServiceImpl() {
		SampleRecordDB.getInstance().seed(gameRepo, playerRepo, dayRepo);
	}

	private static void requireName(String label, String name) {
		if (name == null || name.isBlank()) {
			throw new ValidationException(label + " name must be non-blank.");
		}
	}

	@Override
	public Game addGame(Game g) {
		if (g == null)
			throw new ValidationException("Game cannot be null.");
		requireName("Game", g.getName());
		return gameRepo.save(g);
	}

	@Override
	public Player addPlayer(Player p) {
		if (p == null)
			throw new ValidationException("Player cannot be null.");
		requireName("Player", p.getName());
		return playerRepo.save(p);
	}

	@Override
	public Day addDay(Day d) {
		if (d == null)
			throw new ValidationException("Day cannot be null.");
		requireName("Day", d.getName());
		return dayRepo.save(d);
	}

	@Override
	public GameVO gameWiseReport(Game g) {
		if (g == null)
			throw new ValidationException("Game cannot be null.");
		requireName("Game", g.getName());

		Game found = gameRepo.findByName(g.getName());
		if (found == null)
			throw new NotFoundException("Game not found: " + g.getName());

		return new GameVO(found, playerRepo.findPlayersByGame(found), 
				dayRepo.findDaysByGame(found));
	}

	@Override
	public Map<Game, List<Day>> playerWiseReport(Player p) {
		if (p == null)
			throw new ValidationException("Player cannot be null.");
		requireName("Player", p.getName());

		Player found = playerRepo.findByName(p.getName());
		if (found == null)
			throw new NotFoundException("Player not found: " + p.getName());

		return found.getGames().stream()
				.collect(Collectors.toMap(game -> game, dayRepo::findDaysByGame, (a, b) -> a, LinkedHashMap::new));
	}

	@Override
	public Map<Game, List<Player>> dayWiseReport(Day d) {
		if (d == null)
			throw new ValidationException("Day cannot be null.");
		requireName("Day", d.getName());

		Day found = dayRepo.findByName(d.getName());
		if (found == null)
			throw new NotFoundException("Day not found: " + d.getName());

		return found.getGames().stream().collect(
				Collectors.toMap(game -> game, playerRepo::findPlayersByGame, (a, b) -> a, LinkedHashMap::new));
	}
}