package com.lyadsky

import com.lyadsky.database.DatabaseFactory
import com.lyadsky.database.dao.users.UserDAOImpl
import com.lyadsky.plugins.*
import com.lyadsky.token.TokenConfig
import com.lyadsky.token.TokenServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureCors()
    configureKoin()

    DatabaseFactory.init()

    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = environment.config.property("jwt.secret").getString(),
        realm = environment.config.property("jwt.realm").getString()
    )

    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig, UserDAOImpl(), TokenServiceImpl())
}


