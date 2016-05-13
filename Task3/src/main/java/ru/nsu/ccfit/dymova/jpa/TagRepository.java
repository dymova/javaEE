package ru.nsu.ccfit.dymova.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.dymova.entities.Tag;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, BigInteger> {
//    Optional<Tag> findByNodeId(BigInteger nodeId);

//    void updateTag(Tag tag); //todo
}
