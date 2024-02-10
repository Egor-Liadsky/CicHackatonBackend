package com.lyadsky.plugins

import com.lyadsky.database.dao.users.UserDAOImpl
import com.lyadsky.features.auth.authRouting
import com.lyadsky.features.marker.markerRouting
import com.lyadsky.token.TokenConfig
import com.lyadsky.token.TokenServiceImpl
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    tokenConfig: TokenConfig, userDAO: UserDAOImpl,
    tokenService: TokenServiceImpl,
) {

    routing {
        authRouting(tokenConfig, userDAO, tokenService)
        markerRouting()
    }
}
