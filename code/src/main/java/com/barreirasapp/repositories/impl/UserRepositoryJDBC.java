package com.barreirasapp.repositories.impl;

import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.entities.Moderator;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.valueobjects.Email;
import com.barreirasapp.entities.enums.Gender;
import com.barreirasapp.entities.enums.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJDBC implements UserRepository {

    public UserRepositoryJDBC() {
    }


    @Override
    public Integer insert(User user) {
        String sql = """
                          INSERT INTO User (name, email, birth_date, gender, password, role)
                          VALUES (?, ?, ?, ?, ?, ?);
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            LocalDate birthDate = user.getBirthDate();
            java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);
            UserRole userRole = user.getRole() != null ? user.getRole() : UserRole.COMMON;

            st.setString(1, user.getName());
            st.setString(2, user.getEmail().value());
            st.setDate(3, sqlDate);
            st.setString(4, user.getGender().toString());
            st.setString(5, user.getPassword());
            st.setString(6, userRole.toString());

            return QueryExecutor.executeUpdateWithGeneratedKey(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(User User) {
        LocalDate birthDate = User.getBirthDate();
        java.sql.Date sqlDate = java.sql.Date.valueOf(birthDate);

        String sql = """
                          UPDATE User SET name = ?, birth_date = ?, gender = ?
                          WHERE id = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){

            st.setString(1, User.getName());
            st.setDate(2, sqlDate);
            st.setString(3, User.getGender().toString());
            st.setInt(4, User.getId());

            QueryExecutor.executeUpdate(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = """
                          DELETE FROM User WHERE id = ?
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, id);
            QueryExecutor.executeUpdate(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = """
                            SELECT id, name, email, birth_date, gender, password, role
                            FROM User
                            WHERE id = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(instantiateUser(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = """
                            SELECT id, name, email, birth_date, gender, password, role
                            FROM User
                            WHERE id = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            List<User> UserList = new ArrayList<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    UserList.add(instantiateUser(rs));
                }
            }

            return UserList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        String sql = """
                            SELECT id, name, email, birth_date, gender, password, role
                            FROM User
                            WHERE email = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){

            st.setString(1, email.value());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(instantiateUser(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Moderator> findModeratorById(Integer id) {
        String sql = """
                            SELECT id, name, email, birth_date, gender, password, cellphone, role
                            FROM Moderator
                            JOIN User on User.id = Moderator.user_fk
                            WHERE Moderator.user_fk = ?;
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.ofNullable(instantiateModerator(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return Optional.empty();
    }

    public User instantiateUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        Email email = new Email(rs.getString("email"));

        String genderRs = rs.getString("gender");

        UserRole userRole = UserRole.COMMON;
        Gender gender = Gender.OTHER;

        try {
            userRole = UserRole.valueOf(rs.getString("role"));
        } catch (Exception ignored) {}

        try {
            gender = Gender.valueOf(genderRs);
        } catch (Exception ignored) {}

        LocalDate birthDate = LocalDate.parse(rs.getString("birth_date"));

        return new User(id, name, email, birthDate, gender, password, userRole);
    }

    public Moderator instantiateModerator(ResultSet rs) throws SQLException {
        User user = instantiateUser(rs);
        String cellphone = rs.getString("cellphone");

        return new Moderator(
                user.getPassword(),
                user.getGender(),
                user.getBirthDate(),
                user.getEmail(), user.getName(),
                cellphone
        );
    }
}
