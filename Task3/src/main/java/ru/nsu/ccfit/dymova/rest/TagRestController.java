package ru.nsu.ccfit.dymova.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.dymova.entities.Tag;
import ru.nsu.ccfit.dymova.jpa.TagRepository;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagRestController {

//    @Autowired
//    private TagRepository tagRepository;
//
//    @RequestMapping(method = RequestMethod.GET, value = "{/tagId}")
//    public Tag readTag(@PathVariable BigInteger tagId) {
//        return tagRepository.findOne(tagId);
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Tag> readTags() {
//        return tagRepository.findAll();
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public void createTag(@RequestBody Tag tag) {
//        validateNode(tag.getNodeId());
//        tagRepository.save(tag);
//    }
//
////    @RequestMapping(method = RequestMethod.PUT)
////    public void updateTag(@RequestBody Tag tag) {
////        tagRepository.updateTag(tag);
////    }
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{tagId}")
//    public void deleteTag(@PathVariable("tagId") BigInteger tagId){
//        tagRepository.delete(tagId);
//    }
//    private void validateNode(BigInteger nodeId) {
//        tagRepository.findByNodeId(nodeId).orElseThrow(() -> new NodeNotFoundException(nodeId));
//
//    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class NodeNotFoundException extends RuntimeException {
    public NodeNotFoundException(BigInteger nodeId) {
        super("couldn't found node '" + nodeId + "'.");
    }
}

