package com.lyadsky.features.status

import com.lyadsky.database.dao.status.StatusDAO
import com.lyadsky.dto.StatusDTOReceive
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.statusRouting() {

    val statusDAO: StatusDAO by inject()

    post("api/status") {
        val statusReceive = call.receive<StatusDTOReceive>()
        call.respond(HttpStatusCode.OK, statusDAO.insertStatus(statusReceive))
    }

    get("api/status") {
        call.respond(HttpStatusCode.OK, statusDAO.getStatuses())
    }

    get("api/status/{id?}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound, "Status not found")
        call.respond(HttpStatusCode.OK, statusDAO.getStatus(id.toInt()))
    }

    put("api/status/{id?}") {
        val statusReceive = call.receive<StatusDTOReceive>()
        val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.NotFound, "Status not found")
        call.respond(HttpStatusCode.OK, statusDAO.updateStatus(id.toInt(), statusReceive))
    }

    delete("api/status/{id?}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.NotFound, "Status not found")
        call.respond(HttpStatusCode.OK, "Status with id $id deleted")
    }
}