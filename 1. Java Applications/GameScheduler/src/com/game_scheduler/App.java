package com.game_scheduler;

import java.util.Scanner;

import com.game_scheduler.entities.Day;
import com.game_scheduler.entities.Game;
import com.game_scheduler.entities.Player;
import com.game_scheduler.exceptions.SchedulerException;
import com.game_scheduler.services.SchedularService;
import com.game_scheduler.servicesImpl.SchedularServiceImpl;
import com.game_scheduler.vo.GameVO;

public class App {
	private static int readChoice(Scanner sc) {
		String raw = sc.nextLine().trim();
		try {
			return Integer.parseInt(raw);
		} catch (NumberFormatException nfe) {
			System.out.println("Please enter a valid number (0-3).");
			return -1;
		}
	}

	private static String readNonBlank(Scanner sc, String prompt) {
		System.out.print(prompt);
		String v = sc.nextLine();
		if (v == null || v.trim().isBlank()) {
			System.out.println("Input cannot be blank. Try again.");
			return null;
		}
		return v.trim();
	}

	public static void main(String[] args) {
		SchedularService service = new SchedularServiceImpl();
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.println("\n=== GAME SCHEDULER ===");
				System.out.println("1. Game-wise Report");
				System.out.println("2. Player-wise Report");
				System.out.println("3. Day-wise Report");
				System.out.println("0. Exit");
				System.out.print("Choice: ");

				int ch = readChoice(sc);
				if (ch == -1)
					continue;

				try {
					switch (ch) {
					case 1 -> {
						String gName = readNonBlank(sc, "Enter Game: ");
						if (gName == null)
							continue;
						GameVO vo = service.gameWiseReport(new Game(gName));
						System.out.println("\nGAME: " + vo.getGame().getName());
						System.out.println("\nPLAYERS:");
						vo.getPlayers().forEach(p -> System.out.println(" - " + p.getName()));
						System.out.println("DAYS:");
						vo.getDays().forEach(d -> System.out.println(" - " + d.getName()));
					}
					case 2 -> {
						String pName = readNonBlank(sc, "Enter Player: ");
						if (pName == null)
							continue;
						var map = service.playerWiseReport(new Player(pName));
						System.out.println("\nPLAYER-WISE:");
						map.forEach((g, days) -> System.out.println(
								g.getName() + " : " + String.join(", ", days.stream().map(Day::getName).toList())));
					}
					case 3 -> {
						String dName = readNonBlank(sc, "Enter Day: ");
						if (dName == null)
							continue;
						var map = service.dayWiseReport(new Day(dName));
						System.out.println("\nDAY-WISE:");
						map.forEach((g, players) -> System.out.println(g.getName() + " : "
								+ String.join(", ", players.stream().map(Player::getName).toList())));
					}
					case 0 -> {
						System.out.println("Bye!");
						return;
					}
					default -> System.out.println("Invalid choice. Select 0–3.");
					}
				} catch (SchedulerException se) {

					System.out.println("Error: " + se.getMessage());
				} catch (Exception ex) {

					System.out.println("Unexpected error. Please try again.");
				}
			}
		}
	}
}