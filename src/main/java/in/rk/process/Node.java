package in.rk.process;

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
	private String id;
	private String url;
	private String status;
	private Map<String, String> data;
	private List<Node> childs;
}
