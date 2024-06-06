package in.rk.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeProcessor {
	private static final String URL = "https://www.abcd.com";

	public List<Node> processTickets(List<Map<String, String>> ticketsList, List<Map<String, String>> devicesList) {
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
					.filter(device -> device.get("device").equals(parentNode.getId())).map(device -> {
						Node childNode = new Node();
						childNode.setId(device.get("device id"));
						childNode.setUrl(URL);
						childNode.setStatus(device.get("ovf status"));
						childNode.setData(device);
						return childNode;
					}).collect(Collectors.toList());
			parentNode.setChilds(childNodes);
		}

		return parentNodes;
	}

	public static void main(String[] args) {
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

		NodeProcessor processor = new NodeProcessor();
		List<Node> nodes = processor.processTickets(ticketsList, devicesList);

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
				}
			}
			System.out.println("-------------");
		}
	}
}
