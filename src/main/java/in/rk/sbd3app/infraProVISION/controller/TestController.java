package in.rk.sbd3app.infraProVISION.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.rk.process.Node;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class TestController {

	private static final String APPCODE_TAG = "APPCODE";
	private static final String APPCODE_ID_KEY = "Application Code";
	private static final String APPCODE_PARENT_KEY = "LOBT";
	private static final String APPCODE_STATUS_KEY = "Status";
	private static final String APPCODE_URL = "https://www.appcode.com";
	
	private static final String RIT_TAG = "RIT";
	private static final String RIT_ID_KEY = "Case ID";
	private static final String RIT_PARENT_KEY = "AppCode";
	private static final String RIT_STATUS_KEY = "Status";
	private static final String RIT_URL = "https://www.rit.com";
	

	private static final String IDR_TAG = "IDR";
	private static final String IDR_ID_KEY = "CASEID";
	private static final String IDR_PARENT_KEY = "CASEID";
	private static final String IDR_STATUS_KEY = "OVERALLSTATUS";
	private static final String IDR_URL = "https://www.idr.com";
	
	private static final String VIRTUAL_TAG = "VM";
	private static final String VIRTUAL_ID_KEY = "HOSTNAME";
	private static final String VIRTUAL_PARENT_KEY = "ID";
	private static final String VIRTUAL_STATUS_KEY = "STATUS";
	private static final String VIRTUAL_URL = "https://www.virtual.com";

	private static final String DATABASE_TAG = "DB";
	private static final String DATABASE_ID_KEY = "HOSTNAME";
	private static final String DATABASE_PARENT_KEY = "ENVIRONMENT";
	private static final String DATABASE_STATUS_KEY = "STATUS";
	private static final String DATABASE_URL = "https://www.database.com";

	private static final String DEP_TAG = "DEP";
	private static final String DEP_ID_KEY = "RECORD ID";
	private static final String DEP_PARENT_KEY = "APP CODE";
	private static final String DEP_STATUS_KEY = "STATUS";
	private static final String DEP_URL = "https://www.dep.com";
	
	
	
	private String convertNumber(String input) {
		try {
			double number = Double.parseDouble(input);
			return String.format("%.0f", number);
		} catch (Exception e) {
			return input;
		}
	}
	private List<Node> processAsNodeList(List<Map<String, String>> response, String tag, String idKey, String nameKey,
			String url, String statusKey) {
		return response.stream().map(element -> new Node(tag, convertNumber(element.get(idKey)),
				convertNumber(element.get(nameKey)), url, element.get(statusKey), element, new ArrayList<Node>()))
				.collect(Collectors.toList());
	}
	private static void addChildsToRootNodes(List<Node> rootNodes, List<Node> childNodes) {
		childNodes.forEach(eachChildNode -> {
			rootNodes.stream().filter(eachRootNode -> eachChildNode.getName().equals(eachRootNode.getId())).findFirst()
					.ifPresent(matchedRootNode -> matchedRootNode.getChilds().add(eachChildNode));
		});
	}
	
	private  Node collectData()
	{
		//1
		List<Map<String, String>> appcodeResponse = new ArrayList<>();
		appcodeResponse.add(Map.of("Status", "active", "Application Code", "BZID", "platform", "Windows", "data1",
				"some data-1", "data2", "some data-2", "LOBT", "MOT"));
		List<Node> appcodeNodes = processAsNodeList(appcodeResponse, APPCODE_TAG, APPCODE_ID_KEY, APPCODE_PARENT_KEY, APPCODE_URL,APPCODE_STATUS_KEY);
		//2
		List<Map<String, String>> ritResponse = new ArrayList<>();
		ritResponse.add(
				Map.of("Status", "DAO Rejected", "AppCode", "BZID", "Case ID", "1.0180379E7", "data1", "Some data1"));

		ritResponse
				.add(Map.of("Status", "Rejected", "AppCode", "BZID", "Case ID", "1.0180378E7", "data2", "Some data2"));

		ritResponse.add(
				Map.of("Status", "Case Closed", "AppCode", "BZID", "Case ID", "2.0180379E7", "data1", "Some data1"));

		ritResponse.add(
				Map.of("Status", "Case Rejected", "AppCode", "BZID", "Case ID", "3.0180379E7", "data1", "Some data1"));

		ritResponse
				.add(Map.of("Status", "Rejected", "AppCode", "BZID", "Case ID", "4.0180379E7", "data1", "Some data1"));

		List<Node> ritNodes = processAsNodeList(ritResponse, RIT_TAG, RIT_ID_KEY, RIT_PARENT_KEY, RIT_URL, RIT_STATUS_KEY);
		//3
		List<Map<String, String>> idrResponse = new ArrayList<>();
		idrResponse.add(Map.of("OVERALLSTATUS", "COMPLETED", "AppCode", "BZID", "CASEID", "1.0180379E7", "data1",
				"Some data1"));

		idrResponse.add(Map.of("OVERALLSTATUS", "COMPLETED", "AppCode", "BZID", "CASEID", "1.0180379E7", "data2",
				"Some data2"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "PENDING", "AppCode", "BZID", "CASEID", "2.0180379E7", "data1", "Some data1"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "PENDING", "AppCode", "BZID", "CASEID", "3.0180379E7", "data1", "Some data1"));

		idrResponse.add(
				Map.of("OVERALLSTATUS", "REJECTED", "AppCode", "BZID", "CASEID", "3.0180379E7", "data1", "Some data1"));

		List<Node> idrNodes = processAsNodeList(idrResponse, IDR_TAG, IDR_ID_KEY, IDR_PARENT_KEY, IDR_URL, IDR_STATUS_KEY);
		

		List<Map<String, String>> virtualResponse = new ArrayList<>();
		virtualResponse.add(Map.of("STATUS", "ACTIVE", "ID", "BZID", "HOSTNAME", "x01sabcd", "data1", "Some data1"));

		virtualResponse.add(Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "x01babcd", "data2", "Some data2"));

		virtualResponse.add(Map.of("STATUS", "ACTIVE", "ID", "BZID", "HOSTNAME", "x01gabcd", "data1", "Some data1"));

		virtualResponse.add(Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "x01tabcd", "data1", "Some data1"));

		virtualResponse.add(Map.of("STATUS", "DISPOSED", "ID", "BZID", "HOSTNAME", "w01sabcd", "data1", "Some data1"));

		List<Node> virtualNodes = processAsNodeList(virtualResponse, VIRTUAL_TAG, VIRTUAL_ID_KEY, VIRTUAL_PARENT_KEY, VIRTUAL_URL,
				VIRTUAL_STATUS_KEY);
		List<Map<String, String>> databaseResponse = new ArrayList<>();
		databaseResponse
				.add(Map.of("STATUS", "ACTIVE", "ENVIRONMENT", "SIT", "HOSTNAME", "x01sxyzx", "data1", "Some data1"));

		databaseResponse
				.add(Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "UAT", "HOSTNAME", "x01bxyzx", "data2", "Some data2"));

		databaseResponse
				.add(Map.of("STATUS", "ACTIVE", "ENVIRONMENT", "PROD", "HOSTNAME", "x01gxyzx", "data1", "Some data1"));

		databaseResponse
				.add(Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "SIT", "HOSTNAME", "x01txyzx", "data1", "Some data1"));

		databaseResponse
				.add(Map.of("STATUS", "DISPOSED", "ENVIRONMENT", "UAT", "HOSTNAME", "w01sxyzx", "data1", "Some data1"));

		List<Node> databaseNodes = processAsNodeList(databaseResponse, DATABASE_TAG, DATABASE_ID_KEY, DATABASE_PARENT_KEY,
				DATABASE_URL, DATABASE_STATUS_KEY);

		List<Map<String, String>> depResponse = new ArrayList<>();
		depResponse
				.add(Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0001", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse
				.add(Map.of("STATUS", "REJECTED", "RECORD ID", "DEP0002", "APP CODE", "BZID", "data2", "Some data2"));

		depResponse
				.add(Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0003", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse.add(Map.of("STATUS", "CLOSED", "RECORD ID", "DEP0004", "APP CODE", "BZID", "data1", "Some data1"));

		depResponse
				.add(Map.of("STATUS", "APPROVED", "RECORD ID", "DEP0005", "APP CODE", "BZID", "data1", "Some data1"));

		List<Node> depNodes = processAsNodeList(depResponse, DEP_TAG, DEP_ID_KEY, DEP_PARENT_KEY, DEP_URL, DEP_STATUS_KEY);
		
		
		
		//Connecting nodes :  root to child
		addChildsToRootNodes(ritNodes, idrNodes);
		addChildsToRootNodes(appcodeNodes, ritNodes);
		return appcodeNodes.get(0);
	}
	
	private ConcurrentMap<String, Node> nodeDatabase = new ConcurrentHashMap<>();
	
	
	@GetMapping("/one")
	public void  getOne() {
		log.info("Fetching testdata");
		try {
			Node rootNode=collectData();
			nodeDatabase.put(rootNode.getId(), rootNode);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Fetching json string data {}", e);
		}
	}
	@GetMapping("/test")
	public Node  getJsonString() {
		log.info("Fetching testdata");
		try {
			return nodeDatabase.get("BZID") ;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Fetching json string data {}", e);
		}
		return null;
	}
	 @PutMapping("/edit")
	    public ResponseEntity<Void> editNode(@RequestBody Node updatedNode) {
	        nodeDatabase.put(updatedNode.getId(), updatedNode);
	        return new ResponseEntity<>(HttpStatus.OK);
	    }

	    @PostMapping("/clone")
	    public ResponseEntity<Void> cloneNode(@RequestBody Node node) {
	        Node clonedNode = Node.builder()
	                .tag(node.getTag())
	                .id("clone-" + node.getId())
	                .name(node.getName() + " (Clone)")
	                .url(node.getUrl())
	                .status(node.getStatus())
	                .data(node.getData())
	                .build();
	        nodeDatabase.put(clonedNode.getId(), clonedNode);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }

	    @PostMapping("/new")
	    public ResponseEntity<Void> createNode(@RequestBody Node node) {
	        nodeDatabase.put(node.getId(), node);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }

}
