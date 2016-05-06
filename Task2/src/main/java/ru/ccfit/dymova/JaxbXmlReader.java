package ru.ccfit.dymova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openstreetmap.osm._0.Node;

import javax.xml.bind.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.validation.Schema;
import java.io.InputStream;


public class JaxbXmlReader {
    private static final Logger log = LogManager.getLogger(JaxbXmlReader.class.getName());

    private XMLStreamReader reader;
    private Unmarshaller unmarshaller;



    public JaxbXmlReader(InputStream stream, Schema schema) throws XMLStreamException, JAXBException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        reader = inputFactory.createXMLStreamReader(stream);
        reader = new NamespaceAddingReader(reader);
        JAXBContext jc = JAXBContext.newInstance(Node.class);
        unmarshaller = jc.createUnmarshaller();
//        unmarshaller.setSchema(schema);
    }


    public Node getNextNode() throws XMLStreamException, JAXBException {
        while(reader.hasNext()) {
            if(reader.isStartElement() && "node".equals(reader.getName().toString())){
                JAXBElement<Node> nodeElement = unmarshaller.unmarshal(reader, Node.class);
                return nodeElement.getValue();
            }
            reader.next();
        }
        return null;
    }


    private static class NamespaceAddingReader extends StreamReaderDelegate {
        private static final String NAMESPACE_URI = "http://openstreetmap.org/osm/0.6";

        public NamespaceAddingReader(XMLStreamReader reader) {
            super(reader);
        }

        @Override
        public String getNamespaceURI() {
            return NAMESPACE_URI;
        }
    }
}
