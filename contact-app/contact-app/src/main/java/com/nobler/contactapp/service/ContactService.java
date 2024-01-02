package com.nobler.contactapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.serial.SerialException;
import org.springframework.dao.DataAccessException;
import com.nobler.contactapp.model.Contact;
import com.nobler.contactapp.model.StateTable;

public interface ContactService {
	public List<Contact> getContacts();
	public Contact getContacts(long id);
	public boolean deleteContact(long id);
	public boolean updateContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException;
	public long createContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException;
	public List<StateTable> getStateIDTable();
}
