package com.github.dbf

import javafx.collections.FXCollections
import tornadofx.Controller
import java.util.*


class OpenButtonController: Controller() {

    var values = FXCollections.observableArrayList(listOf(
        Column("Joe Thompson", UUID.randomUUID().toString()),
        Column("Joe Thompson", UUID.randomUUID().toString()),
        Column("Joe Thompson", UUID.randomUUID().toString()),
        Column("Joe Thompson", UUID.randomUUID().toString())
    ))

    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
        values.add(Column("123", UUID.randomUUID().toString()))
        fire(TableInfoViewEvent(TableInfoView(values)))
    }


}