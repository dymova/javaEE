package ru.nsu.ccfit.dymova.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.dymova.entities.Node;

import java.math.BigInteger;
import java.util.List;


public interface NodeRepository extends JpaRepository<Node, BigInteger> {

    List<Node> findByOriginalId(BigInteger originalId);

    Node findById(BigInteger id);

}
