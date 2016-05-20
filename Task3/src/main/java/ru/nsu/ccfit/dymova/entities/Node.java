package ru.nsu.ccfit.dymova.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Entity
//@NamedNativeQueries({
//        @NamedNativeQuery(name = "earthDistance",
//                query = "SELECT earth_distance(ll_to_earth(?1, :?2), ll_to_earth(?3, ?4))")
//})
@Table(name = "nodes")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "node_id")
    private BigInteger originalId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "node", fetch = FetchType.EAGER)
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

    @Column(name = "node_timestamp", columnDefinition = "timestamp with time zone")
    private Timestamp timestamp;

    public Node(List<Tag> tags, BigInteger originalId, Double lat, Double lon, String user, BigInteger uid, Boolean visible,
                BigInteger version, BigInteger changeset, Timestamp timestamp) {
        this.tags = tags;
        this.originalId = originalId;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
        this.uid = uid;
        this.visible = visible;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;

        tags.forEach(tag -> tag.setNode(this));
    }

    public Node() {
    }

    public BigInteger getId() {
        return id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public BigInteger getOriginalId() {
        return originalId;
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

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setOriginalId(BigInteger originalId) {
        this.originalId = originalId;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
