package in.rk.sbd3app.infraProVISION.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.rk.process.Node;
import in.rk.sbd3app.infraProVISION.services.NodeService;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/{id}")
    public Node getNode(@PathVariable String id) {
        return nodeService.getNodeById(id);
    }

    @PostMapping
    public Node createNode(@RequestBody Node node) {
        return nodeService.saveNode(node);
    }

    @PutMapping("/{id}")
    public Node updateNode(@PathVariable String id, @RequestBody Node node) {
        node.setId(id);
        return nodeService.updateNode(node);
    }

    @DeleteMapping("/{id}")
    public void deleteNode(@PathVariable String id) {
        nodeService.deleteNodeById(id);
    }

    @GetMapping
    public List<Node> getAllNodes() {
        return nodeService.getAllNodes();
    }
}
