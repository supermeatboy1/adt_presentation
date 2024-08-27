import java.util.*;

public class Wallet {
	private double balance;
	private String name;
	private String password;
	private ContactInfo contact_info;

	public Wallet(String name, ContactInfo contact_info, String password) {
		balance = 0.0;
		this.name = name;
		this.password = password;
		this.contact_info = contact_info;
	}

	public void deposit(double amount) {
		balance += amount;
		System.out.printf("Amount %.2f deposited to the account [%s]%n", amount, name);
	}
	
	public void depositPrompt(Scanner scan) {
		printBalance();
		boolean done = false;
		while (!done) {
			try {
				System.out.print("Enter the amount to deposit: ");
				double amount = Double.parseDouble(scan.nextLine());
				deposit(amount);
				printBalance();
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid amount.");
			}
		}
	}

	public void withdrawPrompt(Scanner scan) {
		printBalance();
		boolean done = false;
		while (!done) {
			try {
				System.out.print("Enter the amount to withdraw: ");
				double amount = Double.parseDouble(scan.nextLine());
				if (withdrawAttempt(amount)) {
					/* Print balance if withdrawal is successful. */
					printBalance();
				}
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid amount.");
			}
		}
	}

	public boolean withdrawAttempt(double amount) {
		if (amount > balance) {
			// Prevent removing too much money from the account.
			System.out.printf("Amount to withdraw (%.2f) is more than the current balance (%.2f).%n", amount, balance);
			System.out.println("Cannot withdraw.");
			return false;
		}
		System.out.printf("Amount %.2f withdrawn from the account: %s%n", amount, name);
		System.out.println("Withdrawal successful.");
		balance -= amount;
		return true;
	}

	public boolean authenticate(Scanner scan) {
		System.out.println("Authentication is needed.");
		int attempt_num = 1;
		do {
			System.out.println("Attempt #" + attempt_num + ":");
			System.out.print("Enter your password: ");
			String attempt_pass = scan.nextLine();
			if (attempt_pass.equals(password)) {
				System.out.println("Authentication successful.");
				return true;
			}
			System.out.println("Invalid password.");
			attempt_num++;
		} while (attempt_num <= 3);
		System.out.println("You have exhausted all your attempts.");
		return false;
	}

	public double getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void printBalance() {
		System.out.printf("Account [%s] has $%.2f %n", name, balance);
	}

	public void printTabulated() {
		System.out.printf("%-24s %-12.2f ", name, balance);
		contact_info.printTabulated();
		System.out.println();
	}

	public ContactInfo getContactInfo() {
		return contact_info;
	}

	/*******************************************************************/

	public static void printTableHeader() {
		System.out.printf("%-24s %-12s ", "Name", "Balance");
		ContactInfo.printTableHeader();
		System.out.println();
	}

	public static Wallet creationPrompt(Scanner scan) {
		System.out.print("Enter your name: ");
		String name = scan.nextLine();

		ContactInfo contact_info = ContactInfo.creationPrompt(scan);

		System.out.print("Enter your password: ");
		String password = scan.nextLine();

		return new Wallet(name, contact_info, password);
	}
}
