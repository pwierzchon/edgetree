package pl.destino.edgetree.service

import pl.destino.edgetree.api.model.Tree

interface TreeService {
    fun calculateTreeFromRoot(root: Int): Tree
}