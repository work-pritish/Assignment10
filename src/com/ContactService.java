package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import util.conProvider;

class ContactNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	ContactNotFoundException(String s) {
		super(s);
	}
}

public class ContactService {
	
	public static void addContact(Contact contact,List<Contact> contacts) {
		contacts.add(contact);
	}
	
	public static void RemoveContact(Contact contact, List<Contact> contacts) throws ContactNotFoundException {
		if(contacts.contains(contact))
			contacts.remove(contact);
		else
			throw new ContactNotFoundException("contact not found !!");
	}
	
	public static Contact searchContactByName(String name, List<Contact> contacts) throws ContactNotFoundException {
		for(Contact c: contacts) {
			if(c.getContactName().equals(name))
				return c;
		}
		throw new ContactNotFoundException("contact not found !!");
	}
	
	public static boolean isFound(String s1, String s2) {
		int s2_pointer = 0, i;
		for(i = 0; i < s1.length(); i++) {
			if(s1.charAt(i) == s2.charAt(s2_pointer)) {
				s2_pointer++;
			}
			else {
				s2_pointer = 0;
                if(s1.charAt(i) == s2.charAt(s2_pointer))
                    s2_pointer++;
			}
            if(s2_pointer == s2.length())
					return true;
			
		}
		return false;
	}
	
	public static List<Contact> SearchContactByNumber(String number, List<Contact> contacts) throws ContactNotFoundException {
		List<Contact> result = new ArrayList<Contact>();
		for(Contact c: contacts) {
			List<String> allNumbers = c.getContactNumber();
			for(String each_num: allNumbers) {
				if(isFound(each_num, number))
					result.add(c);
			}
		}
		if(result.size() == 0)
			throw new ContactNotFoundException("contact not found !!");
		return result;
	}
	
	public static void addContactNumber(int contactId, String contactNo, List<Contact> contacts) {
		for(Contact c: contacts) {
			if(c.getContactID() == contactId) {
				List<String> numbers = new ArrayList<String>();
				numbers.add(contactNo);
				for(String nums: c.getContactNumber()) {
					numbers.add(nums);
				}
				c.setContactNumber(numbers);
				break;
			}
		}
	}
	
	public static void sortContactsByName(List<Contact> contacts) {
		Collections.sort(contacts, new SortbyName());
	}
	
	public static void readContactsFromFile(List<Contact> contacts, String fileName) {
		File file = new File(fileName);
		Scanner reader;
		try {
			reader = new Scanner(file);
			while(reader.hasNextLine()) {
				String data[] = reader.nextLine().split(",");
				List<String> numbers = new ArrayList<String>();
				for(int i = 3; i < data.length; i++)
					numbers.add(data[i]);
				System.out.println("Contact id: " + data[0]);
				System.out.println("Contact name: " + data[1]);
				System.out.println("Email: " + data[2]);
				System.out.println("Contact numbers " + numbers);
				System.out.println("------>");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void serializeContactDetails(List<Contact> contacts, String fileName) {
		try {
	         for(Contact c: contacts) {
	        	 FileOutputStream fileOut = new FileOutputStream(fileName);
	        	 ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        	 out.writeObject(c);	        	 
	        	 out.close();
	        	 fileOut.close();
	        	 System.out.printf("Serialized data is saved in tmp");
	         }
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
	}
	public static void deSerializeContactDetails(String fileName) {
		try {
	         FileInputStream fis = new FileInputStream(fileName);
	         ObjectInputStream ois = new ObjectInputStream(fis);
	        	 try {
					ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}	        	 
	        	 ois.close();
	        	 fis.close();
	        	 System.out.printf("deSerialized data");
	      }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static List<Contact> populateContactFromDb() {
		String query = "Select * from ContactSchema;";
		Connection con = conProvider.createCon();
		List<Contact> allContacts = new ArrayList<Contact>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Contact c = new Contact();
				c.setContactID(rs.getInt(1));
				c.setContactName(rs.getString(2));
				c.setEmail(rs.getString(3));
				c.setContactNumber(Arrays.asList(rs.getString(4)));
				allContacts.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allContacts;
	}
	
	public static boolean addContacts(List<Contact> existingContact, List<Contact> newContacts) {
		boolean flag = false;
		existingContact.addAll(newContacts);
		return flag;
	}
	
	public static void displayAllContacts(List<Contact> contacts) {
		for(Contact c: contacts) {
			System.out.println("Contact id: " + c.getContactID());
			System.out.println("Contact name: " + c.getContactName());
			System.out.println("Email: " + c.getEmail());
			System.out.println("Contact numbers " + c.getContactNumber());
			System.out.println("-----------------------");
		}
	}
}


class SortbyName implements Comparator<Contact> {
    public int compare(Contact a, Contact b) {
        return a.getContactName().compareTo(b.getContactName());
    }
}