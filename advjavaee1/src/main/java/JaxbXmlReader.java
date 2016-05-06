import org.openstreetmap.osm._0.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.validation.Schema;
import java.io.InputStream;

public class JaxbXmlReader {
    private static final String NODE = "node";
    private final XMLEventReader reader;
    private Unmarshaller unmarshaller;

    public JaxbXmlReader(InputStream stream, Schema schema) throws XMLStreamException, JAXBException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        reader = inputFactory.createXMLEventReader(stream);
        JAXBContext jc = JAXBContext.newInstance("org.openstreetmap.osm._0");
        unmarshaller = jc.createUnmarshaller();
//        unmarshaller.setSchema(schema);
    }


    public Node getNextNode() throws XMLStreamException, JAXBException {
        while (reader.hasNext()) {
            XMLEvent event = reader.peek();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String elementName = startElement.getName().toString();

                if (elementName.equals(JaxbXmlReader.NODE)) {
                    JAXBElement<Node> nodeElement = unmarshaller.unmarshal(reader, Node.class);
                    return nodeElement.getValue();
                }
            }
            reader.nextEvent();
        }
        return null;
    }
}
