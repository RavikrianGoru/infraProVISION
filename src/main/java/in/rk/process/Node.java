package in.rk.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {

	private String tag;
	private String id;
	private String name;
	private String url;
	private String status;
	private Map<String, String> data;
	@Builder.Default
	private List<Node> childs = new ArrayList<>();
}