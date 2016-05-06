package ru.nsu.ccfit.dymova.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.entities.Tag;
import ru.nsu.ccfit.dymova.services.NodeDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class JdbcNodeDao implements NodeDao {
    private static final Logger log = LoggerFactory.getLogger(JdbcNodeDao.class);
    private static final String INSERT_NODE_REQUEST = "INSERT INTO nodes " +
            "(node_id,node_lat, node_lon, node_user, node_uid, " +
            "node_visible, node_version, node_changeset, node_timestamp) " +
            "VALUES (? , ?, ?, ? , ?, ?, ? , ?, ?)";
    private static final String INSERT_TAG_REQUEST = "INSERT INTO tags " +
            "(k, v, node_id) VALUES  (?, ?, ?)";
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcNodeDao(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Transactional
    @Override
    public void insertNodes(Node node) {
//        String request = "INSERT INTO nodes" +
//                "(node_id,node_lat, node_lon, node_user, node_uid, " +
//                "node_visible, node_version, node_changeset, node_timestamp) " +
//                "VALUES (:id , :lat, :lon, :user , :uid, " +
//                ":visible, :version, :changeset, :timestamp)";
//        SqlParameterSource namedParams = new BeanPropertySqlParameterSource(node);
//        jdbcOperations.update(request, namedParams, keyHolder, new String[] {"id"});
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_NODE_REQUEST, new String[] {"id"});
                ps.setObject(1, node.getNodeId(), Types.BIGINT);
                ps.setDouble(2, node.getLat());
                ps.setDouble(3, node.getLon());
                ps.setString(4, node.getUser());
                ps.setObject(5, node.getUid(), Types.BIGINT);
                if (node.isVisible() == null) {
                    ps.setNull(6, Types.BOOLEAN);
                } else {
                    ps.setBoolean(6, node.isVisible());
                }
                ps.setObject(7, node.getVersion(), Types.BIGINT);
                ps.setObject(8, node.getChangeset(), Types.BIGINT);
                ps.setTimestamp(9, node.getTimestamp());
                return ps;
            }, keyHolder);

            Number nodeId = keyHolder.getKey();
            List<Tag> tags = node.getTags();
            jdbcOperations.batchUpdate(INSERT_TAG_REQUEST, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                ps.setString(1, tag.getK());
                ps.setString(2, tag.getV());
                ps.setObject(3, nodeId, Types.BIGINT);
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }
}
