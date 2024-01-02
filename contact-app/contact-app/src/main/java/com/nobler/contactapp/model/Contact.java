package com.nobler.contactapp.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Contact {
	private long id;
	
	private String lastName;
	
	private String firstName;
	
	private Date birthDate;
	
	private String email;
	
	private String phone;
	
	private String address;
	
	private String city;
	
	private long stateID;
	
	private String zipCode;
	
	private SerialBlob profilePic;
	
	private BigDecimal salary;
	
	private Date edited;
	
	private String timeOfBirth;
	
	private String filename;
	
	private Blob blob;
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

	public String getfilename() {
		return filename;
	}

	public void setfilename(String profilePicFileName) {
		this.filename = profilePicFileName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getStateID() {
		return stateID;
	}

	public void setStateID(long stateID) {
		this.stateID = stateID;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public SerialBlob getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(SerialBlob profilePic) {
		this.profilePic = profilePic;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public Date getEdited() {
		return edited;
	}

	public void setEdited(Date edited) {
		this.edited = edited;
	}

	public String getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(String timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}
	
	public Contact()
	{
		this.setId(0);
		this.setLastName(StringUtils.EMPTY);
		this.setFirstName(StringUtils.EMPTY);
		this.setBirthDate(null);
		this.setEmail(StringUtils.EMPTY);
		this.setPhone(StringUtils.EMPTY);
		this.setAddress(StringUtils.EMPTY);
		this.setCity(StringUtils.EMPTY);
		this.setStateID(0);
		this.setZipCode(StringUtils.EMPTY);
		this.setProfilePic(null);
		this.setSalary(BigDecimal.valueOf(0));
		this.setEdited(null);
		this.setTimeOfBirth(StringUtils.EMPTY);
		this.setfilename(StringUtils.EMPTY);
	}
}
