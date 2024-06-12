package in.rk.sbd3app.infraProVISION.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import in.rk.process.Node;

@Service
public class NodeService {

    private final List<Node> nodeRepository = new ArrayList<>();

    public Node getNodeById(String id) {
        return nodeRepository.stream().filter(node -> node.getId().equals(id)).findFirst().orElse(null);
    }

    public Node saveNode(Node node) {
        nodeRepository.add(node);
        return node;
    }

    public Node updateNode(Node updatedNode) {
        Optional<Node> existingNodeOpt = nodeRepository.stream().filter(node -> node.getId().equals(updatedNode.getId())).findFirst();
        if (existingNodeOpt.isPresent()) {
            Node existingNode = existingNodeOpt.get();
            existingNode.setTag(updatedNode.getTag());
            existingNode.setName(updatedNode.getName());
            existingNode.setUrl(updatedNode.getUrl());
            existingNode.setStatus(updatedNode.getStatus());
            existingNode.setData(updatedNode.getData());
            return existingNode;
        }
        return null;
    }

    public void deleteNodeById(String id) {
        nodeRepository.removeIf(node -> node.getId().equals(id));
    }

    public List<Node> getAllNodes() {
        return nodeRepository;
    }
}
