package com.game_scheduler.services;

import java.util.List;
import java.util.Map;
import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.vo.GameVO;

public interface SchedularService {

	Game addGame(Game g);

	Player addPlayer(Player p);

	Day addDay(Day d);

	GameVO gameWiseReport(Game g);

	Map<Game, List<Day>> playerWiseReport(Player p);

	Map<Game, List<Player>> dayWiseReport(Day d);
}