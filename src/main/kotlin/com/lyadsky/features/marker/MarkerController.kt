package com.lyadsky.features.marker

import com.lyadsky.database.dao.marker.MarkerDAO
import com.lyadsky.dto.MarkerDTOReceive
import com.lyadsky.dto.MarkerDTOResponse

class MarkerController(private val markerDAO: MarkerDAO) {

    suspend fun insertMarker(markerDTOReceive: MarkerDTOReceive): MarkerDTOResponse {
        return markerDAO.insertMarker(markerDTOReceive)
    }

    suspend fun getMarker(id: Int): MarkerDTOResponse {
        return markerDAO.getMarker(id)
    }

    suspend fun getMarkers(): List<MarkerDTOResponse> {
        return markerDAO.getMarkers()
    }

    suspend fun updateMarker(id: Int, markerDTOReceive: MarkerDTOReceive): MarkerDTOResponse {
        return markerDAO.updateMarker(id = id, markerReceive = markerDTOReceive)
    }

    suspend fun deleteMarker(id: Int): String {
        markerDAO.deleteMarker(id)
        return "Marker with id $id deleted"
    }

    suspend fun updateIsRepairStatus(id: Int, repair: Boolean): Boolean {
        return markerDAO.updateIsRepairStatus(id, repair)
    }
}