package in.rk.sbd3app.infraProVISION.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rk.process.Node;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {
    private final Map<String, Node> inMemoryDB = new HashMap<>();

    @GetMapping("/{id}")
    public Node getNode(@PathVariable String id) {
        return inMemoryDB.get(id);
    }

    @PutMapping("/edit/{id}")
    public Node editNode(@PathVariable String id, @RequestBody Node node) {
        inMemoryDB.put(id, node);
        return node;
    }

    @PostMapping("/clone/{id}")
    public Node cloneNode(@PathVariable String id) {
        Node originalNode = inMemoryDB.get(id);
        if (originalNode != null) {
            Node clonedNode = new Node("TAG", UUID.randomUUID().toString(), originalNode.getName(), originalNode.getUrl(), originalNode.getStatus(), new HashMap<>(originalNode.getData()), new ArrayList<>(originalNode.getChilds()));
            inMemoryDB.put(clonedNode.getId(), clonedNode);
            return clonedNode;
        }
        return null;
    }

    @PostMapping("/new")
    public Node createNode(@RequestBody Node node) {
        node.setId(UUID.randomUUID().toString());
        inMemoryDB.put(node.getId(), node);
        return node;
    }

    @GetMapping("/test")
    public Node testData() {
        // Provide sample data as needed
        Node sampleNode = new Node("APPCODE", "BZID", "MOT", "https://www.appcode.com", "Active", Map.of("Application Code", "BZID", "data2", "some data-2", "LOBT", "MOT", "Status", "Active", "platform", "Windows", "data1", "some data-1"), new ArrayList<>());
        inMemoryDB.put(sampleNode.getId(), sampleNode);
        return sampleNode;
    }
}
