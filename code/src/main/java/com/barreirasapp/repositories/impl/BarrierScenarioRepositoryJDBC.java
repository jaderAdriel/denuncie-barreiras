package com.barreirasapp.repositories.impl;

import com.barreirasapp.repositories.*;
import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Comment;
import com.barreirasapp.entities.Law;
import com.barreirasapp.entities.User;
import com.barreirasapp.entities.enums.BarrierType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BarrierScenarioRepositoryJDBC implements BarrierScenarioRepository {

    private final CommentRepository commentRepository;
    private final LawBarrierScenarioAssociationRepository associationLawDao;
    private final BarrierScenarioLikeRepository barrierScenarioLikeRepository;
    private final UserRepository userRepository;

    public BarrierScenarioRepositoryJDBC(CommentRepository commentRepository, LawBarrierScenarioAssociationRepository associationLawDao, BarrierScenarioLikeRepository barrierScenarioLikeRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.associationLawDao = associationLawDao;
        this.barrierScenarioLikeRepository = barrierScenarioLikeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Integer insert(BarrierScenario scenario) {

        String sql = """
                         INSERT INTO BarrierScenario (type, author_fk, content, title, image_file_name)
                         VALUES (?, ?, ?, ?, ?);
                     """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            ){

            conn.setAutoCommit(false);
            String barrierType = (scenario.getBarrierType() == null) ? null : scenario.getBarrierType().toString();


            st.setString(1, barrierType);
            st.setInt(2, scenario.getAuthor().getId());
            st.setString (3, scenario.getContent());
            st.setString(4, scenario.getTitle());
            QueryExecutor.setStringOrNull(st, 5, scenario.getImageCoverPath());

            int id = QueryExecutor.executeUpdateWithGeneratedKey(st, conn);

            scenario.setId(id);

            for (Law law : scenario.getAssociatedLaws()) {
                associationLawDao.associate(law, scenario);
            }

            conn.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(BarrierScenario scenario) {
        String sql = """
                        UPDATE BarrierScenario
                        SET type = ?, content = ?, title = ?, image_file_name = ?
                        WHERE id = ?;
                    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS )
            ){

            conn.setAutoCommit(false);

            String barrierType = (scenario.getBarrierType() == null) ? null : scenario.getBarrierType().toString();

            st.setString(1, barrierType);
            st.setString(2, scenario.getContent());
            st.setString(3, scenario.getTitle());
            QueryExecutor.setStringOrNull(st, 4, scenario.getImageCoverPath());
            st.setInt(5, scenario.getId());

            this.persistAssociatedLaws(scenario);

            st.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM BarrierScenario WHERE id = ?";

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
            ){

            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<BarrierScenario> findById(Integer id) {

        String sql = """
                        SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                        FROM BarrierScenario
                        WHERE id = ?;
                    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql))
        {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(instantiateScenario(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<BarrierScenario> findAllByAuthorId(Integer id) {
        String sql = """
                        SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                        FROM BarrierScenario
                        WHERE author_fk = ?;
                    """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
            ){

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                List<BarrierScenario> scenarioList = new ArrayList<>();

                while (rs.next()) {
                    scenarioList.add(instantiateScenario(rs));
                }

                return scenarioList;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<BarrierScenario> findAll() {
        String sql = """
                        SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                        FROM BarrierScenario;
                    """;

        try ( Connection conn = DataSource.getConnection();
              PreparedStatement st = conn.prepareStatement(sql)
            ){

            try (ResultSet rs = st.executeQuery()) {
                List<BarrierScenario> scenarioList = new ArrayList<>();

                while (rs.next()) {
                    scenarioList.add(instantiateScenario(rs));
                }

                return scenarioList;
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void persistComments(BarrierScenario barrierScenario) {
        commentRepository.persistComments(barrierScenario);
    }

    @Override
    public void persistAssociatedLaws(BarrierScenario barrierScenario) {
        associationLawDao.persistAssociatedLaws(barrierScenario);
    }

    @Override
    public void addLiker(User user, BarrierScenario barrierScenario) {
        this.barrierScenarioLikeRepository.insert(user, barrierScenario);
    }

    @Override
    public void removeLiker(User user, BarrierScenario barrierScenario) {
        this.barrierScenarioLikeRepository.deleteById(user, barrierScenario);
    }


    private BarrierScenario instantiateScenario(ResultSet rs) throws SQLException {
        int authorId = rs.getInt("author_fk");

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new SQLException("Integridade em barrierScenario.user comprometida"));

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();
        String scenarioContent = rs.getString("content");
        String scenarioTitle = rs.getString("title");
        int scenarioId = rs.getInt("id");

        BarrierType barrierType;

        try {
            barrierType = BarrierType.valueOf(rs.getString("type"));
        } catch (Exception ignored){
            barrierType = BarrierType.OTHER;
        }

        Set<Law> associatedLaws = associationLawDao.findByBarrierScenarioId(scenarioId);
        Set<Comment> comments = commentRepository.findByBarrierScenarioId(scenarioId);
        Set<User> likes = barrierScenarioLikeRepository.findByBarrierScenarioId(scenarioId);

        BarrierScenario barrierScenario = new BarrierScenario(
                scenarioId,
                barrierType,
                author,
                scenarioContent,
                scenarioTitle,
                creationDate,
                associatedLaws,
                comments,
                likes
        );

        barrierScenario.setImageCoverPath(rs.getString("image_file_name"));

        return barrierScenario;
    }

}
