package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.UserDao;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.entities.valueobjects.Email;
import com.barreirasapp.model.enums.Gender;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJDBC implements UserDao {

    private final Connection conn;

    public UserDaoJDBC(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void insert(User User) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    """
                          INSERT INTO User (name, email, birth_date, gender, password)
                          VALUES (?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            LocalDate birthDate = User.getBirthDate(); // Seu LocalDate
            java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

            st.setString(1, User.getName());
            st.setString(2, User.getEmail().value());
            st.setDate(3, sqlDate);
            st.setString(4, User.getGender().toString());
            st.setString(5, User.getPassword());

            int rowsAffected =  st.executeUpdate();

            if (rowsAffected > 0) {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    User.setId(id);
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
    public void update(User User) {
        PreparedStatement st = null;

        LocalDate birthDate = User.getBirthDate(); // Seu LocalDate
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

        try {
            st = conn.prepareStatement(
                    """
                          UPDATE User
                          SET name = ?, birth_date = ?, gender = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, User.getName());
            st.setDate(2, sqlDate);
            st.setString(3, User.getGender().toString());
            st.setInt(4, User.getId());
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
            st = conn.prepareStatement("DELETE FROM User WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public User findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, name, email, birth_date, gender, password
                            FROM User
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateUser(rs);
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
    public List<User> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<User> UserList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, name, email, birth_date, gender, password
                            FROM User
                      """
            );

            while (rs.next()) {
                UserList.add(instantiateUser(rs));
            }
            return UserList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private User instantiateUser(ResultSet rs) throws SQLException {
        User User = new User();
        User.setId(rs.getInt("id"));
        User.setName(rs.getString("name"));
        User.setPassword(rs.getString("password"));

        Email email = new Email(rs.getString("email"));
        Gender gender = Gender.valueOf(rs.getString("gender"));
        LocalDate birth_date = LocalDate.parse(rs.getString("birth_date"));

        User.setEmail(email);
        User.setBirthDate(birth_date);
        User.setGender(gender);
        return User;
    }

    @Override
    public Optional<User> getUserByEmail(Email email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, name, email, birth_date, gender, password
                            FROM User
                            WHERE email = ?;
                      """
            );

            st.setString(1, email.value());
            rs = st.executeQuery();

            if (rs.next()) {
                return Optional.of(instantiateUser(rs)) ;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }

        return Optional.empty();
    }
}
