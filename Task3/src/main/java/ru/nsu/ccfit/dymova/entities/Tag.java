package ru.nsu.ccfit.dymova.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne()
    @JsonIgnore
    private Node node;

    public Tag(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public Tag() {
    }

    public Node getNode() {
        return node;
    }

    public String getK() {
        return k;
    }

    public String getV() {
        return v;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
