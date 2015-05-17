package gui;
import java.util.EventObject;

public class FormEvent extends EventObject{

	private String name;
	private String occupation;
	private int ageCategory;
	private String employment;
	private boolean isCitizen;
	private String taxId;
	private String gender;
	
	public FormEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public FormEvent(Object source, String name, String occupation, 
			int ageCategory, String employment, boolean isCitizen, 
			String taxId, String gender) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.employment = employment;
		this.isCitizen = isCitizen;
		this.taxId = taxId;
		this.gender = gender;
	}
	
	public boolean isCitizen() {
		return isCitizen;
	}

	public String getTaxId() {
		return taxId;
	}
	
	public String getGender() {
		return gender;
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
	
	public int getAgeCategory() {
		return ageCategory;
	}
	
	public String getEmployment() {
		return employment;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
}
