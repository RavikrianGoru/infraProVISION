package in.rk.process;





import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Abcd {

    private static final String URL = "https://www.abcd.com";

    public void processTickets(Node rootNode, List<Map<String, String>> someList, String parentNodeKey, String subNodeKeyMatch) {
        if (rootNode.getChilds() == null) {
            rootNode.setChilds(new ArrayList<>());
        }

        List<Node> matchedNodes = someList.stream()
            .filter(map -> map.get(parentNodeKey).equals(rootNode.getId()))
            .map(map -> {
                Node node = new Node();
                node.setId(map.get(subNodeKeyMatch));
                node.setUrl(URL);
                node.setStatus(map.get("ovf status"));
                node.setData(map);
                return node;
            })
            .collect(Collectors.toList());

        rootNode.getChilds().addAll(matchedNodes);

        for (Node childNode : rootNode.getChilds()) {
            processTickets(childNode, someList, parentNodeKey, subNodeKeyMatch);
        }
    }

    public static void main(String[] args) {
        List<Map<String, String>> ticketsList = new ArrayList<>();

        // Sample data for ticketsList
        ticketsList.add(Map.of(
            "name", "A001",
            "case id", "C001",
            "actual status", "active",
            "data1", "some data-1",
            "data2", "some data-2"
        ));

        ticketsList.add(Map.of(
            "name", "A002",
            "case id", "C002",
            "actual status", "active",
            "data1", "some data-1",
            "data2", "some data-2"
        ));

        ticketsList.add(Map.of(
            "name", "A003",
            "case id", "C003",
            "actual status", "active",
            "data1", "some data-1",
            "data2", "some data-2"
        ));

        List<Map<String, String>> devicesList = new ArrayList<>();

        // Sample data for devicesList
        devicesList.add(Map.of(
            "device", "A001",
            "device id", "C001",
            "ovf status", "active",
            "data", "some data1"
        ));

        devicesList.add(Map.of(
            "device", "A001",
            "device id", "C002",
            "ovf status", "active",
            "data", "some data1"
        ));

        devicesList.add(Map.of(
            "device", "A002",
            "device id", "C003",
            "ovf status", "active",
            "data", "some data1"
        ));

        List<Map<String, String>> accountsList = new ArrayList<>();

        // Sample data for accountsList
        accountsList.add(Map.of(
            "account", "C001",
            "accountNbr", "D002",
            "ovf status", "active",
            "data", "some dat1"
        ));

        accountsList.add(Map.of(
            "account", "C003",
            "accountNbr", "D002",
            "ovf status", "active",
            "data", "some dat1"
        ));

        accountsList.add(Map.of(
            "account", "C001",
            "accountNbr", "D003",
            "ovf status", "active",
            "data", "some dat1"
        ));

        // Creating root nodes from ticketsList
        List<Node> rootNodes = ticketsList.stream().map(ticket -> {
            Node node = new Node();
            node.setId(ticket.get("name"));
            node.setUrl(URL);
            node.setStatus(ticket.get("actual status"));
            node.setData(ticket);
            return node;
        }).collect(Collectors.toList());

        Abcd processor = new Abcd();

        // Processing devicesList
        for (Node rootNode : rootNodes) {
            processor.processTickets(rootNode, devicesList, "device", "device id");
        }

        // Processing accountsList
        for (Node rootNode : rootNodes) {
            processor.processTickets(rootNode, accountsList, "account", "accountNbr");
        }

        // Printing the result
        for (Node node : rootNodes) {
            printNode(node, "");
        }
    }

    private static void printNode(Node node, String indent) {
        System.out.println(indent + "Node ID: " + node.getId());
        System.out.println(indent + "Node URL: " + node.getUrl());
        System.out.println(indent + "Node Status: " + node.getStatus());
        System.out.println(indent + "Node Data: " + node.getData());
        if (node.getChilds() != null) {
            for (Node child : node.getChilds()) {
                printNode(child, indent + "    ");
            }
        }
    }
}

