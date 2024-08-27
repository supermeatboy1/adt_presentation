import java.util.*;

public class JMoney {
	public static void main(String[] args) {
		boolean running = true;
		Scanner scan = new Scanner(System.in);
		ArrayList<Wallet> wallets = new ArrayList<Wallet>();

		while (running) {
			clearConsole();
			
			displaySplash();

			if (!wallets.isEmpty())
				Wallet.printTableHeader();

			for (Wallet wallet : wallets)
				wallet.printTabulated();

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
					wallets.add(Wallet.creationPrompt(scan));
					System.out.println("Account added.");
					break;
				case 2: /* Delete account */
					Wallet found = findWalletByName(scan, wallets);
					if (found != null) {
						wallets.remove(found);
						System.out.println("Account (" + found.getName() + ") removed.");
					}
					break;
				case 3: /* Deposit */
					found = findWalletByName(scan, wallets);
					
					if (found != null && found.authenticate(scan)) {
						found.depositPrompt(scan);
					}
					break;
				case 4: /* Withdraw */
					found = findWalletByName(scan, wallets);
					
					if (found != null && found.authenticate(scan)) {
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

	public static Wallet findWalletByName(Scanner scan, ArrayList<Wallet> wallets) {
		System.out.print("What is the account's name? ");
		String name = scan.nextLine();
		for (Wallet wallet : wallets) {
			if (wallet.getName().equalsIgnoreCase(name)) {
				return wallet;
			}
		}
		System.out.printf("Account [%s] not found.%n", name);
		return null;
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
