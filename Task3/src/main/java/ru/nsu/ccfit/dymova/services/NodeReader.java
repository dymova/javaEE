package ru.nsu.ccfit.dymova.services;

import ru.nsu.ccfit.dymova.entities.Node;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface NodeReader {
    Node getNextNode() throws XMLStreamException, JAXBException;

    void close() throws IOException;
}
