package ru.nsu.ccfit.dymova.logic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.jpa.NodeRepository;
import ru.nsu.ccfit.dymova.services.NodeReader;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Component
public class LoadController {
    private static final Logger log = LoggerFactory.getLogger(LoadController.class);

    @Autowired
    private NodeReader nodeReader;

    @Autowired
    private NodeRepository nodeRepository;

    public void loadToDatabase() throws JAXBException, XMLStreamException, IOException {
        Node node;
//        Node node =  nodeReader.getNextNode();
//        while ((node = nodeReader.getNextNode()) != null) {
//            nodeRepository.save(node);
//        }
//        nodeReader.close();
    }
}


