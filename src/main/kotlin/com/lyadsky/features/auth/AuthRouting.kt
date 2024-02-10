package com.lyadsky.features.auth

import com.lyadsky.database.dao.users.UserDAO
import com.lyadsky.dto.UserReceive
import com.lyadsky.token.TokenClaim
import com.lyadsky.token.TokenConfig
import com.lyadsky.token.TokenService
import com.lyadsky.utils.hashToSha256
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRouting(tokenConfig: TokenConfig, userDAO: UserDAO, tokenService: TokenService) {

    post("api/user/register") {
        val userReceive = call.receive<UserReceive>().apply {
            this.password = this.password.hashToSha256()
        }
        val token = tokenService.generateJWTToken(
            tokenConfig,
            TokenClaim(name = "username", value = userReceive.username),
            TokenClaim(name = "password", value = userReceive.password)
        )
        call.respond(HttpStatusCode.OK, token)
    }

    post("api/user/login") {
        val userReceive = call.receive<UserReceive>().apply {
            this.password = this.password.hashToSha256()
        }
        try {
            val user = userDAO.authUser(userReceive)
            if (userReceive.password == user.password) {
                val token = tokenService.generateJWTToken(
                    tokenConfig,
                    TokenClaim(name = "username", value = userReceive.username),
                    TokenClaim(name = "password", value = userReceive.password)
                )
                call.respond(HttpStatusCode.OK, token)
            } else {
                call.respond(HttpStatusCode.Conflict, "Invalid password")
            }
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.Conflict, "A user with this username was not found")
        }
    }

    authenticate("auth-jwt") {

        get("api/users") {
            call.respond(HttpStatusCode.OK, userDAO.getUsers())
        }

        get("api/users/{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.NotFound, "Missing user with id")
            call.respond(HttpStatusCode.OK, userDAO.getUser(id.toInt()))
        }

        put("api/users/{id?}") {
            val id =
                call.parameters["id"] ?: return@put call.respond(HttpStatusCode.NotFound, "Missing user with this id")
            val user = call.receive<UserReceive>().apply {
                this.password = this.password.hashToSha256()
            }
            userDAO.updateUser(id.toInt(), user)
            call.respond(HttpStatusCode.OK, "User updated")
        }

        delete("api/users/{id?}") {
            val id =
                call.parameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.NotFound,
                    "Missing user with this id"
                )
            userDAO.deleteUser(id.toInt())
            call.respond(HttpStatusCode.OK, "User deleted")
        }
    }
}

