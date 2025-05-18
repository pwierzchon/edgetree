package pl.destino.edgetree.validation

import pl.destino.edgetree.api.model.Edge

fun validateEdge(edge: Edge) {

    if (edge.fromId == edge.toId) {
        throw IllegalArgumentException("Fields fromId and toId must not be equal.")
    }

}