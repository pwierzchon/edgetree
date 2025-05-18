package pl.destino.edgetree.service

import org.springframework.stereotype.Service
import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.api.model.Tree
import pl.destino.edgetree.db.tables.records.EdgeRecord
import pl.destino.edgetree.exception.RootNotFoundException
import pl.destino.edgetree.repository.EdgeRepository
import pl.destino.edgetree.utils.mapEdgeFromDbObject
import pl.destino.edgetree.utils.reverseEdge

@Service
class TreeServiceImpl(private val repository: EdgeRepository): TreeService {

    override fun calculateTreeFromRoot(root: Int): Tree {

        val treeEdges = ArrayList<Edge>()
        val inspectedNodes = HashSet<Int>()
        val result = Tree(root, treeEdges)
        val edgesFromRoot = getEdgesFromRoot(root)

        inspectedNodes.add(root)
        treeEdges.addAll(edgesFromRoot)
        
        var nodesToInspect = getNodesToInspect(edgesFromRoot)
                
        while (nodesToInspect.size > 0) {
            var childEdges = getEdgesFromNodes(nodesToInspect, inspectedNodes)
            treeEdges.addAll(childEdges)
            inspectedNodes.addAll(nodesToInspect);
            nodesToInspect = getNodesToInspect(childEdges)
        }
        
        return result
    }

    private fun getReversalEdge(root: Int): Edge? {
        val edgesReverse = repository.getEdgesReverse(root)
        if (edgesReverse == null) {
            return null;
        }
        return reverseEdge(mapEdgeFromDbObject(edgesReverse))
    }

    private fun getReversalEdges(nodesToInspect: List<Int>, inspectedNodes: HashSet<Int>): List<Edge> {
        return repository.getEdgesReverse(nodesToInspect, inspectedNodes)
            .map { edgeRecord: EdgeRecord -> reverseEdge(mapEdgeFromDbObject(edgeRecord)) }
            .toList()
    }

    private fun getEdgesFromNodes(nodesToInspect: List<Int>, inspectedNodes: HashSet<Int>): List<Edge> {
        val edges = repository.getEdges(nodesToInspect, inspectedNodes)
            .map { edgeRecord: EdgeRecord -> mapEdgeFromDbObject(edgeRecord) }
            .toMutableList()
        edges.addAll(getReversalEdges(nodesToInspect, inspectedNodes))
        return edges
    }

    private fun getNodesToInspect(edges: List<Edge>): List<Int> {
        return edges.stream()
            .map { edge: Edge -> edge.toId }
            .toList()
    }

    private fun getEdgesFromRoot(from: Int): List<Edge> {
        val edges = repository.getEdges(from)
            .map { edgeRecord: EdgeRecord -> mapEdgeFromDbObject(edgeRecord) }
            .toMutableList()

        val reversalEdge = getReversalEdge(from)
        if (reversalEdge != null) {
            edges.add(reversalEdge)
        }

        if (edges.size == 0) {
            throw RootNotFoundException("Root $from not found in the database.")
        }

        return edges.toList()
    }
}