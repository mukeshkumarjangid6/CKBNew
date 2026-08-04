package com.game_scheduler.repositories;

import java.util.List;
import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;

/**
 * Defines the contract for all Day‑related data operations. This abstraction
 * layer decouples the service layer from the underlying storage mechanism
 * (in‑memory, DB, external service).
 *
 * Primary business responsibility: - Provide day lookups for reporting -
 * Maintain Day → Games mappings
 */
public interface DayRepository {

	/**
	 * Persists a Day entity into the system. Used during system initialization and
	 * when administrators add new days to the schedule.
	 */
	Day save(Day day);

	/**
	 * Fetches a Day by its business key (name). Central to Day‑wise reporting and
	 * schedule lookup.
	 */
	Day findByName(String dayName);

	/**
	 * Returns all days on which a particular game is scheduled. Supports Game →
	 * Days → Players reporting flow.
	 */
	List<Day> findDaysByGame(Game game);
}