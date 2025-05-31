package com.barreirasapp.repositories.impl;

import com.barreirasapp.repositories.UserRepository;
import com.barreirasapp.infra.db.DataSource;
import com.barreirasapp.infra.db.QueryExecutor;
import com.barreirasapp.infra.exceptions.DatabaseException;
import com.barreirasapp.repositories.CommentRepository;
import com.barreirasapp.entities.BarrierScenario;
import com.barreirasapp.entities.Comment;
import com.barreirasapp.entities.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CommentRepositoryJBDC implements CommentRepository {
    private final UserRepository userRepository;

    public CommentRepositoryJBDC(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void insert(Comment comment, BarrierScenario barrierScenario)  {
        String sql = """
                          INSERT INTO Comment (user_pk, barrier_scenario_fk, content)
                          VALUES (?, ?, ?)
                      """;

        try (Connection conn = DataSource.getConnection();
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setInt(1, comment.getAuthor().getId());
            st.setInt(2, barrierScenario.getId());
            st.setString(3, comment.getContent());

            QueryExecutor.executeUpdateWithGeneratedKey(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Set<Comment> findByBarrierScenarioId(Integer barrierScenarioId) {
        String sql = """
                        SELECT content, id, user_pk, barrier_scenario_fk, content, created_at
                        FROM Comment
                        WHERE barrier_scenario_fk = ?
                      """;
        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {

            st.setInt(1, barrierScenarioId);
            Set<Comment> commentList = new HashSet<>();

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    commentList.add(instantiateComment(rs));
                }
            }

            return commentList;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = """
                          DELETE FROM Comment WHERE id = ?
                      """;

        try (Connection conn = DataSource.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)
        ) {
            st.setInt( 1 , id);

            QueryExecutor.executeUpdate(st, conn);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void persistComments(BarrierScenario barrierScenario) {
        Set<Comment> associatedComments = this.findByBarrierScenarioId(barrierScenario.getId());

        Set<Comment> commentsToUpdate = barrierScenario.getComments();

        for (Comment comment : commentsToUpdate) {
            if (associatedComments.contains(comment)) continue;
            this.insert(comment, barrierScenario);
        }

        for (Comment comment : associatedComments) {
            if (commentsToUpdate.contains(comment)) continue;
            deleteById(comment.getId());
        }
    }

    private Comment instantiateComment(ResultSet rs) throws SQLException {
        int id = Integer.parseInt(rs.getString("id"));
        Integer userId = Integer.valueOf(rs.getString("user_pk"));
        String content = rs.getString("content");
        LocalDateTime creationDate = rs.getTimestamp("created_at").toLocalDateTime();

        User author = userRepository.findById(userId)
                .orElseThrow(SQLException::new);

        return new Comment(id, content, author, creationDate);
    }

}
