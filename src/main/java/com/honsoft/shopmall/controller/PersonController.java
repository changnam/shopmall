package com.honsoft.shopmall.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
//
//	@GetMapping
//	public @ResponseBody List<Person> getAllPersons(){
//		List<Person> list = personRepository.findAll();
//		
//		String sql = "select count(*) from persons";
//		int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);
//		logger.info("rowCount: "+rowCount);
//		
//		sql = "select count(*) from persons where name = ?";
//		int count = jdbcTemplate.queryForObject(sql, Integer.class,"홍길순");
//		logger.info("count: "+count);
//		
////		sql = "select * from persons where id = ?";
////		Person person = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class),1);
////		logger.info("person: "+person.getName());;
//		
//		
//		sql= "select * from persons where id = ?";
//		Person person = jdbcTemplate.queryForObject(sql, new RowMapper<Person>() {
//
//			@Override
//			public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
//				// TODO Auto-generated method stub
//				Person person = new Person();
//				person.setId(rs.getLong("id"));
//				person.setName(rs.getString("name"));
//				person.setPassword(rs.getString("password"));
//				return person;
//			}
//			
//		},1);
//		
//		logger.info("person for rowmapper: "+person.toString());
//		
//		
//		sql= "select * from persons where id = ?";
//		
//		Person newPerson = jdbcTemplate.queryForObject(sql,new PersonRowMapper(),1);
//		
//		logger.info("newPerson for PersonRowMapper: "+newPerson.getName()+","+newPerson.getCreatedDate()+newPerson.toString());
//		
//		
//		sql = "select * from persons";
//		List<Map<String,Object>> personList = jdbcTemplate.queryForList(sql);
//		
//		
////		list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
//		
//		sql = "insert into persons (name,password,createdDate,createdAt) values(?,?,?,?)";
//		
//		int result = jdbcTemplate.update(sql,"이순신","1234",LocalDateTime.now(),LocalDateTime.now());
//		
//		logger.info("result : "+result);
//		
//		sql = "update persons set name = ? where name = ?";
//		
//		result = jdbcTemplate.update(sql,"김복동","이순신");
//		
//		logger.info("result : "+result);
//		
//		sql = "delete from persons where name = ?";
//		
//		result = jdbcTemplate.update(sql,"김복동");
//		
//		logger.info("result : "+result);
//		
//		list = personRepository.findAll();
//		
//		return list;
//	}
//	
	@GetMapping
	public String requestMethod(Model model) {
		 String sql = "SELECT * FROM persons";
		 List<Person>  personList= jdbcTemplate.query(sql,
					new PersonRowMapper());		
	
		model.addAttribute("personList", personList);
		return "persons/personList";
	}	
	
	@GetMapping("/new")
	public String newMethod(Model model) {
		Person person = new Person();
		model.addAttribute("person", person);
		
		return "persons/personAdd";
	}
	
	@PostMapping("/insert")
	public String insertMethod(@ModelAttribute("person") Person person) {
		
		String sql = "INSERT INTO persons (name, age, email, createdDate,createdAt) VALUES (?, ?, ?,?,?)";
		//int result = jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail()); 		
		Object[] params = {person.getName(), person.getAge(), person.getEmail(), LocalDateTime.now(),LocalDateTime.now()};
		int result = jdbcTemplate.update(sql, params);
		
		return "redirect:/persons";
	}
	
	@GetMapping("/edit/{id}")
	public String editMethod(@PathVariable(name = "id") int id, Model model) {
				
		 String sql = "SELECT * FROM persons WHERE id =?";		
		 Person person = jdbcTemplate.queryForObject(sql,new PersonRowMapper(), id);
		 model.addAttribute("person", person);
		
		return "persons/personEdit";
	}	
	
	@PostMapping("/update")
	public String updateMethod(@ModelAttribute("person") Person person) {
		
		
		String sql = "UPDATE persons SET name=?, age=?, email=? WHERE id=?";
		int result = jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getEmail(), person.getId()); 		
				
		return "redirect:/persons";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(@PathVariable(name = "id") int id) {
		String sql = "DELETE FROM persons WHERE id=?";		
		int result = jdbcTemplate.update(sql, id);
		
		return "redirect:/persons";
	}

	
}
