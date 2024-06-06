package in.rk.sbd3app.infraProVISION.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rk.sbd3app.infraProVISION.services.FileReadService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class DataController {

	@Autowired
	private FileReadService jsonFileReadServiceImpl;
	
	
	@GetMapping("/data")
	public List<Integer> getData() {
		log.info("Fetching data");
		return Arrays.asList(30, 86, 168, 281, 303, 365);
	}
	@GetMapping("/json")
	public String  getJsonString() {
		log.info("Fetching json string data");
		try {
			return jsonFileReadServiceImpl.readFile() ;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while Fetching json string data {}", e);
		}
		return null;
	}

}
