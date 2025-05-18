package pl.destino.edgetree.repository

import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.db.tables.records.EdgeRecord

interface EdgeRepository {

    fun getEdges(): List<EdgeRecord>
    fun getEdges(nodes: List<Int>): List<EdgeRecord>
    fun getEdges(nodes: List<Int>, nodesToExclude: HashSet<Int>): List<EdgeRecord>
    fun saveEdge(edgeRecord: EdgeRecord)
    fun findEdge(edgeRecord: Edge): Boolean
    fun deleteEdge(edgeRecord: EdgeRecord)
    fun getEdges(fromId: Int): List<EdgeRecord>
    fun getEdgesReverse(fromId: Int): EdgeRecord?
    fun getEdgesReverse(nodes: List<Int>, nodesToExclude: HashSet<Int>): List<EdgeRecord>
    fun findCircularReference(edge: Edge): Boolean

}