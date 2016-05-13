package ru.nsu.ccfit.dymova.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.jpa.NodeRepository;

import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping("/nodes")
public class NodeRestController {

    @Autowired
    private NodeRepository nodeRepository;

    @RequestMapping(value = "/id/{nodeId}", method = RequestMethod.GET)
    public Node readNodeById(@PathVariable BigInteger nodeId){
        return nodeRepository.findById(nodeId);
    }

    @RequestMapping(value = "/original_id/{nodeId}",method = RequestMethod.GET)
    public List<Node> readNodeByOriginalId(@PathVariable("nodeId") BigInteger nodeId){
        return nodeRepository.findByOriginalId(nodeId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Node> readNodes(){
        return nodeRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{nodeId}")
    public void deleteNode(@PathVariable("nodeId") BigInteger nodeId){
        nodeRepository.delete(nodeId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createNode(@RequestBody Node input){
        Node result = nodeRepository.save(new Node(input.getTags(), input.getOriginalId(), input.getLat(), input.getLon(), input.getUser(), input.getUid(), input.isVisible(), input.getVersion(), input.getChangeset(), input.getTimestamp()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{nodeId}")
    public ResponseEntity<Object> updateNode(@PathVariable("nodeId") BigInteger nodeId, @RequestBody Node node){
        Node currentNode = nodeRepository.findById(nodeId);
        if(currentNode == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentNode = new Node(node.getTags(), node.getOriginalId(), node.getLat(), node.getLon(), node.getUser(), node.getUid(), node.isVisible(), node.getVersion(), node.getChangeset(), node.getTimestamp());
        currentNode.setId(nodeId);

        nodeRepository.save(currentNode);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(nodeId).toUri());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
