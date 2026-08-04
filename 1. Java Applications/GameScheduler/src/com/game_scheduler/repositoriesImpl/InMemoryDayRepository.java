package com.game_scheduler.repositoriesImpl;

import java.util.List;
import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.exceptions.ValidationException;
import com.game_scheduler.initializer.SchedularDB;
import com.game_scheduler.repositories.DayRepository;

public class InMemoryDayRepository implements DayRepository {

	@Override
	public Day save(Day day) {
		if (day == null || day.getName() == null || day.getName().isBlank()) {
			throw new ValidationException("Day must have a non-blank name.");
		}
		return SchedularDB.insertDay(day);
	}

	@Override
	public Day findByName(String dayName) {
		if (dayName == null || dayName.isBlank()) {
			throw new ValidationException("Day name must be non-blank.");
		}
		return SchedularDB.findDayByName(dayName);
	}

	@Override
	public List<Day> findDaysByGame(Game game) {
		if (game == null || game.getName() == null || game.getName().isBlank()) {
			throw new ValidationException("Game must be provided.");
		}
		return SchedularDB.queryDaysByGame(game);
	}
}