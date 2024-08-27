import java.util.*;

public class ContactInfo {
	private String phone_number, email, address;
	
	public ContactInfo(String phone_number, String email, String address) {
		this.phone_number = phone_number;
		this.email = email;
		this.address = address;
	}
	
	public void printTabulated() {
		System.out.printf("%-16s %-24s %-24s", phone_number, email, address);
	}

	/*******************************************************************/

	public static void printTableHeader() {
		System.out.printf("%-16s %-24s %-24s", "Phone Number", "E-mail", "Address");
	}
	
	public static ContactInfo creationPrompt(Scanner scan) {		
		System.out.print("Enter your phone number: ");
		String phone_number = scan.nextLine();
		System.out.print("Enter your e-mail: ");
		String email = scan.nextLine();
		System.out.print("Enter your address: ");
		String address = scan.nextLine();
		
		return new ContactInfo(phone_number, email, address);
	}
}
