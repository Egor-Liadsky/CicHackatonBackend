package com.lyadsky.database.dao.status

import com.lyadsky.dto.StatusDTOReceive
import com.lyadsky.dto.StatusDTOResponse

interface StatusDAO {

    suspend fun insertStatus(statusDTOReceive: StatusDTOReceive): StatusDTOResponse

    suspend fun getStatus(id: Int): StatusDTOResponse

    suspend fun getStatuses(): List<StatusDTOResponse>

    suspend fun updateStatus(id: Int, statusDTOReceive: StatusDTOReceive): StatusDTOResponse

    suspend fun deleteStatus(id: Int)
}