package com.lyadsky.database.dao.marker

import com.lyadsky.database.DatabaseFactory.dbQuery
import com.lyadsky.database.entity.MarkerEntity
import com.lyadsky.dto.MarkerDTOReceive
import com.lyadsky.dto.MarkerDTOResponse
import com.lyadsky.dto.StatusDTOResponse
import com.mysql.cj.jdbc.Blob
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import javax.sql.rowset.serial.SerialBlob

class MarkerDAOImpl : MarkerDAO {

    override suspend fun insertMarker(markerReceive: MarkerDTOReceive): MarkerDTOResponse = dbQuery {
//        val status = StatusEntity.toDto(markerReceive)
       MarkerEntity.insert {
            it[description] = markerReceive.description
            it[city] = markerReceive.city
            it[latitude] = markerReceive.latitude
            it[longitude] = markerReceive.longitude
            it[image] = markerReceive.image
            it[userCreatedId] = markerReceive.userCreatedId
//            it[statusId] = status.id
        }
//        val marker = MarkerEntity.select(MarkerEntity.dateCreated eq markerReceive.).single()

        return@dbQuery MarkerDTOResponse(
            id = 0,
            description = markerReceive.description,
            city = markerReceive.city,
            latitude = markerReceive.latitude,
            longitude = markerReceive.longitude,
            image = markerReceive.image,
            dateCreated = "date",
            userCreatedId = markerReceive.userCreatedId,
//            status = status
        )
    }

    override suspend fun getMarker(id: Int): MarkerDTOResponse = dbQuery {
        val marker = MarkerEntity.select(MarkerEntity.rowId eq id).map { markerItem ->
//            val status = StatusEntity.select(StatusEntity.markerId eq markerItem[MarkerEntity.rowId]).map {
//                StatusDTOResponse(
//                    id = it[StatusEntity.rowId],
//                    markerId = it[StatusEntity.markerId],
//                    name = it[StatusEntity.name]
//                )
//            }.single()
            MarkerDTOResponse(
                id = markerItem[MarkerEntity.rowId],
                description = markerItem[MarkerEntity.description],
                city = markerItem[MarkerEntity.city],
                latitude = markerItem[MarkerEntity.latitude],
                longitude = markerItem[MarkerEntity.longitude],
                image = markerItem[MarkerEntity.image],
                dateCreated = markerItem[MarkerEntity.dateCreated].toString(),
                userCreatedId = markerItem[MarkerEntity.userCreatedId],
//                status = status
            )
        }.single()

        return@dbQuery marker
    }

    override suspend fun getMarkers(): List<MarkerDTOResponse> = dbQuery {
        val markers = MarkerEntity.selectAll().map { markerItem ->
//            val status = StatusEntity.select(StatusEntity.markerId eq markerItem[MarkerEntity.rowId]).map {
//                StatusDTOResponse(
//                    id = it[StatusEntity.rowId],
//                    markerId = it[StatusEntity.markerId],
//                    name = it[StatusEntity.name]
//                )
//            }.single()
            MarkerDTOResponse(
                id = markerItem[MarkerEntity.rowId],
                description = markerItem[MarkerEntity.description],
                city = markerItem[MarkerEntity.city],
                latitude = markerItem[MarkerEntity.latitude],
                longitude = markerItem[MarkerEntity.longitude],
                image = markerItem[MarkerEntity.image],
                dateCreated = markerItem[MarkerEntity.dateCreated].toString(),
                userCreatedId = markerItem[MarkerEntity.userCreatedId],
//                status = status
            )
        }
        return@dbQuery markers
    }

    override suspend fun updateMarker(id: Int, markerReceive: MarkerDTOReceive): MarkerDTOResponse = dbQuery {
        val marker = MarkerEntity.select(MarkerEntity.rowId eq id).single()
//        val status = StatusEntity.toDto(markerReceive)

        MarkerEntity.update({ MarkerEntity.rowId eq id }) {
            it[description] = markerReceive.description
            it[city] = markerReceive.city
            it[latitude] = markerReceive.latitude
            it[longitude] = markerReceive.longitude
            it[image] = markerReceive.image
            it[userCreatedId] = markerReceive.userCreatedId
//            it[statusId] = status.id
        }
        return@dbQuery MarkerDTOResponse(
            id = marker[MarkerEntity.rowId],
            description = marker[MarkerEntity.description],
            city = marker[MarkerEntity.city],
            latitude = marker[MarkerEntity.latitude],
            longitude = marker[MarkerEntity.longitude],
            image = markerReceive.image,
            dateCreated = marker[MarkerEntity.dateCreated].toString(),
            userCreatedId = marker[MarkerEntity.userCreatedId],
//            status = status
        )
    }

    override suspend fun deleteMarker(id: Int) = dbQuery {
        MarkerEntity.deleteWhere { MarkerEntity.rowId eq id }
        return@dbQuery
    }
}

//fun StatusEntity.toDto(markerReceive: MarkerDTOReceive): StatusDTOResponse =
//    StatusEntity.select(StatusEntity.name eq markerReceive.status.name).map {
//        StatusDTOResponse(
//            id = it[StatusEntity.rowId],
//            markerId = it[StatusEntity.markerId],
//            name = it[StatusEntity.name]
//        )
//    }.single()
