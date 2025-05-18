package pl.destino.edgetree.controller

import org.springframework.web.bind.annotation.*
import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.service.EdgeService
import pl.destino.edgetree.validation.validateEdge

@RestController
@RequestMapping("/edge")
class EdgeController(private val service: EdgeService) {

    @PostMapping
    fun addEdge(@RequestBody edge: Edge): Edge? {
        validateEdge(edge)
        return service.addEdge(edge)
    }

    @DeleteMapping
    fun deleteEdge(@RequestBody edge: Edge): String {
        validateEdge(edge)
        return service.deleteEdge(edge)
    }

}