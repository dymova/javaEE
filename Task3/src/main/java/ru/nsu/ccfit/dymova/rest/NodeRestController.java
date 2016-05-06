package ru.nsu.ccfit.dymova.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.jpa.NodeRepository;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping(value = "/nodes")
public class NodeRestController {

    @Autowired
    private NodeRepository nodeRepository;

    @RequestMapping(value = "/{nodeId}", method = RequestMethod.GET)
    public List<Node> getNode(@PathVariable BigInteger nodeId){
        return nodeRepository.findByNodeId(nodeId);
    }
}
