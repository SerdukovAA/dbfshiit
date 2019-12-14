package com.github.dbf

import tornadofx.Controller


class SaveAsButtonController: Controller() {
    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }
}