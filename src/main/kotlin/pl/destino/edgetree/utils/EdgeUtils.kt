package pl.destino.edgetree.utils

import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.db.tables.records.EdgeRecord

fun reverseEdge(edge: Edge): Edge {
    return Edge(edge.toId, edge.fromId)
}

fun mapEdgeFromDbObject(edgeRecord: EdgeRecord): Edge {
    return Edge(edgeRecord.fromId!!, edgeRecord.toId!!)
}