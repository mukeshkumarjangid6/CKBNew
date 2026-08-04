package com.game_scheduler.initializer;

import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.repositories.DayRepository;
import com.game_scheduler.repositories.GameRepository;
import com.game_scheduler.repositories.PlayerRepository;

public class SampleRecordDB {
	private static SampleRecordDB recordDB = null;

	private SampleRecordDB() {
	}

	public static SampleRecordDB getInstance() {
		recordDB = (recordDB == null) ? new SampleRecordDB() : recordDB;
		return recordDB;
	}

	public void seed(GameRepository gameRepo, PlayerRepository playerRepo, DayRepository dayRepo) {

		// 1) Games
		Game cricket = gameRepo.save(new Game("Cricket"));
		Game football = gameRepo.save(new Game("Football"));
		Game chess = gameRepo.save(new Game("Chess"));
		Game tennis = gameRepo.save(new Game("Tennis"));
		Game badminton = gameRepo.save(new Game("Badminton"));

		// 2) Players (attach games, then save)
		Player alice = new Player("Alice");
		alice.addGame(cricket);
		playerRepo.save(alice);

		Player bob = new Player("Bob");
		bob.addGame(football);
		playerRepo.save(bob);

		Player charlie = new Player("Charlie");
		charlie.addGame(chess);
		playerRepo.save(charlie);

		Player david = new Player("David");
		david.addGame(cricket);
		david.addGame(football);
		playerRepo.save(david);

		Player ella = new Player("Ella");
		ella.addGame(tennis);
		playerRepo.save(ella);

		// 3) Days (attach games, then save)
		Day day1 = new Day("Day1");
		day1.addGame(cricket);
		day1.addGame(chess);
		dayRepo.save(day1);

		Day day2 = new Day("Day2");
		day2.addGame(football);
		dayRepo.save(day2);

		Day day3 = new Day("Day3");
		day3.addGame(cricket);
		day3.addGame(badminton);
		dayRepo.save(day3);

		Day day4 = new Day("Day4");
		day4.addGame(tennis);
		dayRepo.save(day4);

		Day day5 = new Day("Day5");
		day5.addGame(football);
		day5.addGame(chess);
		dayRepo.save(day5);
	}
}