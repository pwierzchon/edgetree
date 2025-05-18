package pl.destino.edgetree.repository

import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import pl.destino.edgetree.api.model.Edge
import pl.destino.edgetree.db.tables.records.EdgeRecord
import pl.destino.edgetree.db.tables.references.EDGE

@Repository
class EdgeRepositoryImpl(@Autowired private val jooq: DSLContext): EdgeRepository {

    override fun getEdges(): List<EdgeRecord> {
        return jooq.selectFrom(EDGE).toList()

    }

    override fun getEdges(nodes: List<Int>): List<EdgeRecord> {
        return jooq.selectFrom(EDGE)
            .where(EDGE.FROM_ID.`in`(nodes))
            .toList()
    }

    override fun getEdges(nodes: List<Int>, nodesToExclude: HashSet<Int>): List<EdgeRecord> {
        return jooq.selectFrom(EDGE)
            .where(EDGE.FROM_ID.`in`(nodes))
            .and(EDGE.TO_ID.notIn(nodesToExclude))
            .toList()
    }

    override fun getEdges(fromId: Int): List<EdgeRecord> {
        return jooq.selectFrom(EDGE)
            .where(EDGE.FROM_ID.eq(fromId))
            .toList()

    }

    override fun saveEdge(edgeRecord: EdgeRecord) {
        jooq.insertInto(EDGE, EDGE.FROM_ID, EDGE.TO_ID)
            .values(edgeRecord.fromId, edgeRecord.toId)
            .execute()
    }

    override fun findEdge(edge: Edge): Boolean {
        return jooq.fetchExists(EDGE,
            EDGE.FROM_ID.eq(edge.fromId),
            EDGE.TO_ID.eq(edge.toId))
    }

    override fun deleteEdge(edgeRecord: EdgeRecord) {
        jooq.delete(EDGE)
            .where(EDGE.FROM_ID.eq(edgeRecord.fromId))
            .and(EDGE.TO_ID.eq(edgeRecord.toId))
            .execute()
    }

    override fun getEdgesReverse(fromId: Int): EdgeRecord? {

        return jooq.selectFrom(EDGE)
            .where(EDGE.TO_ID.eq(fromId))
            .fetchAny()
    }

    override fun getEdgesReverse(nodes: List<Int>, nodesToExclude: HashSet<Int>): List<EdgeRecord> {
        return jooq.selectFrom(EDGE)
            .where(EDGE.TO_ID.`in`(nodes))
            .and(EDGE.FROM_ID.notIn(nodesToExclude))
            .toList()
    }

    override fun findCircularReference(edge: Edge): Boolean {
        return jooq.fetchValue(exists(selectOne().from(EDGE).where(EDGE.TO_ID.eq(edge.toId))))
    }

}