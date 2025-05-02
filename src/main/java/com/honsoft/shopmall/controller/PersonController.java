package com.honsoft.shopmall.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.honsoft.shopmall.entity.Person;
import com.honsoft.shopmall.repository.PersonRepository;
import com.honsoft.shopmall.util.PersonRowMapper;

@Controller
@RequestMapping("/persons")
public class PersonController {
	private static Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	private final JdbcTemplate jdbcTemplate;
	private final PersonRepository personRepository;
	
	public PersonController(JdbcTemplate jdbcTemplate,PersonRepository personRepository) {
		this.jdbcTemplate = jdbcTemplate;
		this.personRepository = personRepository;
	}

	@GetMapping
	public @ResponseBody List<Person> getAllPersons(){
		List<Person> list = personRepository.findAll();
		
		String sql = "select count(*) from persons";
		int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);
		logger.info("rowCount: "+rowCount);
		
		sql = "select count(*) from persons where name = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class,"홍길순");
		logger.info("count: "+count);
		
//		sql = "select * from persons where id = ?";
//		Person person = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class),1);
//		logger.info("person: "+person.getName());;
		
		
		sql= "select * from persons where id = ?";
		Person person = jdbcTemplate.queryForObject(sql, new RowMapper<Person>() {

			@Override
			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Person person = new Person();
				person.setId(rs.getLong("id"));
				person.setName(rs.getString("name"));
				person.setPassword(rs.getString("password"));
				return person;
			}
			
		},1);
		
		logger.info("person for rowmapper: "+person.toString());
		
		
		sql= "select * from persons where id = ?";
		
		Person newPerson = jdbcTemplate.queryForObject(sql,new PersonRowMapper(),1);
		
		logger.info("newPerson for PersonRowMapper: "+newPerson.getName()+","+newPerson.getCreatedDate());
		
		
		sql = "select * from persons";
		List<Map<String,Object>> personList = jdbcTemplate.queryForList(sql);
		
		
//		list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
		
		
		
		return list;
	}
}
