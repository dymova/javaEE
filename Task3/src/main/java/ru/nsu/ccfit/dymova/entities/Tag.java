package ru.nsu.ccfit.dymova.entities;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "tag_k")
    private String k;

    @Column(name = "tag_v")
    private String v;

    @Column(name = "node_id")
    private BigInteger nodeId;

    public Tag(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public Tag() {
    }

    public BigInteger getNodeId() {
        return nodeId;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
