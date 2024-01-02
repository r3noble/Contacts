package com.nobler.contactapp.restcontrollers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nobler.contactapp.service.ContactService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobler.contactapp.helper.Constants;
import com.nobler.contactapp.model.Contact;
import com.nobler.contactapp.model.StateTable;

@CrossOrigin
@RestController
@RequestMapping(value = Constants.API_CONTEXT_PATH + "/contacts")
public class ContactWebService {
	@Autowired
	protected ContactService contactService;
	
	@GetMapping(value = "/test")
	public String test()
	{
		return "This is a test";
	}
	
	@GetMapping(value = "/getMultiple", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Contact>> get()
	{
		List<Contact> contactList = this.contactService.getContacts();
		
		return new ResponseEntity<List<Contact>>(contactList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getSingle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contact> getSingle(@PathVariable String id)
	{
		if(id.length() == 0)
			return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
		
		Contact contact = this.contactService.getContacts(Long.parseLong(id));
		
		if(contact == null)
		{
			return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
		}
		
		contact.setBlob(null);
		
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contact> delete(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException
	{
		JsonNode rootNode = new ObjectMapper().readTree(json);
		JsonNode idNode = rootNode.get("id");
		if (idNode != null && idNode.isNumber()) {
	        long id = idNode.asLong();

	        boolean elementDeleted = this.contactService.deleteContact(id);

	        if (elementDeleted) {
	            return new ResponseEntity<Contact>(HttpStatus.OK);
	        }
	    }

	    return new ResponseEntity<Contact>(HttpStatus.NOT_MODIFIED);
	}
	
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Contact> update(@RequestParam(value = "jsonBodyData") String json, @RequestParam(value = "file") MultipartFile file) 
			throws JsonParseException, JsonMappingException, IOException, SerialException, SQLException, DataAccessException
	{
		Contact contact = new ObjectMapper().readValue(json, Contact.class);
		
		MultipartFile multipartFile = file;
		
		byte[] bytes = multipartFile.getBytes();
		SerialBlob blob = new SerialBlob(bytes);
		contact.setProfilePic(blob);
		
		String fileName = multipartFile.getOriginalFilename();
		contact.setfilename(fileName);
		
		boolean contactUpdated = this.contactService.updateContact(contact);
		if(contactUpdated == true)
		{
			return new ResponseEntity<Contact>(contact, HttpStatus.OK);
		}
		
		return new ResponseEntity<Contact>(HttpStatus.NOT_MODIFIED);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contact> create(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException, SerialException, SQLException, DataAccessException
	{
		Contact contact = new ObjectMapper().readValue(json, Contact.class);
		long returnCode = this.contactService.createContact(contact);
		if(returnCode != 0)
		{
			return new ResponseEntity<Contact>(contact, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Contact>(HttpStatus.NOT_MODIFIED);
	}
	
	@GetMapping(value = "/getState", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<StateTable>> getState()
	{
		List<StateTable> stateTable = this.contactService.getStateIDTable();
		
		return new ResponseEntity<List<StateTable>>(stateTable, HttpStatus.OK);
	}
}
