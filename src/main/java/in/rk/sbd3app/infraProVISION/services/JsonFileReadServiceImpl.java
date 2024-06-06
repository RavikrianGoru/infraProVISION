package in.rk.sbd3app.infraProVISION.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component("jsonFileReader")
public class JsonFileReadServiceImpl implements FileReadService {

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${json.graph.static.file.name}")
	String fileName;

	@Override
	public String readFile() throws IOException {

		//log.info("File Name: {}", fileName);
		// Load the resource from the classpath
		Resource resource = resourceLoader.getResource("classpath:" + fileName);
		String jsonString = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		//log.info("json string data as string :{}", jsonString);
		// Read and return the raw JSON file content as a string
		return jsonString;
	}

}
