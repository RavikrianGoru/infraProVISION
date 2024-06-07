package in.rk.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Node {
	private String id;
	private String name;
	private String url;
	private String status;
	private Map<String, String> data;
	@Builder.Default
	private List<Node> childs = new ArrayList<>();
}

public class Test {

	private static final String APPCODE_URL = "https://www.appcode.com";
	private static final String APPCODE_ID_KEY = "Application Code";
	private static final String APPCODE_PARENT_KEY = "LOBT";
	private static final String APPCODE_STATUS_KEY = "Status";

	private static final String RIT_ID_KEY = "Case ID";
	private static final String RIT_PARENT_KEY = "AppCode";
	private static final String RIT_STATUS_KEY = "Status";
	private static final String RIT_URL = "https://www.rit.com";

	private static final String IDR_ID_KEY = "CASEID";
	private static final String IDR_PARENT_KEY = "CASEID";
	private static final String IDR_STATUS_KEY = "OVERALLSTATUS";
	private static final String IDR_URL = "https://www.idr.com";
	
	
	private static final String VIRTUAL_URL = "https://www.virtual.com";
	private static final String DATABASE_URL = "https://www.database.com";
	private static final String DEP_URL = "https://www.dep.com";

	public static List<Node> processAsNodeList(List<Map<String, String>> response, String idKey, String nameKey,
			String url, String statusKey) {
		return response.stream().map(element -> new Node(convertNumber(element.get(idKey)),
				convertNumber(element.get(nameKey)), url, element.get(statusKey), element, new ArrayList<Node>()))
				.collect(Collectors.toList());
	}

	public static String convertNumber(String input) {
		try {
			double number = Double.parseDouble(input);
			return String.format("%.0f", number);
		} catch (NumberFormatException e) {
			return input;
		}
	}

	 
	public static void main(String[] args) {

		List<Map<String, String>> appcodeResponse = new ArrayList<>();
		appcodeResponse.add(
				Map.of("Status", "active", "Application Code", "BZID", "platform", "Windows", "data1", "some data-1", "data2", "some data-2", "LOBT", "MOT"));
		List<Node> appcodeNodes = processAsNodeList(appcodeResponse, APPCODE_ID_KEY, APPCODE_PARENT_KEY, APPCODE_URL, APPCODE_STATUS_KEY);


		List<Map<String, String>> ritResponse = new ArrayList<>();
		ritResponse.add(
				Map.of("Status", "DAO Rejected", "AppCode", "BZID", "Case ID", "1.0180379E7", "data1", "Some data1"));

		ritResponse.add(
				Map.of("Status", "Rejected", "AppCode", "BZID", "Case ID", "1.0180378E7", "data2", "Some data2"));

		ritResponse.add(
				Map.of("Status", "Case Closed", "AppCode", "BZID", "Case ID", "2.0180379E7", "data1", "Some data1"));

		ritResponse.add(
				Map.of("Status", "Case Rejected", "AppCode", "BZID", "Case ID", "3.0180379E7", "data1", "Some data1"));

		ritResponse
				.add(Map.of("Status", "Rejected", "AppCode", "BZID", "Case ID", "4.0180379E7", "data1", "Some data1"));

		List<Node> ritNodes = processAsNodeList(ritResponse, RIT_ID_KEY, RIT_PARENT_KEY, RIT_URL, RIT_STATUS_KEY);
		
		
		List<Map<String, String>> idrResponse = new ArrayList<>();
		idrResponse.add(
				Map.of("OVERALLSTATUS", "COMPLETED", "AppCode", "BZID", "CASEID", "1.0180379E7", "data1", "Some data1"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "COMPLETED", "AppCode", "BZID", "CASEID", "1.0180379E7", "data2", "Some data2"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "PENDING", "AppCode", "BZID", "CASEID", "2.0180379E7", "data1", "Some data1"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "PENDING", "AppCode", "BZID", "CASEID", "3.0180379E7", "data1", "Some data1"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "REJECTED", "AppCode", "BZID", "CASEID", "3.0180379E7", "data1", "Some data1"));

		List<Node> idrNodes = processAsNodeList(idrResponse, IDR_ID_KEY, IDR_PARENT_KEY, IDR_URL, IDR_STATUS_KEY);
		

		// Print the nodes
		//appcodeNodes.forEach(System.out::println);
		//ritNodes.forEach(System.out::println);
		
		// Traverse ritNodes and add matching nodes to childs list in appcodeNodes
		addChildsToRootNodes(ritNodes, idrNodes);

		addChildsToRootNodes(appcodeNodes, ritNodes);
		// Print the nodes
		appcodeNodes.forEach(System.out::println);
		

	}

	private static void addChildsToRootNodes(List<Node> rootNode, List<Node> childNode) {
		childNode.forEach(ritNode -> {
			rootNode.stream().filter(appcodeNode -> ritNode.getName().equals(appcodeNode.getId())).findFirst()
					.ifPresent(appcodeNode -> appcodeNode.getChilds().add(ritNode));
		});
	}
}
