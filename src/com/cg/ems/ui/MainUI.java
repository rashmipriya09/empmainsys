package com.cg.ems.ui;

import java.text.ParseException;
import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;

import com.cg.ems.exception.EMSException;
import com.cg.ems.service.IUserLoginService;
import com.cg.ems.service.UserLoginServiceImpl;

public class MainUI {

	public static void main(String[] args) throws ParseException {
		Scanner scanner = new Scanner(System.in);
		PropertyConfigurator.configure("resources/log4j.properties");

		int choice = -1;
		int loginAttempts = 0;
		IUserLoginService userService = new UserLoginServiceImpl();

		while (choice != 2 && loginAttempts <= 3) {
			System.out.println("************************");
			System.out.println("Employee Maintenance System ");
			System.out.println("************************");
			System.out.print("1.Login\n2.Exit \n");
			System.out.print("Enter your choice:");
			choice = scanner.nextInt();

			if (choice == 1) {
				System.out.println("Enter the Login credentials:\n");
				System.out.println("Enter Username:");
				String userName = scanner.next();
				System.out.println("Enter Password:");
				String password = scanner.next();
				loginAttempts++;
				try {
					String role = userService.getRole(userName, password);
					String userId = userService.getUserId(userName, password);
					
					if ("admin".equals(role)) {
						AdminConsole adminConsole = new AdminConsole(userName);
						adminConsole.start();
					} else {
						UserConsole console = new UserConsole(userName, userId);
						console.start();
					}
				} catch (EMSException exception) {
					System.err.println(exception.getMessage());
				}
			} else if (choice >= 3) {
				System.out.println("--------------------------");
				System.out.println("Please enter a valid choice!");
				System.out.println("--------------------------");
			}
		}
		scanner.close();
		System.out.println("Thank You!");

	}

}
