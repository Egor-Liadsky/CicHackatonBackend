package com.lyadsky.database.dao.marker

import com.lyadsky.database.DatabaseFactory.dbQuery
import com.lyadsky.database.entity.MarkerEntity
import com.lyadsky.database.entity.UsersEntity
import com.lyadsky.dto.MarkerDTOReceive
import com.lyadsky.dto.MarkerDTOResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.Date
import org.jetbrains.exposed.sql.javatime.date
import org.slf4j.Marker

class MarkerDAOImpl : MarkerDAO {

    override suspend fun insertMarker(markerReceive: MarkerDTOReceive): MarkerDTOResponse = dbQuery {
        MarkerEntity.insert {
            it[description] = markerReceive.description
            it[city] = markerReceive.city
            it[latitude] = markerReceive.latitude
            it[longitude] = markerReceive.longitude
            it[image] = markerReceive.image
            it[userCreatedId] = markerReceive.userCreatedId
        }

        val marker = MarkerEntity.select(MarkerEntity.description eq markerReceive.description).single()

        return@dbQuery MarkerDTOResponse(
            id = marker[MarkerEntity.rowId],
            description = marker[MarkerEntity.description],
            city = marker[MarkerEntity.city],
            latitude = marker[MarkerEntity.latitude],
            longitude = marker[MarkerEntity.longitude],
            image = marker[MarkerEntity.image],
            dateCreated = marker[MarkerEntity.dateCreated].toString(),
            userCreatedId = marker[MarkerEntity.userCreatedId]
        )
    }

    override suspend fun getMarker(id: Int): MarkerDTOResponse = dbQuery {
        val marker = MarkerEntity.select(MarkerEntity.rowId eq id).single()
        return@dbQuery MarkerDTOResponse(
            id = marker[MarkerEntity.rowId],
            description = marker[MarkerEntity.description],
            city = marker[MarkerEntity.city],
            latitude = marker[MarkerEntity.latitude],
            longitude = marker[MarkerEntity.longitude],
            image = marker[MarkerEntity.image],
            dateCreated = marker[MarkerEntity.dateCreated].toString(),
            userCreatedId = marker[MarkerEntity.userCreatedId]
        )
    }

    override suspend fun getMarkers(): List<MarkerDTOResponse> = dbQuery {
        val markers = MarkerEntity.selectAll().map {
            MarkerDTOResponse(
                id = it[MarkerEntity.rowId],
                description = it[MarkerEntity.description],
                city = it[MarkerEntity.city],
                latitude = it[MarkerEntity.latitude],
                longitude = it[MarkerEntity.longitude],
                image = it[MarkerEntity.image],
                dateCreated = it[MarkerEntity.dateCreated].toString(),
                userCreatedId = it[MarkerEntity.userCreatedId]
            )
        }
        return@dbQuery markers
    }

    override suspend fun updateMarker(id: Int, markerReceive: MarkerDTOReceive): MarkerDTOResponse = dbQuery {
        val marker = MarkerEntity.select(MarkerEntity.rowId eq id).single()

        MarkerEntity.update({ MarkerEntity.rowId eq id }) {
            it[description] = markerReceive.description
            it[city] = markerReceive.city
            it[latitude] = markerReceive.latitude
            it[longitude] = markerReceive.longitude
            it[image] = markerReceive.image
            it[userCreatedId] = markerReceive.userCreatedId
        }
        return@dbQuery MarkerDTOResponse(
            id = marker[MarkerEntity.rowId],
            description = marker[MarkerEntity.description],
            city = marker[MarkerEntity.city],
            latitude = marker[MarkerEntity.latitude],
            longitude = marker[MarkerEntity.longitude],
            image = marker[MarkerEntity.image],
            dateCreated = marker[MarkerEntity.dateCreated].toString(),
            userCreatedId = marker[MarkerEntity.userCreatedId]
        )
    } // TODO create mapper

    override suspend fun deleteMarker(id: Int) = dbQuery {
        MarkerEntity.deleteWhere { MarkerEntity.rowId eq id }
        return@dbQuery
    }
}
