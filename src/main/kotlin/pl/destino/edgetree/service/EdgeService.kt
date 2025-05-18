package pl.destino.edgetree.service

import pl.destino.edgetree.api.model.Edge

interface EdgeService {

    fun getEdges(): List<Edge>
    fun addEdge(edge: Edge): Edge?
    fun deleteEdge(edge: Edge): String
}