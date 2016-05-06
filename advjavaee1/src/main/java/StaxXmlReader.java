import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.*;
import java.util.stream.Collectors;

public class StaxXmlReader {
    private static final Logger log = LogManager.getLogger(StaxXmlReader.class.getName());
    private static final String USER = "user";
    private static final String TAG = "tag";
    private static final String KEY = "k";
    private static final String NODE = "node";
    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> keys = new HashMap<>();

    public void process(XMLEventReader reader) throws XMLStreamException {
        while(reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                String elementName = startElement.getName().toString();

                if(elementName.equals(StaxXmlReader.NODE)){
                    Attribute attr = startElement.getAttributeByName(QName.valueOf(USER));
                    if(attr != null) {
                        incrementValue(users, attr.getValue());
                    }
                }
                if(elementName.equals(TAG)) {
                    Attribute attr = startElement.getAttributeByName(QName.valueOf(KEY));
                    if(attr != null)
                    {
                        incrementValue(keys, attr.getValue());
                    }
                }
            }
        }
//        printStatistics();
    }

    private void incrementValue(HashMap<String, Integer> map, String key) {
        if(map.containsKey(key)){
            Integer count = map.get(key);
            map.put(key, ++count);
        }
        else{
            map.put(key, 1);
        }
    }

//    private void printStatistics() {
//        log.info("USERS STATISTICS: ");
//        log.info( "Total: " + users.size());
//
//        Comparator<Map.Entry<String, Integer>> byValue = (entry1, entry2) ->
//                entry1.getValue().compareTo(entry2.getValue());
//
//        List<Map.Entry<String, Integer>> sortedUsers = users.entrySet()
//                .stream()
//                .sorted(byValue.reversed())
//                .collect(Collectors.toList());
//
//        for (Map.Entry<String, Integer> user : sortedUsers) {
//            log.info(user.getKey() + " - " + user.getValue() + " times");
//        }
//
//        log.info("-------------------------------------------------------");
//        log.info("KEYS STATISTICS: ");
//        log.info( "Total: " + keys.size());
//
//        for (Map.Entry<String, Integer> entry : keys.entrySet()) {
//            log.info(entry.getKey() + " - " + entry.getValue() + " times");
//        }
//    }


}
