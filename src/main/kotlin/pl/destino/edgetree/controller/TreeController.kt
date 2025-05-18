package pl.destino.edgetree.controller

import org.springframework.web.bind.annotation.*
import pl.destino.edgetree.api.model.Tree
import pl.destino.edgetree.service.TreeService

@RestController
@RequestMapping("/tree")
class TreeController(private val service: TreeService) {

    @GetMapping
    fun getTree(@RequestParam("root") root: Int): Tree {
        return service.calculateTreeFromRoot(root);
    }

}