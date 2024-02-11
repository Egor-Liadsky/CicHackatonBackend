package com.lyadsky.features.marker

import com.lyadsky.dto.MarkerDTOReceive
import com.lyadsky.dto.Repair
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.markerRouting() {

    val markerController: MarkerController by inject()

    post("api/markers") {
        val markerReceive = call.receive<MarkerDTOReceive>()
        val response = markerController.insertMarker(markerReceive)
        call.respond(HttpStatusCode.OK, response)
    }

    get("api/markers/{id?}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound, "Marker not found")
        call.respond(HttpStatusCode.OK, markerController.getMarker(id.toInt()))
    }

    get("api/markers") {
        call.respond(HttpStatusCode.OK, markerController.getMarkers())
    }

    put("api/markers/{id?}") {
        val markerReceive = call.receive<MarkerDTOReceive>()
        val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.NotFound, "Marker not found")
        call.respond(HttpStatusCode.OK, markerController.updateMarker(id.toInt(), markerReceive))
    }

    delete("api/markers/{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.NotFound, "Marker not found")
        call.respond(HttpStatusCode.OK, markerController.deleteMarker(id.toInt()))
    }

    put("api/markers/repair/{id?}&{repair?}") {
        val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.NotFound, "Marker not found")
        val repair = call.parameters["repair"]
//        val receive = call.receive<Repair>()
//        call.respond(HttpStatusCode.OK, markerController.updateIsRepairStatus(receive.id, receive.status))
        call.respond(HttpStatusCode.OK, markerController.updateIsRepairStatus(id.toInt(), repair.toBoolean()))
    }
}