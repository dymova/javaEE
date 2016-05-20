package ru.nsu.ccfit.dymova.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.entities.Tag;

import java.math.BigInteger;
import java.util.List;


public interface NodeRepository extends JpaRepository<Node, BigInteger> {

    List<Node> findByOriginalId(BigInteger originalId);

    Node findById(BigInteger id);

    @Query("select n from Node n join n.tags t where ABS(earth_distance(ll_to_earth(0, 0), ll_to_earth(n.lat, n.lon))-" +
            "earth_distance(ll_to_earth(0, 0), ll_to_earth(:lat, :lon))) < :dist" +
            " AND ABS(earth_distance(ll_to_earth(0, 90), ll_to_earth(n.lat, n.lon)) -" +
            "earth_distance(ll_to_earth(0, 90), ll_to_earth(:lat, :lon))) < :dist " +
            "AND t.k=:key " +
            "ORDER BY ABS(earth_distance(ll_to_earth(0, 90), ll_to_earth(n.lat, n.lon)))+" +
            "ABS(earth_distance(ll_to_earth(0, 0), ll_to_earth(n.lat, n.lon)))")
    List<Node> findNodeInDistance(@Param("lat") Double lat, @Param("lon") Double lon,
                                 @Param("dist") Double dist, @Param("key") String key);



}
