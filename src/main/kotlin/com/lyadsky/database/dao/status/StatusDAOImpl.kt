package com.lyadsky.database.dao.status

import com.lyadsky.database.DatabaseFactory.dbQuery
import com.lyadsky.database.entity.StatusEntity
import com.lyadsky.database.entity.UsersEntity
import com.lyadsky.dto.StatusDTOReceive
import com.lyadsky.dto.StatusDTOResponse
import com.lyadsky.dto.UserResponse
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StatusDAOImpl : StatusDAO {
    override suspend fun insertStatus(statusDTOReceive: StatusDTOReceive): StatusDTOResponse = dbQuery {
        StatusEntity.insert {
            it[name] = statusDTOReceive.name
        }
        val status = StatusEntity.select(StatusEntity.name eq statusDTOReceive.name).single()
        return@dbQuery StatusDTOResponse(
            id = status[StatusEntity.rowId],
            name = status[StatusEntity.name]
        )
    }

    override suspend fun getStatus(id: Int): StatusDTOResponse = dbQuery {
        val status = StatusEntity.select(StatusEntity.rowId eq id).map {
            StatusDTOResponse(
                id = it[StatusEntity.rowId],
                name = it[StatusEntity.name]
            )
        }.single()
        return@dbQuery status
    }

    override suspend fun getStatuses(): List<StatusDTOResponse> = dbQuery {
        val statuses = StatusEntity.selectAll().map {
            StatusDTOResponse(
                id = it[StatusEntity.rowId],
                name = it[StatusEntity.name]
            )
        }
        return@dbQuery statuses
    }

    override suspend fun updateStatus(id: Int, statusDTOReceive: StatusDTOReceive): StatusDTOResponse = dbQuery {
        val status = StatusEntity.select(StatusEntity.rowId eq id).single()
        StatusEntity.update({ StatusEntity.rowId eq id }) {
            it[name] = statusDTOReceive.name
        }
        return@dbQuery StatusDTOResponse(
            id = status[StatusEntity.rowId],
            name = status[StatusEntity.name]
        )
    }

    override suspend fun deleteStatus(id: Int) = dbQuery {
        StatusEntity.deleteWhere { StatusEntity.rowId eq id }
        return@dbQuery
    }
}