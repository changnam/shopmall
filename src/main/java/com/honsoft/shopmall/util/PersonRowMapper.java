package com.honsoft.shopmall.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import com.honsoft.shopmall.entity.Person;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getLong("id"));
        person.setName(rs.getString("name"));
        person.setPassword(rs.getString("password"));
        person.setAge(rs.getInt("age"));
        person.setEmail(rs.getString("email"));

        // BaseEntity fields
        person.setCreatedBy(rs.getString("createdBy"));
        person.setCreatedDate(rs.getTimestamp("createdDate") != null ? rs.getTimestamp("createdDate").toLocalDateTime() : null);
        person.setLastModifiedBy(rs.getString("lastModifiedBy"));
        person.setLastModifiedDate(rs.getTimestamp("lastModifiedDate") != null ? rs.getTimestamp("lastModifiedDate").toLocalDateTime() : null);
        
        Timestamp timestamp = rs.getTimestamp("createdAt");
        person.setCreatedAt(timestamp != null ? timestamp.toInstant() : null);
        
        timestamp = rs.getTimestamp("updatedAt");
        person.setUpdatedAt(timestamp != null ? timestamp.toInstant() : null);

        return person;
    }
}
