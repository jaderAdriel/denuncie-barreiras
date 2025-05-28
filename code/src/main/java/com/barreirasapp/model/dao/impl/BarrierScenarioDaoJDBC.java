package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.*;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;
import com.barreirasapp.model.entities.Law;
import com.barreirasapp.model.entities.User;
import com.barreirasapp.model.enums.BarrierType;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BarrierScenarioDaoJDBC implements BarrierScenarioDao {

    private final Connection conn;
    private final CommentDao commentDao;
    private final LawBarrierScenarioAssociationDao associationLawDao;
    private final BarrierScenarioLikeDao barrierScenarioLikeDao;
    private final UserDao userDao;

    public BarrierScenarioDaoJDBC(Connection conn, CommentDao dao, LawBarrierScenarioAssociationDao associationLawDao, BarrierScenarioLikeDao barrierScenarioLikeDao, UserDao userDao) {
        this.conn = conn;
        this.commentDao = dao;
        this.associationLawDao = associationLawDao;
        this.barrierScenarioLikeDao = barrierScenarioLikeDao;
        this.userDao = userDao;
    }

    @Override
    public void insert(BarrierScenario scenario) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    """
                              INSERT INTO BarrierScenario (type, author_fk, content, title, image_file_name)
                              VALUES (?, ?, ?, ?, ?);
                      """, Statement.RETURN_GENERATED_KEYS
            );

            String barrierType = (scenario.getBarrierType() == null) ? null : scenario.getBarrierType().toString();

            st.setString(1, barrierType);
            st.setInt(2, scenario.getAuthor().getId());
            st.setString (3, scenario.getContent());
            st.setString(4, scenario.getTitle());

            if (scenario.getImageCoverPath().isEmpty() || scenario.getImageCoverPath() == null)
                st.setNull(5, Types.VARCHAR);
            else
                st.setString(5, scenario.getImageCoverPath());

            int id = QueryExecutor.executeUpdateWithGeneratedKey(st, conn);

            scenario.setId(id);

            for (Law law : scenario.getAssociatedLaws()) {
                associationLawDao.associate(law, scenario);
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    public void insertComment(Comment comment, BarrierScenario barrierScenario) {
        commentDao.insert(comment, barrierScenario);
    }

    @Override
    public void update(BarrierScenario scenario) {
        PreparedStatement st = null;

        try {

            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    """
                          UPDATE BarrierScenario
                          SET type = ?, content = ?, title = ?, image_file_name = ?
                          WHERE id = ?;
                      """, Statement.RETURN_GENERATED_KEYS
            );

            String barrierType = (scenario.getBarrierType() == null) ? null : scenario.getBarrierType().toString();

            st.setString(1, barrierType);
            st.setString(2, scenario.getContent());
            st.setString(3, scenario.getTitle());
            st.setInt(5, scenario.getId());

            if (scenario.getImageCoverPath().isEmpty() || scenario.getImageCoverPath() == null)
                st.setNull(4, Types.VARCHAR);
            else
                st.setString(4, scenario.getImageCoverPath());

            st.executeUpdate();

            this.associationLawDao.update(scenario);

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DatabaseException(e.getMessage());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }


    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM BarrierScenario WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public BarrierScenario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                            FROM BarrierScenario
                            WHERE id = ?;
                      """
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateScenario(rs);
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
    public List<BarrierScenario> findAllByAuthorId(Integer id) {
        Statement st = null;
        ResultSet rs = null;
        List<BarrierScenario> scenarioList = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                            FROM BarrierScenario
                            WHERE author_fk = ?;
                      """
            );

            while (rs.next()) {
                scenarioList.add(instantiateScenario(rs));
            }
            return scenarioList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    public List<BarrierScenario> findAll() {
        Statement st = null;
        ResultSet rs = null;
        List<BarrierScenario> moderatorList = new ArrayList<>();
        try {
            st = conn.createStatement();
            rs = st.executeQuery(
                    """
                            SELECT id, author_fk, type, content, title, creation_date, likes, image_file_name
                            FROM BarrierScenario;
                      """
            );

            while (rs.next()) {
                moderatorList.add(instantiateScenario(rs));
            }
            return moderatorList;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    private BarrierScenario instantiateScenario(ResultSet rs) throws SQLException {
        int authorId = rs.getInt("author_fk");
        User author = userDao.findById(authorId);

        LocalDateTime creationDate = rs.getTimestamp("creation_date").toLocalDateTime();

        int scenarioId = rs.getInt("id");

        BarrierType barrierType;

        try {
            barrierType = BarrierType.valueOf(rs.getString("type"));
        } catch (Exception ignored){
            barrierType = BarrierType.OTHER;
        };

        String scenarioContent = rs.getString("content");
        String scenarioTitle = rs.getString("title");

        List<Law> associatedLaws = associationLawDao.findByBarrierScenarioId(scenarioId);

        List<Comment> comments = commentDao.findByBarrierScenarioId(scenarioId);

        Set<User> likes = barrierScenarioLikeDao.findByBarrierScenarioId(scenarioId);

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
