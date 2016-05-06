package ru.nsu.ccfit.dymova.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "nodes")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "node_id")
    private BigInteger nodeId;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "node_id")
    private List<Tag> tags;

    @Column(name = "node_lat")
    private Double lat;

    @Column(name = "node_lon")
    private Double lon;

    @Column(name = "node_user")
    private String user;

    @Column(name = "node_uid")
    private BigInteger uid;

    @Column(name = "node_visible")
    private Boolean visible;

    @Column(name = "node_version")
    private BigInteger version;

    @Column(name = "node_changeset")
    private BigInteger changeset;

//    @Column(name = "node_timestamp")
    @Column(name = "node_timestamp", columnDefinition = "timestamp with time zone")
    private Timestamp timestamp;

    public Node(List<Tag> tags, BigInteger nodeId, Double lat, Double lon, String user, BigInteger uid, Boolean visible,
                BigInteger version, BigInteger changeset, Timestamp timestamp) {
        this.tags = tags;
        this.nodeId = nodeId;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
        this.uid = uid;
        this.visible = visible;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
    }

    public Node() {
    }

    public List<Tag> getTags() {
        return tags;
    }

    public BigInteger getNodeId() {
        return nodeId;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getUser() {
        return user;
    }

    public BigInteger getUid() {
        return uid;
    }

    public Boolean isVisible() {
        return visible;
    }


    public BigInteger getVersion() {
        return version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
