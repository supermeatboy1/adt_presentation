import java.util.*;

public class JMoney {
	public static void main(String[] args) {
		boolean running = true;
		Scanner scan = new Scanner(System.in);
		HashMap<String, Wallet> wallets = new HashMap<String, Wallet>();

		while (running) {
			clearConsole();
			
			displaySplash();

			if (!wallets.isEmpty())
				Wallet.printTableHeader();
			
			// O(n)
			for (String account_name : wallets.keySet())
				wallets.get(account_name).printTabulated();

			if (!wallets.isEmpty())
				System.out.println();

			System.out.println("Available Actions: ");
			System.out.println("* 1 - Create a new account");
			System.out.println("* 2 - Delete an account by name");
			System.out.println("* 3 - Deposit to an account");
			System.out.println("* 4 - Withdraw from an account");
			System.out.println("* 0 - Exit");
			System.out.println();

			System.out.print("Please choose an action: ");
			int action = -1;
			try {
				action = Integer.parseInt(scan.nextLine());
				switch (action) {
				case 0: /* Exit */
					running = false;
					break;
				case 1: /* Create new account */
					Wallet wallet = Wallet.creationPrompt(scan, wallets);
					if (wallet != null) {
						long start = System.nanoTime();
						wallets.put(wallet.getName().toLowerCase(), wallet);
						long operation_time = System.nanoTime() - start;
						System.out.println("Account added.");
						System.out.printf("Operation time: %d nanoseconds%n", operation_time);
					}
					break;
				case 2: /* Delete account */
					Wallet found = findWalletByName(scan, wallets);
					if (found != null && found.authenticate(scan)) {
						long start = System.nanoTime();
						wallets.remove(found.getName().toLowerCase());
						long operation_time = System.nanoTime() - start;
						System.out.println("Account (" + found.getName() + ") removed.");
						System.out.printf("Operation time: %d nanoseconds%n", operation_time);
					}
					break;
				case 3: /* Deposit */
					found = findWalletByName(scan, wallets);
					
					if (found != null) {
						found.depositPrompt(scan);
					}
					break;
				case 4: /* Withdraw */
					found = findWalletByName(scan, wallets);
					
					if (found != null) {
						found.withdrawPrompt(scan);
					}
					break;
				default:
					System.out.println("Invalid action.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid action.");
			}
			/* Skip if the action is Exit. */
			if (action != 0) {
				System.out.println("Press Enter to continue.");
				scan.nextLine();
			}
		}

		scan.close();
		System.out.println("App closed.");
	}

	public static Wallet findWalletByName(Scanner scan, HashMap<String, Wallet> wallets) {
		System.out.print("What is the account's name? ");
		String name = scan.nextLine();
		String name_lower = name.toLowerCase();
		// O(n)
		if (wallets.containsKey(name_lower)) {
			return wallets.get(name_lower);
		} else {
			System.out.printf("Account [%s] not found.%n", name);
			return null;
		}
	}
	// https://stackoverflow.com/a/40041221
	public static void clearConsole() {
		System.out.println("\033\143");
	}
	public static void displaySplash() {
		System.out.println(
				  " ╦ ╦┌─┐┬  ┬  ┌─┐┌┬┐  ╔╦╗┌─┐┌┐┌┌─┐┌─┐┌─┐┬─┐\n"
				+ " ║║║├─┤│  │  ├┤  │   ║║║├─┤│││├─┤│ ┬├┤ ├┬┘\n"
				+ " ╚╩╝┴ ┴┴─┘┴─┘└─┘ ┴   ╩ ╩┴ ┴┘└┘┴ ┴└─┘└─┘┴└─\n");
	}
}
