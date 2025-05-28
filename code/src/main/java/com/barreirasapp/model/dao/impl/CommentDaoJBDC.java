package com.barreirasapp.model.dao.impl;

import com.barreirasapp.infra.db.DatabaseConnection;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.model.dao.CommentDao;
import com.barreirasapp.model.dao.DaoFactory;
import com.barreirasapp.model.entities.BarrierScenario;
import com.barreirasapp.model.entities.Comment;
import com.barreirasapp.model.entities.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoJBDC implements CommentDao {
    private final Connection conn;

    public CommentDaoJBDC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Comment comment, BarrierScenario barrierScenario)  {
        PreparedStatement st = null;
        ResultSet rs;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    """
                          INSERT INTO Comment (user_pk, barrier_scenario_fk, content)
                          VALUES (?, ?, ?)
                      """, Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, comment.getAuthor().getId());
            st.setInt(2, barrierScenario.getId());
            st.setString(3, comment.getContent());

            int id = QueryExecutor.executeUpdateWithGeneratedKey(st, conn);
            comment.setId(id);

            conn.commit();

        } catch (SQLException e) {

            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public List<Comment> findByBarrierScenarioId(Integer barrierScenarioId) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Comment> commentList = new ArrayList<>();
        try {
            st = conn.prepareStatement(
                    """
                            SELECT content, id, user_pk, barrier_scenario_fk, content, created_at
                            FROM Comment
                            WHERE barrier_scenario_fk = ?
                      """
            );

            st.setInt(1, barrierScenarioId);
            rs = st.executeQuery();

            while (rs.next()) {
                commentList.add(instantiateComment(rs));
            }

            return commentList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeResultSet(rs);
            DatabaseConnection.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    """
                              DELETE FROM Comment
                              WHERE id = ?
                      """, Statement.RETURN_GENERATED_KEYS
            );
            st.setInt( 1 , id);
            st.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            DatabaseConnection.closeStatement(st);
        }
    }

    private Comment instantiateComment(ResultSet rs) throws SQLException {
        int id = Integer.parseInt(rs.getString("id"));
        Integer userId = Integer.valueOf(rs.getString("user_pk"));
        String content = rs.getString("content");
        LocalDateTime creationDate = rs.getTimestamp("created_at").toLocalDateTime();
        User user = DaoFactory.createUserDao().findById(userId);

        return new Comment(id, content, user, creationDate);
    }

}
