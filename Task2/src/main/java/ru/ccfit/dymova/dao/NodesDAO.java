package ru.ccfit.dymova.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;

import java.sql.*;
import java.util.Arrays;
import java.util.List;


public class NodesDAO implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(NodesDAO.class.getName());

    private static final String URL = "jdbc:postgresql://localhost:5433/osm";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1";
    private Connection connection;

    public NodesDAO() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Class.forName("org.postgresql.Driver");
    }

    public void addNodeByExecuteUpdate(List<Node> nodes) throws SQLException, ClassNotFoundException {
        try (Statement statement = connection.createStatement()) {
            long before = System.currentTimeMillis();
            int count = 0;
            String parameters;
            for (Node node : nodes) {
                parameters = String.join(", ",
                        String.valueOf(node.getId()),
                        String.valueOf(node.getLat()),
                        String.valueOf(node.getLon()),
                        "'" + node.getUser() + "'",
                        String.valueOf(node.getUid()),
                        String.valueOf(node.isVisible()),
                        String.valueOf(node.getVersion()),
                        String.valueOf(node.getChangeset()),
                        "'" + node.getTimestamp() + "'");

                String nodeRequest = "INSERT INTO nodes " +
                        "(id, node_lat, node_lon, node_user, node_uid, " +
                        "node_visible, node_version, node_changeset, node_timestamp) " +
                        "VALUES (" + parameters + ")";
                count += statement.executeUpdate(nodeRequest);

                for (Tag tag : node.getTag()) {
                    parameters = String.join(", ",
                            "'" + tag.getK() + "'",
                            "'" + tag.getV() + "'",
                            String.valueOf(node.getId()));
                    String tagRequest = "INSERT INTO tags " +
                            "(k, v, node_id) VALUES  (" + parameters + ")";
                    count += statement.executeUpdate(tagRequest);
                }
            }
            long after = System.currentTimeMillis();
            log.info("Insert " + count + " records by executeUpdate");
            log.info("Time: " + (after - before) + " ms");
        }

    }

    public void addNodeByPreparedStatement(List<Node> nodes) throws SQLException, ClassNotFoundException {
        String nodeRequest = "INSERT INTO nodes " +
                "(id,node_lat, node_lon, node_user, node_uid, " +
                "node_visible, node_version, node_changeset, node_timestamp) " +
                "VALUES (? , ?, ?, ? , ?, ?, ? , ?, ?)";
        String tagRequest = "INSERT INTO tags " +
                "(k, v, node_id) VALUES  (?, ?, ?)";

        try (PreparedStatement nodeStatement = connection.prepareStatement(nodeRequest);
             PreparedStatement tagStatement = connection.prepareStatement(tagRequest)) {
            long before = System.currentTimeMillis();
            int count = 0;
            for (Node node : nodes) {
                nodeStatement.setObject(1, node.getId(), Types.BIGINT);
                nodeStatement.setDouble(2, node.getLat());
                nodeStatement.setDouble(3, node.getLon());
                nodeStatement.setString(4, node.getUser());
                nodeStatement.setObject(5, node.getUid(), Types.BIGINT);
                if (node.isVisible() == null) {
                    nodeStatement.setNull(6, Types.BOOLEAN);
                } else {
                    nodeStatement.setBoolean(6, node.isVisible());
                }
                nodeStatement.setObject(7, node.getVersion(), Types.BIGINT);
                nodeStatement.setObject(8, node.getChangeset(), Types.BIGINT);


                nodeStatement.setTimestamp(9, new Timestamp(node.getTimestamp().toGregorianCalendar().getTimeInMillis()));


                count += nodeStatement.executeUpdate();

                for (Tag tag : node.getTag()) {
                    tagStatement.setString(1, tag.getK());
                    tagStatement.setString(2, tag.getV());
                    tagStatement.setObject(3, node.getId(), Types.BIGINT);
                    count += tagStatement.executeUpdate();
                }
            }
            long after = System.currentTimeMillis();
            log.info("Insert " + count + " records by PreparedStatement");
            log.info("Time: " + (after - before) + " ms");
        }
    }


    public void addNodeByBatch(List<Node> nodes) throws SQLException, ClassNotFoundException {
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement()) {
            long before = System.currentTimeMillis();
            for (Node node : nodes) {
                String parameters = String.join(", ",
                        String.valueOf(node.getId()),
                        String.valueOf(node.getLat()),
                        String.valueOf(node.getLon()),
                        "'" + node.getUser() + "'",
                        String.valueOf(node.getUid()),
                        String.valueOf(node.isVisible()),
                        String.valueOf(node.getVersion()),
                        String.valueOf(node.getChangeset()),
                        "'" + node.getTimestamp() + "'");
                String nodeRequest = "INSERT INTO nodes " +
                        "(id, node_lat, node_lon, node_user, node_uid, " +
                        "node_visible, node_version, node_changeset, node_timestamp) " +
                        "VALUES (" + parameters + ")";

                statement.addBatch(nodeRequest);

                for (Tag tag : node.getTag()) {
                    parameters = String.join(", ",
                            "'" + tag.getK() + "'",
                            "'" + tag.getV() + "'",
                            String.valueOf(node.getId()));
                    String tagRequest = "INSERT INTO tags " +
                            "(k, v, node_id) VALUES  (" + parameters + ")";
                    statement.addBatch(tagRequest);
                }

            }
            int[] results = statement.executeBatch();
            connection.commit();
            long after = System.currentTimeMillis();
            log.info("Insert " + Arrays.stream(results).count() + " records by Batch");
            log.info("Time: " + (after - before) + " ms");

            connection.setAutoCommit(true);
        }

    }

    @Override
    public void close() throws Exception {
        connection.close();
    }


}
