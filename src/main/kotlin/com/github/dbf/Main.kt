package com.github.dbf

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File
import java.nio.charset.Charset


fun main(args: Array<String>) {
    launch<Main>(args)
}

val avalableCharsets = FXCollections.observableArrayList(Charset.availableCharsets().keys)

class Main : App(MyView::class)


class MyView : View() {

    val tableController: TableController by inject()

    override val root = borderpane {
        top = hbox {
            button("Open file") {
                action {
                    val dbfFiles: List<File> = chooseFile(
                        "Single + non/block",
                               arrayOf(
                                   FileChooser.ExtensionFilter("DBF files (*.dbf)", "*.dbf", "*.DBF")
                               )
                        ,FileChooserMode.Single
                    )
                    tableController.openDbf(dbfFiles)
                }
            }
            combobox(tableController.selectedCharset, avalableCharsets)
        }

        center = hbox {
            label("Пусто")
        }

        subscribe<TableInfoViewEvent> { event ->
            center {
                tableview(event.rows) {
                    isEditable = true
                    event.rows.first().keys.forEach{
                        column(it, String::class) {
                            value { row ->
                                SimpleStringProperty(row.value.get(it)!!.stringValue)
                            }
                        }.makeEditable()
                    }
                }
            }

        }
        setMinSize(500.toDouble(), 500.toDouble())
    }

}

