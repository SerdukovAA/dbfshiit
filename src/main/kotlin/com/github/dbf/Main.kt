package com.github.dbf

import tornadofx.*

fun main(args: Array<String>) {
    launch<Main>(args)
}

class Main: App(MyView::class)

class Column(val title: String, val value: String)
class TableInfoView(val columns: List<Column>)
class TableInfoViewEvent(val tableInfoView: TableInfoView) : FXEvent()

class MyView: View() {

    val openButtonController: OpenButtonController by inject()
    val saveAsButtonController: SaveAsButtonController by inject()

    override val root = borderpane {

        top = hbox {
            button("Open"){
                action {
                    openButtonController.writeToDb("Hello")
                }
            }
            button("Save As"){
                action {
                    saveAsButtonController.writeToDb("Hello")
                }
            }
        }

        center = hbox {
         label("Пусто")
        }

        subscribe<TableInfoViewEvent> { event ->
            center = tableview<Column> {
                smartResize()
                event.tableInfoView.columns.forEach{
                    readonlyColumn(it.title, Column::value)
                }
                items = event.tableInfoView.columns.observable();
            }
        }
     }
    }


