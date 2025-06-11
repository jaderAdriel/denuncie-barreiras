package com.barreirasapp.repositories.impl;

import com.barreirasapp.entities.Entity;
import com.barreirasapp.entities.enums.EntityType;
import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.EntityRepository;
import com.barreirasapp.repositories.UserRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityRepositoryJDBC implements EntityRepository {
    private final UserRepository userRepository;

    public EntityRepositoryJDBC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Integer insert(Entity entity) {
        String sql = """
                          INSERT INTO Entity (cnpj, name, phone, create_at, type, city, street, state, postal_code)
                          VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                      """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            st.setString(1, entity.getCnpj());
            st.setString(2,  entity.getName());
            st.setString(3, entity.getPhone());
            st.setString(4, entity.getCreateAt().toString());
            st.setString(5, entity.getType().toString());
            st.setString(6, entity.getAddressCity());
            st.setString(7, entity.getAddressStreet());
            st.setString(8, entity.getAddressState());
            st.setString(9, entity.getAddressPostalCode());

            return QueryExecutor.executeUpdateWithGeneratedKey(st, conn);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Optional<Entity> findById(Integer id) {
        return Optional.empty();
    }

    public void deleteByCnpj(String cnpj) {
        String sql = """
                        DELETE FROM Entity WHERE cnpj = ?
                    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setString(1, cnpj);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Optional<Entity> findByCnpj(String cnpj) {
        String sql = """
                        SELECT Entity.*,
                        FROM Entity
                         WHERE cnpj = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setString(1, cnpj);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateReport(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    public Optional<Entity> findByType(EntityType type) {
        String sql = """
                        SELECT Entity.*,
                        FROM Entity
                         WHERE type = ?;
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            st.setString(1, type.toString());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateReport(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Entity> findAll() {
        return getEntities(" ORDER BY cnpj ASC");
    }

    private List<Entity> getEntities(String condition) {
        String sql = """
                        SELECT * FROM Entity
                     """;

        if (!condition.isEmpty())
            sql += condition;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ){
            try (ResultSet rs = st.executeQuery()) {
                List<Entity> entityList = new ArrayList<>();
                while (rs.next()) {
                    entityList.add(instantiateReport(rs));
                }
                return entityList;
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Entity instantiateReport(ResultSet rs) throws SQLException {
        Entity entity = new Entity();

        entity.setCnpj(rs.getString("cnpj"));
        entity.setName(rs.getString("name"));
        entity.setPhone(rs.getString("phone"));

        LocalDateTime creationDate = rs.getTimestamp("create_at").toLocalDateTime();
        entity.setCreateAt(creationDate);

        entity.setType(EntityType.valueOf(rs.getString("type")));
        entity.setAddress(rs.getString("street"),
                rs.getString( "city"),
                rs.getString("state"),
                rs.getString("postal_code")
        );

        return entity;
    }
}

