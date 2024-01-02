package com.nobler.contactapp.dao;

import java.util.ArrayList;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nobler.contactapp.model.Contact;
import com.nobler.contactapp.model.StateTable;

@Repository("ContactDao")

public class ContactDaoImpl implements ContactDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Contact> getContacts(long id) {
		List<Contact> contactList = new ArrayList<Contact>();
		
		String sqlString = 	"select " +
							"id,\n" +
							"last_name,\n" +
							"first_name,\n" +
							"birthdate,\n" +
							"email,\n" +
							"phone,\n" +
							"address,\n" +
							"city,\n" +
							"state_id,\n" +
							"zipcode,\n" +
							//"profilepic,\n" +
							"salary,\n" +
							"edited,\n" +
							"time_format(time_of_birth, '%H:%i') time_of_birth\n" +
							" from contacts";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		
		if(id > 0)
		{
			sqlString += " where id = :id";
		}
		
		List<Map<String, Object>> rows = this.namedParameterJdbcTemplate.queryForList(sqlString, paramMap);
		for(Map<String, Object> row : rows)
		{
			Contact contact = new Contact();
			
			contact.setId((long)row.get("id"));
			contact.setLastName((String)row.get("last_name"));
			contact.setFirstName((String)row.get("first_name"));
			contact.setBirthDate((Date)row.get("birthdate"));
			contact.setEmail((String)row.get("email"));
			contact.setPhone((String)row.get("phone"));
			contact.setAddress((String)row.get("address"));
			contact.setCity((String)row.get("city"));
			contact.setStateID((int)row.get("state_id"));
			contact.setZipCode((String)row.get("zipcode"));
			//contact.setProfilePic(row.get("profilepic"));
			contact.setSalary((BigDecimal)row.get("salary"));
			contact.setEdited((Date)row.get("edited"));
			contact.setTimeOfBirth((String)row.get("time_of_birth"));
			
			if(id > 0)
			{
				Contact blobRec = this.getBlobFile(id);
				contact.setBlob(blobRec.getBlob());
			}
			
			contactList.add(contact);
		}
		
		return contactList;
	}

	@Override
	public boolean deleteContact(long id) {
		String sqlString = 	"Delete from contacts where "
							+ "id = ?";
		Object[] params =
		{
			id
		};
		
		long elementsDeleted = this.jdbcTemplate.update(sqlString, params);
		if(elementsDeleted > 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean updateContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException {
		String sqlString = 	"update contacts set\n" +
							"last_name = ?,\n" +
							"first_name = ?,\n" +
							"birthdate = ?,\n" +
							"email = ?,\n" +
							"phone = ?,\n" +
							"address = ?,\n" +
							"city = ?,\n" +
							"state_id = ?,\n" +
							"zipcode = ?,\n" +
							"profilepic = ?,\n" +
							"salary = ?,\n" +
							"edited = current_timestamp(),\n" +
							"time_of_birth = ?\n" +
							" where id = ?";
		
		Object[] params =
		{
			contact.getLastName(),
			contact.getFirstName(),
			contact.getBirthDate(),
			contact.getEmail(),
			contact.getPhone(),
			contact.getAddress(),
			contact.getCity(),
			contact.getStateID(),
			contact.getZipCode(),
			contact.getProfilePic(),
			contact.getSalary(),
			contact.getTimeOfBirth(),
			contact.getId()
		};
		
		long contactUpdated = this.jdbcTemplate.update(sqlString, params);
		
		if(contactUpdated > 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public long createContact(Contact contact) throws IOException, SerialException, SQLException, DataAccessException {
		long newID = this.getNextID("contacts" , "id");
		
		String sqlString = 	"insert into contacts (\n" +
				"id,\n" +
				"last_name,\n" +
				"first_name,\n" +
				"birthdate,\n" +
				"email,\n" +
				"phone,\n" +
				"address,\n" +
				"city,\n" +
				"state_id,\n" +
				"zipcode,\n" +
				//"profilepic,\n" +
				"salary,\n" +
				"edited,\n" +
				"time_of_birth)\n" +
				" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp(), ?)";
		
		Object[] params =
			{
				newID,
				contact.getLastName(),
				contact.getFirstName(),
				contact.getBirthDate(),
				contact.getEmail(),
				contact.getPhone(),
				contact.getAddress(),
				contact.getCity(),
				contact.getStateID(),
				contact.getZipCode(),
				//contact.getProfilePic(),
				contact.getSalary(),
				contact.getTimeOfBirth(),
			};
		
		long contactInserted = this.jdbcTemplate.update(sqlString, params);
		if(contactInserted == 0)
		{
			return contactInserted;
		}
		
		return newID;
	}
	
	private long getNextID(String tableName, String pkName)
	{
		String sqlString = "select max(" + pkName + ") id from " + tableName;
		Long id = jdbcTemplate.queryForObject(sqlString, Long.class);
		long nextID = id == null ? 1 : id.longValue() + 1;
		
		return nextID;
	}

	@Override
	public List<StateTable> getStateIDTable() {
		List<StateTable> stateIDList = new ArrayList<StateTable>();
		String sqlString = "select id, state_abbr, state_name from state order by state_name";
		stateIDList.add(new StateTable());
		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sqlString);
		for(Map<String, Object> row : rows)
		{
			StateTable stateTable = new StateTable();
			stateTable.setStateID(Long.parseLong(row.get("id") + StringUtils.EMPTY));
			stateTable.setStateAbbreviation((String)row.get("state_abbr"));
			stateTable.setStateName((String)row.get("state_name"));
			
			stateIDList.add(stateTable);
		}
		
		return stateIDList;
	}
	
	private Contact getBlobFile(long id)
	{
		List<Contact> profilePicList = jdbcTemplate.query("select profilepic from contacts where id = ?",
				new Object[] {id},
				(resultSet, i) ->
		{
			return this.getBlob(resultSet);
		});
		
		if(profilePicList.size() == 1)
		{
			return profilePicList.get(0);
		}
		throw new RuntimeException("No item found for id: " + id);
	}
	
	private Contact getBlob(ResultSet resultSet) throws SQLException
	{
		Contact contact = new Contact();
		Blob blob = resultSet.getBlob("profilepic");
		contact.setBlob(blob);
		
		return contact;
	}
}
