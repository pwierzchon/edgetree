package pl.destino.edgetree.service

import org.springframework.stereotype.Service
import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.db.tables.records.EdgeRecord
import pl.destino.edgetree.exception.DuplicateEdgeException
import pl.destino.edgetree.exception.EdgeNotFoundException
import pl.destino.edgetree.repository.EdgeRepository

@Service
class EdgeServiceImpl(private val repository: EdgeRepository): EdgeService {
    override fun getEdges(): List<Edge> {
        return repository.getEdges().stream()
            .map { edgeRecord -> Edge(edgeRecord.fromId!!.toInt(), edgeRecord.toId!!.toInt()) }
            .toList()
    }

    override fun addEdge(edge: Edge): Edge? {
        if (!repository.findEdge(edge)) {
            repository.saveEdge(EdgeRecord(edge.fromId, edge.toId))
            return edge
        } else {
            throw DuplicateEdgeException("$edge already exists in the database!")
        }
    }

    override fun deleteEdge(edge: Edge): String {
        if (repository.findEdge(edge)) {
            repository.deleteEdge(EdgeRecord(edge.fromId, edge.toId))
            return "$edge deleted successfuly."
        } else {
            throw EdgeNotFoundException("$edge not found in the database.")
        }
    }
}