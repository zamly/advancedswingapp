package model;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static int count = 1;
	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory employment;
	private boolean isCitizen;
	private String taxId;
	private Gender gender;
	
	public Person(String name, String occupation, 
			AgeCategory ageCategory, EmploymentCategory employmentCategory, 
			String taxId, boolean usCitizen, Gender gender) {
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.employment = employmentCategory;
		this.isCitizen = usCitizen;
		this.taxId = taxId;
		this.gender = gender;
		this.id = count++;
	}
	
	public Person(int id,String name, String occupation, 
			AgeCategory ageCategory, EmploymentCategory employmentCategory, 
			String taxId, boolean usCitizen, Gender gender) {

		this(name, occupation, ageCategory, employmentCategory, taxId, usCitizen, gender);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public AgeCategory getAgeCategory() {
		return ageCategory;
	}
	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}
	public EmploymentCategory getEmployment() {
		return employment;
	}
	public void setEmployment(EmploymentCategory employment) {
		this.employment = employment;
	}
	public boolean isCitizen() {
		return isCitizen;
	}
	public void setCitizen(boolean isCitizen) {
		this.isCitizen = isCitizen;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return id+" : "+name;
	}

}
