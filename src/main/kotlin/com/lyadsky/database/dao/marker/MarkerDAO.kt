package com.lyadsky.database.dao.marker

import com.lyadsky.dto.MarkerDTOReceive
import com.lyadsky.dto.MarkerDTOResponse

interface MarkerDAO {

    suspend fun insertMarker(markerReceive: MarkerDTOReceive): MarkerDTOResponse

    suspend fun getMarker(id: Int): MarkerDTOResponse

    suspend fun getMarkers(): List<MarkerDTOResponse>

    suspend fun updateMarker(id: Int, markerReceive: MarkerDTOReceive): MarkerDTOResponse

    suspend fun deleteMarker(id: Int)

    suspend fun updateIsRepairStatus(id: Int, repair: Boolean): Boolean
}