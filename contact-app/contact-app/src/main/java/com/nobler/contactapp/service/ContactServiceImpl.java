package com.nobler.contactapp.service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.nobler.contactapp.dao.ContactDao;
import com.nobler.contactapp.model.Contact;
import com.nobler.contactapp.model.StateTable;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;
	
	@Override
	public List<Contact> getContacts() {
		return this.contactDao.getContacts(0);
	}
	
	@Override
	public Contact getContacts(long id) {
		
		List<Contact> contactList = this.contactDao.getContacts(id);
		if (contactList.size() == 0)
			return null;
		
		return contactList.get(0);
	}

	@Override
	public boolean deleteContact(long id) {
		return this.contactDao.deleteContact(id);
	}

	@Override
	public boolean updateContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException {
		return this.contactDao.updateContact(contact);
	}

	@Override
	public long createContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException {
		return this.contactDao.createContact(contact);
	}

	@Override
	public List<StateTable> getStateIDTable() {
		return this.contactDao.getStateIDTable();
	}
}
