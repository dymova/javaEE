package ru.ccfit.dymova;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openstreetmap.osm._0.Node;
import org.openstreetmap.osm._0.Tag;
import ru.ccfit.dymova.dao.NodesDAO;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private static final Logger log = LogManager.getLogger(Controller.class.getName());

    private JaxbXmlReader reader;
    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> keys = new HashMap<>();
    private List<Node> nodes = new ArrayList<>();

    public Controller(JaxbXmlReader jaxbXmlReader) {
        reader = jaxbXmlReader;
    }


    public void run() throws Exception {
        Node node;
        try(NodesDAO nodesDAO = new NodesDAO()) {
            while ((node = reader.getNextNode()) != null) {
                incrementValue(users, node.getUser());

                List<Tag> tags = node.getTag();
                for (Tag tag : tags) {
                    incrementValue(keys, tag.getK());
                }

                nodes.add(node);
                if(nodes.size() == 1) {
//                    nodesDAO.addNodeByExecuteUpdate(nodes);
                    nodesDAO.addNodeByPreparedStatement(nodes); //todo timestamp
//                    nodesDAO.addNodeByBatch(nodes);
                    nodes.clear();
                    return;
                }
            }
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
