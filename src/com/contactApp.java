package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class contactApp {
	public static List<Contact> contacts;
	public static void main(String []args) {
		int choice, id;
		Contact c;
		Scanner sc = new Scanner(System.in);
		contacts = new ArrayList<Contact>();
		while(true) {
			System.out.println("----------MENU----------");
			System.out.println("1: Add contact object");
			System.out.println("2: Remove contact");
			System.out.println("3: Search contact by name");
			System.out.println("4: Search contact by numbers");
			System.out.println("5: Add contact number");
			System.out.println("6: Sort contact by names");
			System.out.println("7: Read contact from file");
			System.out.println("8: Serialize");
			System.out.println("9: Deserialize");
			System.out.println("10: Populate from db");
			System.out.println("11: Add list to existing contact list");
			System.out.println("12: Display all contacts");
			System.out.println("13: Exit");
			choice = sc.nextInt();
			switch(choice) {
				case 1: System.out.print("Enter ContactID: ");
						id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter contact name: ");
						String contactName = sc.nextLine();
						System.out.print("Enter email id: ");
						String email = sc.nextLine();
						System.out.print("Enter contact numbers in comma: ");
						List<String> numbers = new ArrayList<String>();
						String []nums = sc.nextLine().split(",");
						numbers = Arrays.asList(nums);
						c = new Contact(id, contactName, email, numbers);
						ContactService.addContact(c, contacts);
						System.out.print("Contact added \n");
						break;
				
				case 2: System.out.print("Enter contact id: ");
						id = sc.nextInt();
						c = new Contact();
						for(Contact each: contacts)
							if(each.getContactID() == id) {
								c = each;
								break;
							}
						try {
							ContactService.RemoveContact(c, contacts);
							System.out.println("Removed success !!");
						} catch (ContactNotFoundException e) {
							e.printStackTrace();
						}
						break;
						
				case 3: System.out.println("Enter name");
						try {
							sc.nextLine();
							c = ContactService.searchContactByName(sc.nextLine(), contacts);
							System.out.println("contact found");
						} catch (ContactNotFoundException e) {
							
							e.printStackTrace();
						}
						break;
						
				case 4: System.out.print("Enter number: ");
						sc.nextLine();
						try {
							List<Contact> filter = ContactService.SearchContactByNumber(sc.nextLine(), contacts);
							ContactService.displayAllContacts(filter);
						} catch (ContactNotFoundException e) {
							e.printStackTrace();
						}
						break;
						
				case 5: System.out.print("Enter contact id: ");
						id = sc.nextInt();
						System.out.print("Enter contact number");
						sc.nextLine();
						String number = sc.nextLine();
						ContactService.addContactNumber(id, number, contacts);
						System.out.print("Added success !! \n");
						break;
						
				case 6: ContactService.sortContactsByName(contacts);
						ContactService.displayAllContacts(contacts);
						break;
				
				case 7:	ContactService.readContactsFromFile(contacts, "C:\\Users\\lenovo\\Desktop\\Persistent docs\\learnings\\java\\ELTP_Assignments\\ass10\\Ass10_Pritish_Patel\\src\\com\\Contact.txt");
						break;
				
				case 8: ContactService.serializeContactDetails(contacts, "C:\\Users\\lenovo\\Desktop\\Persistent docs\\learnings\\java\\ELTP_Assignments\\ass10\\Ass10_Pritish_Patel\\src\\com\\Contact.txt");
						break;
				
				case 9: ContactService.deSerializeContactDetails("C:\\Users\\lenovo\\Desktop\\Persistent docs\\learnings\\java\\ELTP_Assignments\\ass10\\Ass10_Pritish_Patel\\src\\com\\Contact.txt");
						break;
				
				case 10: contacts = ContactService.populateContactFromDb();
						 ContactService.displayAllContacts(contacts);
						 break;
				
				case 11: //ContactService.addContacts(contacts, newContacts) form new contacts here later
						 break;
				
				case 12: ContactService.displayAllContacts(contacts);
						 break;
				
				case 13: sc.close();
						 System.exit(0);
						 break;
				
				default: System.out.print("Wrong choice !!");
			}
		}		
	}
}
