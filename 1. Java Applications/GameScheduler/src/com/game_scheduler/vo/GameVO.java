package com.game_scheduler.vo;

import java.util.List;

import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;

public class GameVO {
	private Game game;
	private List<Player> players;
	private List<Day> days;

	public GameVO() {
		super();
	}

	public GameVO(Game game, List<Player> players, List<Day> days) {
		super();
		this.game = game;
		this.players = players;
		this.days = days;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Player> getPlayers() {
		return players == null ? List.of() : players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Day> getDays() {
		return days == null ? List.of() : days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

}