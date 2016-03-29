package de.rwth.i5.hiwi.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import de.rwth.i5.hiwi.Controller.Controller;
import de.rwth.i5.hiwi.DBUtility.ConnectionManager;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Starting application...");

		Connection conn = ConnectionManager.getInstance().getConnection();

		try {
			conn.setAutoCommit(false);
			Controller controller = new Controller();
			while (true) {
				System.out.println(
						"Enter Your choice: 0.Exit 1.Insert a PCSystem 2.Show all PCSystems 3.Export all PCSystems");
				int choice = scanner.nextInt();
				switch (choice) {
				case 0:
					System.out.println("Application Closed...");
					return;

				case 1:
					controller.insertPCStore();
					conn.commit();
					System.out.println("Commit changes to database!");
					break;

				case 2:
					controller.printAllPCSystems();
					break;

				case 3:
					controller.exportAllPCSystemstoXML();
					break;

				default:
					System.out.println("Wrong Choice");
					break;
				}

			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			try {
				System.err.println("Roll backing transaction!");
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
		}

		ConnectionManager.getInstance().close("Closing connection...");

		System.out.println("Application Closed...");
	}

}
