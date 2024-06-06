package in.rk.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeProcessDynalic {

	private static final String URL = "https://www.abcd.com";

	public void processTickets(Node rootNode, List<Map<String, String>> someList, String parentNodeKey,
			String subNodeKeyMatch) {
		if (rootNode.getChilds() == null) {
			rootNode.setChilds(new ArrayList<>());
		}

		List<Node> matchedNodes = someList.stream().filter(map -> map.get(subNodeKeyMatch).equals(rootNode.getId()))
				.map(map -> {
					Node node = new Node();
					node.setId(map.get(subNodeKeyMatch));
					node.setUrl(URL);
					node.setStatus(map.get("actual status"));
					node.setData(map);
					return node;
				}).collect(Collectors.toList());

		rootNode.getChilds().addAll(matchedNodes);

		for (Node childNode : rootNode.getChilds()) {
			processTickets(childNode, someList, parentNodeKey, subNodeKeyMatch);
		}
	}

	public static void main(String[] args) {
		// Define root node
		Node rootNode = new Node();
		rootNode.setId("Root");
		rootNode.setUrl(URL);
		rootNode.setStatus("active");
		rootNode.setData(Map.of("name", "Root", "root id", "Root1", "actual status", "active", "data1", "some data-1",
				"data2", "some data-2"));

		List<Map<String, String>> rootList = new ArrayList<>();
		rootList.add(Map.of("name", "Root1", "r id", "A001", "actual status", "active", "data1", "some data-1", "data2",
				"some data-2"));
		rootList.add(Map.of("name", "Root1", "r id", "A002", "actual status", "active", "data1", "some data-1", "data2",
				"some data-2"));
		rootList.add(Map.of("name", "Root1", "r id", "A003", "actual status", "active", "data1", "some data-1", "data2",
				"some data-2"));

		List<Map<String, String>> ticketsList = new ArrayList<>();

		// Sample data for ticketsList
		ticketsList.add(Map.of("name", "A001", "case id", "C001", "actual status", "active", "data1", "some data-1",
				"data2", "some data-2"));

		ticketsList.add(Map.of("name", "A002", "case id", "C002", "actual status", "active", "data1", "some data-1",
				"data2", "some data-2"));

		ticketsList.add(Map.of("name", "A003", "case id", "C003", "actual status", "active", "data1", "some data-1",
				"data2", "some data-2"));

		List<Map<String, String>> devicesList = new ArrayList<>();

		// Sample data for devicesList
		devicesList.add(Map.of("device", "A001", "device id", "C001", "ovf status", "active", "data", "some data1"));

		devicesList.add(Map.of("device", "A001", "device id", "C002", "ovf status", "active", "data", "some data1"));

		devicesList.add(Map.of("device", "A002", "device id", "C003", "ovf status", "active", "data", "some data1"));

		List<Map<String, String>> accountsList = new ArrayList<>();

		// Sample data for accountsList
		accountsList.add(Map.of("account", "C001", "accountNbr", "D002", "ovf status", "active", "data", "some dat1"));

		accountsList.add(Map.of("account", "C003", "accountNbr", "D002", "ovf status", "active", "data", "some dat1"));

		accountsList.add(Map.of("account", "C001", "accountNbr", "D003", "ovf status", "active", "data", "some dat1"));

		NodeProcessDynalic processor = new NodeProcessDynalic();

		// Processing rootList to add nodes to rootNode
		processor.processTickets(rootNode, rootList, "name", "r id");

		// Processing ticketsList
		for (Node node : rootNode.getChilds()) {
			processor.processTickets(node, ticketsList, "name", "case id");
		}

		// Processing devicesList
		for (Node node : rootNode.getChilds()) {
			for (Node childNode : node.getChilds()) {
				processor.processTickets(childNode, devicesList, "device", "device id");
			}
		}

		// Processing accountsList
		for (Node node : rootNode.getChilds()) {
			for (Node childNode : node.getChilds()) {
				for (Node grandChildNode : childNode.getChilds()) {
					processor.processTickets(grandChildNode, accountsList, "account", "accountNbr");
				}
			}
		}

		// Printing the result
		printNode(rootNode, "");
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
