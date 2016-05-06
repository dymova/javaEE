package ru.nsu.ccfit.dymova.logic;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.jdbc.JdbcNodeDao;
import ru.nsu.ccfit.dymova.jpa.NodeRepository;
import ru.nsu.ccfit.dymova.services.NodeDao;
import ru.nsu.ccfit.dymova.services.NodeReader;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

@Component
public class LoadController {
    private static final Logger log = LoggerFactory.getLogger(LoadController.class);

    @Autowired
    private NodeReader nodeReader;

    @Autowired
    private NodeRepository nodeRepository;

    public void loadToDatabase() throws JAXBException, XMLStreamException {
        log.info("start load to db");
        Node node;
        node = nodeReader.getNextNode();
        nodeRepository.save(node);
    }
}


