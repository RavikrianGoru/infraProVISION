package in.rk.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeProcessor {
    private static final String URL = "https://www.abcd.com";

    public List<Node> processTickets(List<Map<String, String>> ticketsList, List<Map<String, String>> devicesList, List<Map<String, String>> accountsList) {
        // Process ticketsList to create parent nodes
        List<Node> parentNodes = ticketsList.stream().map(ticket -> {
            Node node = new Node();
            node.setId(ticket.get("name"));
            node.setUrl(URL);
            node.setStatus(ticket.get("actual status"));
            node.setData(ticket);
            return node;
        }).collect(Collectors.toList());

        // Process devicesList and attach child nodes to the corresponding parent nodes
        for (Node parentNode : parentNodes) {
            List<Node> childNodes = devicesList.stream()
                .filter(device -> device.get("device").equals(parentNode.getId()))
                .map(device -> {
                    Node childNode = new Node();
                    childNode.setId(device.get("device id"));
                    childNode.setUrl(URL);
                    childNode.setStatus(device.get("ovf status"));
                    childNode.setData(device);
                    return childNode;
                }).collect(Collectors.toList());
            parentNode.setChilds(childNodes);

            // Process accountsList and attach child nodes to the corresponding device nodes
            for (Node childNode : childNodes) {
                List<Node> grandChildNodes = accountsList.stream()
                    .filter(account -> account.get("account").equals(childNode.getId()))
                    .map(account -> {
                        Node grandChildNode = new Node();
                        grandChildNode.setId(account.get("accountNbr"));
                        grandChildNode.setUrl(URL);
                        grandChildNode.setStatus(account.get("ovf status"));
                        grandChildNode.setData(account);
                        return grandChildNode;
                    }).collect(Collectors.toList());
                childNode.setChilds(grandChildNodes);
            }
        }

        return parentNodes;
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

        NodeProcessor processor = new NodeProcessor();
        List<Node> nodes = processor.processTickets(ticketsList, devicesList, accountsList);

        // Printing the result
        for (Node node : nodes) {
            System.out.println("Parent Node ID: " + node.getId());
            System.out.println("Parent Node URL: " + node.getUrl());
            System.out.println("Parent Node Status: " + node.getStatus());
            System.out.println("Parent Node Data: " + node.getData());
            if (node.getChilds() != null) {
                for (Node child : node.getChilds()) {
                    System.out.println("    Child Node ID: " + child.getId());
                    System.out.println("    Child Node URL: " + child.getUrl());
                    System.out.println("    Child Node Status: " + child.getStatus());
                    System.out.println("    Child Node Data: " + child.getData());
                    if (child.getChilds() != null) {
                        for (Node grandChild : child.getChilds()) {
                            System.out.println("        GrandChild Node ID: " + grandChild.getId());
                            System.out.println("        GrandChild Node URL: " + grandChild.getUrl());
                            System.out.println("        GrandChild Node Status: " + grandChild.getStatus());
                            System.out.println("        GrandChild Node Data: " + grandChild.getData());
                        }
                    }
                }
            }
            System.out.println("-------------");
        }
    }}
