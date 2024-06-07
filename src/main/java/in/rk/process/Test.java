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

	private static final String APPCODE_ID_KEY = "Application Code";
	private static final String APPCODE_PARENT_KEY = "LOBT";
	private static final String APPCODE_STATUS_KEY = "Status";
	private static final String APPCODE_URL = "https://www.appcode.com";

	private static final String RIT_ID_KEY = "Case ID";
	private static final String RIT_PARENT_KEY = "AppCode";
	private static final String RIT_STATUS_KEY = "Status";
	private static final String RIT_URL = "https://www.rit.com";

	private static final String IDR_ID_KEY = "CASEID";
	private static final String IDR_PARENT_KEY = "CASEID";
	private static final String IDR_STATUS_KEY = "OVERALLSTATUS";
	private static final String IDR_URL = "https://www.idr.com";
	
	
	private static final String VIRTUAL_ID_KEY = "HOSTNAME";
	private static final String VIRTUAL_PARENT_KEY = "ID";
	private static final String VIRTUAL_STATUS_KEY = "STATUS";
	private static final String VIRTUAL_URL = "https://www.virtual.com";


	private static final String DATABASE_ID_KEY = "HOSTNAME";
	private static final String DATABASE_PARENT_KEY = "ENVIRONMENT";
	private static final String DATABASE_STATUS_KEY = "STATUS";
	private static final String DATABASE_URL = "https://www.database.com";
	
	
	
	private static final String DEP_ID_KEY = "RECORD ID";
	private static final String DEP_PARENT_KEY = "APP CODE";
	private static final String DEP_STATUS_KEY = "STATUS";
	private static final String DEP_URL = "https://www.dep.com";

	public static List<Node> processAsNodeList(List<Map<String, String>> response, String idKey, String nameKey,
			String url, String statusKey) {
		return response.stream().map(element -> new Node(convertNumber(element.get(idKey)),
				convertNumber(element.get(nameKey)), url, element.get(statusKey), element, new ArrayList<Node>()))
				.collect(Collectors.toList());
	}

	public static List<Node> createNodeList(String id, String name, String url, String status, Map<String, String> data, List<Node> childs)
	{
		List<Node> nodeList = new ArrayList<>();
		Node node = Node.builder()
				.id(id)
				.name(name)
				.url(url)
				.status(status)
				.data(data)
				.childs(childs)
				.build();
		nodeList.add(node);
		return nodeList;
	}
	public static String convertNumber(String input) {
		try {
			double number = Double.parseDouble(input);
			return String.format("%.0f", number);
		} catch (Exception e) {
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
		

	
		List<Map<String, String>> virtualResponse = new ArrayList<>();
		virtualResponse.add(
				Map.of("STATUS", "ACTIVE", "ID", "BZID", "HOSTNAME", "x01sabcd", "data1", "Some data1"));

		virtualResponse.add(
				Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "x01babcd", "data2", "Some data2"));

		virtualResponse.add(
				Map.of("STATUS", "ACTIVE", "ID", "BZID", "HOSTNAME", "x01gabcd", "data1", "Some data1"));

		virtualResponse.add(
				Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "x01tabcd", "data1", "Some data1"));

		virtualResponse.add(
				Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "w01sabcd", "data1", "Some data1"));

		List<Node> virtualNodes = processAsNodeList(virtualResponse, VIRTUAL_ID_KEY, VIRTUAL_PARENT_KEY, VIRTUAL_URL, VIRTUAL_STATUS_KEY);
		
		
		List<Map<String, String>> databaseResponse = new ArrayList<>();
		databaseResponse.add(
				Map.of("STATUS", "ACTIVE", "ENVIRONMENT", "SIT", "HOSTNAME", "x01sxyzx", "data1", "Some data1"));

		databaseResponse.add(
				Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "UAT", "HOSTNAME", "x01bxyzx", "data2", "Some data2"));

		databaseResponse.add(
				Map.of("STATUS", "ACTIVE", "ENVIRONMENT", "PROD", "HOSTNAME", "x01gxyzx", "data1", "Some data1"));

		databaseResponse.add(
				Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "SIT", "HOSTNAME", "x01txyzx", "data1", "Some data1"));

		databaseResponse.add(
				Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "UAT", "HOSTNAME", "w01sxyzx", "data1", "Some data1"));
		
		List<Node> databaseNodes = processAsNodeList(databaseResponse, DATABASE_ID_KEY, DATABASE_PARENT_KEY, DATABASE_URL, DATABASE_STATUS_KEY);	
		
		List<Map<String, String>> depResponse = new ArrayList<>();
		depResponse.add(
				Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0001", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse.add(
				Map.of("STATUS", "REJECTED", "RECORD ID", "DEP0002", "APP CODE", "BZID", "data2", "Some data2"));

		depResponse.add(
				Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0003", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse.add(
				Map.of("STATUS", "CLOSED", "RECORD ID", "DEP0004", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse.add(
				Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0005", "APP CODE", "BZID", "data1", "Some data1"));
		
		List<Node> depNodes = processAsNodeList(depResponse, DEP_ID_KEY, DEP_PARENT_KEY, DEP_URL, DEP_STATUS_KEY);
		
		//Case-1
		//addChildsToRootNodes(ritNodes, idrNodes);
		//addChildsToRootNodes(appcodeNodes, ritNodes);
		// Print the nodes
		appcodeNodes.forEach(System.out::println);
		System.out.println("------------------");
		ritNodes.forEach(System.out::println);
		System.out.println("------------------");
		idrNodes.forEach(System.out::println);
		System.out.println("------------------");
		virtualNodes.forEach(System.out::println);
		System.out.println("------------------");
		databaseNodes.forEach(System.out::println);
		System.out.println("------------------");
		depNodes.forEach(System.out::println);
		System.out.println("------------------");
	}

	private static void addChildsToRootNodes(List<Node> rootNode, List<Node> childNode) {
		childNode.forEach(ritNode -> {
			rootNode.stream().filter(appcodeNode -> ritNode.getName().equals(appcodeNode.getId())).findFirst()
					.ifPresent(appcodeNode -> appcodeNode.getChilds().add(ritNode));
		});
	}
}
