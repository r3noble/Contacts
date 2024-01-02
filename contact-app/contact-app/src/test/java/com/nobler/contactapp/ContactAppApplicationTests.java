package com.nobler.contactapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nobler.contactapp.model.Contact;
import com.nobler.contactapp.model.StateTable;
import com.nobler.contactapp.service.ContactService;

@SpringBootTest
class ContactAppApplicationTests {

	@Autowired
	ContactService contactService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void getContacts()
	{
		List<Contact> contacts = this.contactService.getContacts();
		for(Contact contact : contacts)
		{
			System.out.println(contact.getFirstName() + StringUtils.SPACE + contact.getLastName());
		}
	}
	
	@Test
	public void getContact()
	{
		Contact contact = this.contactService.getContacts(0);
		System.out.println(contact.getFirstName() + StringUtils.SPACE + contact.getLastName());
	}
	
	@Test
	public void deleteContact()
	{
		this.contactService.deleteContact(1);
	}
	
	@Test
	public void updateContact()
	{
		Contact contact = new Contact();
		contact.setId(1);
		contact.setLastName("Kim");
		contact.setFirstName("Pat");
		contact.setBirthDate(null);
		contact.setEmail("pattermite@gmail.com");
		contact.setPhone("410-645-0853");
		contact.setAddress("14 Sprindrift Cir");
		contact.setCity("Parkville");
		contact.setStateID(26);
		contact.setZipCode("21234");
		//contact.setProfilePic(null);
		contact.setSalary(BigDecimal.valueOf(130000));
		contact.setTimeOfBirth(null);
		this.contactService.updateContact(contact);
	}
	
	@Test
	public void createContact()
	{
		Contact contact = new Contact();
		contact.setLastName("Kim");
		contact.setFirstName("Pat");
		contact.setBirthDate(null);
		contact.setEmail("pattermite@gmail.com");
		contact.setPhone("410-645-0853");
		contact.setAddress("14 Sprindrift Cir");
		contact.setCity("Parkville");
		contact.setStateID(26);
		contact.setZipCode("21234");
		//contact.setProfilePic(null);
		contact.setSalary(BigDecimal.valueOf(130000));
		contact.setTimeOfBirth(null);
		this.contactService.createContact(contact);
	}
	
	@Test
	public void getStateIDTable()
	{
		List<StateTable> stateIDList = this.contactService.getStateIDTable();
		for(StateTable state : stateIDList)
		{
			System.out.println(state.getStateID() + " " + state.getStateAbbreviation() + " " + state.getStateName());
		}
	}
}
