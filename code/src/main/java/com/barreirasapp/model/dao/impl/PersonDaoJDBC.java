package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.PersonDao;
import com.barreirasapp.model.entities.Person;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PersonDaoJDBC implements PersonDao{

    private final Connection conn;

    public PersonDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(Person person) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                          INSERT INTO Person (name, email, birth_date, gender)
                          VALUES (?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            LocalDate birthDate = person.getBirthDate(); // Seu LocalDate
            java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

            st.setString(1, person.getName());
            st.setString(2, person.getEmail().value());
            st.setDate(3, sqlDate);
            st.setString(4, person.getGender().toString());

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    Integer id = rs.getInt("id");
                    person.setId(id);
                }
            } else {
                throw new DatabaseException("Unexpect error: No rows affected");
            }


        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void update(Person person) {
        PreparedStatement st = null;

        LocalDate birthDate = person.getBirthDate(); // Seu LocalDate
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE User
                          SET name = ?, email = ?, birth_date = ?, gender = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, person.getName());
            st.setString(2, person.getEmail().value());
            st.setDate(3, sqlDate);
            st.setString(4, person.getGender().toString());
            st.setInt(5, person.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM Person WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public Person findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, name, email, birth_date, gender
                            FROM Person
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiatePerson(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return null;
    }

    @Override
    public List<Person> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<Person> personList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, name, email, birth_date, gender
                            FROM Person
                      """
            );

            while (rs.next()) {
                personList.add(instantiatePerson(rs));
            }
            return personList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private Person instantiatePerson(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));

        Email email = new Email(rs.getString("email"));
        Gender gender = Gender.valueOf(rs.getString("gender"));
        LocalDate birth_date = LocalDate.parse(rs.getString("birth_date"));

        person.setEmail(email);
        person.setBirthDate(birth_date);
        person.setGender(gender);
        return person;
    }
}
