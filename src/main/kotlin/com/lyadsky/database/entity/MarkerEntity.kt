package com.lyadsky.database.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object MarkerEntity : Table("markers") {
    val rowId = integer("id_marker").autoIncrement()
    val description = varchar("description", 1000)
    val city = varchar("city", 80)
    val latitude = varchar("latitude", 50)
    val longitude = varchar("longitude", 50)
    val image = text("image")
    val dateCreated = datetime("dateCreated").defaultExpression(CurrentDateTime)
    val userCreatedId = varchar("userCreatedId", 80)
    val isRepair = bool("is_repair")
    val isValidate = bool("is_validate")
    val statusId = integer("id_status").references(StatusEntity.rowId)

    override val primaryKey = PrimaryKey(rowId, name = "id_marker")
}