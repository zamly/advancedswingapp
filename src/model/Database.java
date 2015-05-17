package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	
	private List<Person> people;
	private Connection con;
	
	private int port;
	private String user;
	private String password;
	
	public Database() {
		people = new LinkedList<>();
	}
	
	public void configure(int port, String user, String pass) throws Exception {
		this.port = port;
		this.user = user;
		this.password = pass;
		
		//disconnect();
		
	}
	
	public void connect() throws Exception {
		if(con != null) return;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found.");
		}
		
		String url = "jdbc:mysql://localhost:"+port+"/swingTest";
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connecting to DB");
		
	}
	
	public void disconnect() {
		if (con == null) return;
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void save() throws SQLException {
		
		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStat =  con.prepareStatement(checkSql);
		
		String insertSql = "insert into people(id, name, age, employment_status, tax_id, us_citizen, gender, occupation) values (?,?,?,?,?,?,?,?)";
		PreparedStatement insStat = con.prepareStatement(insertSql);
		
		String updSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updStat = con.prepareStatement(updSql);
		
		for(Person person: people) {
			int id = person.getId();
			String name = person.getName();
			String age = person.getAgeCategory().name();
			String occupation = person.getOccupation();
			String taxId = person.getTaxId();
			boolean usCitizen = person.isCitizen();
			String gender = person.getGender().name();
			String empStatus = person.getEmployment().name();
			
			checkStat.setInt(1, id);
			
			ResultSet checkResult = checkStat.executeQuery();
			checkResult.next();
			int count = checkResult.getInt(1); 
			
			//System.out.println("Count for person with ID: "+id+" is "+count);
		
			if (count == 0) {
				System.out.println("saving person with ID: "+id);
				int col = 1;
				insStat.setInt(col++, id);
				insStat.setString(col++, name);
				insStat.setString(col++, age);
				insStat.setString(col++, empStatus);
				insStat.setString(col++, taxId);
				insStat.setBoolean(col++, usCitizen);
				insStat.setString(col++, gender);
				insStat.setString(col++, occupation);
				
				insStat.executeUpdate();
			} else {
				System.out.println("updating person with ID: "+id);
				int col = 1;
				updStat.setString(col++, name);
				updStat.setString(col++, age);
				updStat.setString(col++, empStatus);
				updStat.setString(col++, taxId);
				updStat.setBoolean(col++, usCitizen);
				updStat.setString(col++, gender);
				updStat.setString(col++, occupation);
				updStat.setInt(col++, id);
				
				updStat.executeUpdate();
			}
			
			checkResult.close();
		}
		
		checkStat.close();
		insStat.close();
		updStat.close();
	}
	
	public void load() throws SQLException {
		people.clear();
		
		String sql = "select * from people order by name";
		Statement selectStat = con.createStatement();
		
		ResultSet result = selectStat.executeQuery(sql);
		
		while (result.next()) {
			int id = result.getInt("id");
			String name = result.getString("name");
			String age = result.getString("age");
			String emp = result.getString("employment_status");
			String taxId = result.getString("tax_id");
			boolean isCitizen = result.getBoolean("us_citizen");
			String gender = result.getString("gender");
			String occ = result.getString("occupation");
			
			Person p = new Person(id, name, occ, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), taxId, isCitizen, Gender.valueOf(gender));
			people.add(p);
			System.out.println(p);
		}
		
		result.close();
		selectStat.close();
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}
	
	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Person[] person = people.toArray(new Person[people.size()]);
		
		//if arraylist was directly written as an object the type
		// would have been erased. so when restoring would get an unchecked 
		//conversion warning. Arraylist has to be converted to an array.
		oos.writeObject(person);
		oos.close();
	}
	
	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Person[] persons = (Person[]) ois.readObject();
			
			people.clear();
			people.addAll(Arrays.asList(persons));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ois.close();
	}

}
