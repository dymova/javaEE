import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {
    private static final Logger log = LogManager.getLogger(Controller.class.getName());

    private JaxbXmlReader reader;
    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> keys = new HashMap<>();

    public Controller(JaxbXmlReader jaxbXmlReader) {
        reader = jaxbXmlReader;
    }


    public void run() throws JAXBException, XMLStreamException {
        Node node;
        while((node = reader.getNextNode()) != null)
        {
            List<Tag> tags = node.getTag();
            for (Tag tag : tags) {
                incrementValue(keys, tag.getK());
            }
            incrementValue(users, node.getUser());
        }
        printStatistics();
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

    private void printStatistics() {
        log.info("USERS STATISTICS: ");
        log.info( "Total: " + users.size());

        Comparator<Map.Entry<String, Integer>> byValue = (entry1, entry2) ->
                entry1.getValue().compareTo(entry2.getValue());

        List<Map.Entry<String, Integer>> sortedUsers = users.entrySet()
                .stream()
                .sorted(byValue.reversed())
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> user : sortedUsers) {
            log.info(user.getKey() + " - " + user.getValue() + " times");
        }

        log.info("-------------------------------------------------------");
        log.info("KEYS STATISTICS: ");
        log.info( "Total: " + keys.size());

        for (Map.Entry<String, Integer> entry : keys.entrySet()) {
            log.info(entry.getKey() + " - " + entry.getValue() + " times");
        }
    }
}
