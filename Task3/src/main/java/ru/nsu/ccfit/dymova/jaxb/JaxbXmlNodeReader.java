package ru.nsu.ccfit.dymova.jaxb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.ccfit.dymova.entities.Node;
import ru.nsu.ccfit.dymova.entities.Tag;
import ru.nsu.ccfit.dymova.services.NodeReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


public class JaxbXmlNodeReader implements NodeReader {
    private static final Logger log = LoggerFactory.getLogger(JaxbXmlNodeReader.class);

    private XMLStreamReader reader;
    private Unmarshaller unmarshaller;
    private InputStream stream;


    public JaxbXmlNodeReader(InputStream stream) throws XMLStreamException, JAXBException {
        this.stream = stream;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        reader = inputFactory.createXMLStreamReader(stream);
        reader = new NamespaceAddingReader(reader);
        JAXBContext jc = JAXBContext.newInstance(org.openstreetmap.osm._0.Node.class);
        unmarshaller = jc.createUnmarshaller();

    }


    @Override
    public Node getNextNode() throws XMLStreamException, JAXBException {
        while(reader.hasNext()) {
            if(reader.isStartElement() && "node".equals(reader.getName().toString())){
                JAXBElement<org.openstreetmap.osm._0.Node> nodeJAXBElement = unmarshaller.unmarshal(reader, org.openstreetmap.osm._0.Node.class);
                org.openstreetmap.osm._0.Node osmNode = nodeJAXBElement.getValue();
                Timestamp timestamp = new Timestamp(osmNode.getTimestamp().toGregorianCalendar().getTimeInMillis());
                List<Tag> tags = osmNode.getTag().stream()
                        .map(t -> new Tag(t.getK(), t.getV()))
                        .collect(Collectors.toList());
                return new Node(tags, osmNode.getId(),osmNode.getLat(), osmNode.getLon(), osmNode.getUser(),
                        osmNode.getUid(), osmNode.isVisible(), osmNode.getVersion(), osmNode.getChangeset(), timestamp);
            }
            reader.next();
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        stream.close();
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