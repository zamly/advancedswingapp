package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;
import gui.FormEvent;

public class Controller {
	
	Database database = new Database();
	
	public void addPerson(FormEvent fe) {
		String name = fe.getName();
		String occupation = fe.getOccupation();
		int ageCat = fe.getAgeCategory();
		String emp = fe.getEmployment();
		String taxId = fe.getTaxId();
		String gen = fe.getGender();
		boolean usCitizen = fe.isCitizen();
		
		AgeCategory ageCategory;
		switch (ageCat) {
		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		default:
			ageCategory = AgeCategory.adult;
			break;
		}
		
		EmploymentCategory employmentCategory;
		switch (emp) {
		case "Employed":
			employmentCategory = EmploymentCategory.employed;
			break;
		case "Self-Employed":
			employmentCategory = EmploymentCategory.selfEmployed;
			break;
		case "Unemployed":
			employmentCategory = EmploymentCategory.unemployed;
			break;
		default:
			employmentCategory = EmploymentCategory.other;
			break;
		}
		
		Gender gender;
		switch (gen) {
		case "Male":
			gender = Gender.male;
			break;
		case "Female":
			gender = Gender.female;
			break;
		default:
			gender = Gender.male;
			break;
		}
		
		Person person = new Person(
				name, 
				occupation, 
				ageCategory, 
				employmentCategory, 
				taxId, 
				usCitizen, 
				gender);
		
		database.addPerson(person);
//		System.out.println("Person Added. "+ person.getAgeCategory());
	}
	
	public void configure(int port, String user, String pass) throws Exception {
		database.configure(port, user, pass);
	}
	
	public void connect() throws Exception {
		database.connect();
	}
	
	public void save() throws SQLException {
		database.save();
	}
	
	public void load() throws SQLException {
		database.load();
	}
	
	public void disconnect() {
		database.disconnect();
	}
	
	public void removePerson(int index) {
		database.removePerson(index);
	}
	
	
	public List<Person> getPeople() {
		return database.getPeople();
	}
	
	public void saveToFile(File file) throws IOException {
		database.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException {
		database.loadFromFile(file);
	}

}
