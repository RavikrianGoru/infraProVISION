package in.rk.sbd3app.infraProVISION.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rk.process.Node;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class TestController {

	private static final String APPCODE_ID_KEY = "Application Code";
	private static final String APPCODE_PARENT_KEY = "LOBT";
	private static final String APPCODE_STATUS_KEY = "Status";
	private static final String APPCODE_URL = "https://www.appcode.com";
	
	private static final String RIT_ID_KEY = "Case ID";
	private static final String RIT_PARENT_KEY = "AppCode";
	private static final String RIT_STATUS_KEY = "Status";
	private static final String RIT_URL = "https://www.rit.com";
	
	private String convertNumber(String input) {
		try {
			double number = Double.parseDouble(input);
			return String.format("%.0f", number);
		} catch (Exception e) {
			return input;
		}
	}
	private List<Node> processAsNodeList(List<Map<String, String>> response, String idKey, String nameKey,
			String url, String statusKey) {
		return response.stream().map(element -> new Node(convertNumber(element.get(idKey)),
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
		List<Node> appcodeNodes = processAsNodeList(appcodeResponse, APPCODE_ID_KEY, APPCODE_PARENT_KEY, APPCODE_URL,APPCODE_STATUS_KEY);
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

		List<Node> ritNodes = processAsNodeList(ritResponse, RIT_ID_KEY, RIT_PARENT_KEY, RIT_URL, RIT_STATUS_KEY);

		addChildsToRootNodes(appcodeNodes, ritNodes);
		
		return appcodeNodes.get(0);
	}
	
	@GetMapping("/test")
	public Node  getJsonString() {
		log.info("Fetching testdata");
		try {
			return collectData() ;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while Fetching json string data {}", e);
		}
		return null;
	}
}
