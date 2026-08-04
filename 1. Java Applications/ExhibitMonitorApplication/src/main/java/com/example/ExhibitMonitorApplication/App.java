package com.example.ExhibitMonitorApplication;

//Copy the file from the following path in incoming folder: 

//C:\Users\mujangid\OneDrive - Capgemini\Desktop\Training\Trainings\4. NGT Engagement Program\Others2\7. Week 7\Others\Invalid & Valid Files 12 March

//Use this command in powershell to copy, to not to change the created/modified timestamp.

//Copy-Item "C:\Users\mujangid\OneDrive - Capgemini\Desktop\Training\Trainings\4. NGT Engagement Program\Others2\7. Week 7\Others\Invalid & Valid Files 12 March\kyc.csv" "C:\Users\mujangid\eclipse-workspace\ExhibitMonitorApplication\data\incoming" -Force

//Copy-Item "C:\Users\mujangid\OneDrive - Capgemini\Desktop\Training\Trainings\4. NGT Engagement Program\Others2\7. Week 7\Others\Invalid & Valid Files 12 March\*.csv" "C:\Users\mujangid\eclipse-workspace\ExhibitMonitorApplication\data\incoming" -Force

/**
 * Application Entry Point
 *
 * Responsibilities: 1. Load YAML configuration. 2. Initialize database tables.
 * 3. Start the Polar monitoring engine.
 */
public class App {

	public static void main(String[] args) {

		try {

			/*
			 * Load application configuration.
			 */
			ExhibitUtil.loadYaml();

			/*
			 * Initialize database.
			 */
			DBUtil.init();

			System.out.println("[MAIN] Exhibit Monitor Application Started");

			/*
			 * Start file monitoring thread.
			 */
			Thread polarThread = new Thread(new Polar(), "Polar-Thread");

			polarThread.start();

			System.out.println("[MAIN] Polar Monitor Running...");

		} catch (Exception e) {

			System.out.println("[FATAL ERROR] Application Startup Failed");

			e.printStackTrace();
		}
	}
}