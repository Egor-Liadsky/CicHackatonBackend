package com.lyadsky.database.entity

import org.jetbrains.exposed.sql.Table

object StatusEntity : Table("status") {
    val rowId = integer("id_status").autoIncrement()
    val name = varchar("name", 50)

    override val primaryKey = PrimaryKey(rowId, name = "id_status")
}