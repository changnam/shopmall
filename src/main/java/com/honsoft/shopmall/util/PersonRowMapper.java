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

        // BaseEntity fields
        person.setCreatedBy(rs.getString("createdBy"));
        person.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
        person.setLastModifiedBy(rs.getString("lastModifiedBy"));
        person.setLastModifiedDate(rs.getTimestamp("lastModifiedDate").toLocalDateTime());
        
        Timestamp timestamp = rs.getTimestamp("createdAt");
        person.setCreatedAt(timestamp != null ? timestamp.toInstant() : null);
        
        timestamp = rs.getTimestamp("updatedAt");
        person.setUpdatedAt(rs.getTimestamp("updatedAt").toInstant());

        return person;
    }
}
