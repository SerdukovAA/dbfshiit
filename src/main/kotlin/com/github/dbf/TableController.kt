package com.github.dbf

import com.linuxense.javadbf.DBFReader
import com.linuxense.javadbf.DBFRow
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.Controller
import tornadofx.FXEvent
import tornadofx.observable
import tornadofx.onChange
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset


class Value(val stringValue: String?)

class TableInfoViewEvent(val rows:SimpleListProperty<LinkedHashMap<String, Value>>) : FXEvent()

class TableController: Controller() {

    var currentDbfFile: File? = null
    var selectedCharset = SimpleStringProperty(Charset.defaultCharset().name())
    var currentCharset: Charset = Charset.defaultCharset()

    init{
        selectedCharset.onChange {
            currentCharset = Charset.forName(selectedCharset.value)
            refreshTable()
        }
    }

    fun openDbf(dbfFiles: List<File>) {
        currentDbfFile = dbfFiles[0];
        refreshTable()
    }

    fun refreshTable() {
        if(currentDbfFile == null){
            return
        }
        var rowsForView: SimpleListProperty<LinkedHashMap<String, Value>> = readCurrentFile();// перечитать открытый файл
        fire(TableInfoViewEvent(rowsForView))
    }

    private fun readCurrentFile(): SimpleListProperty<LinkedHashMap<String, Value>> {
        var rows:SimpleListProperty<LinkedHashMap<String, Value>> = SimpleListProperty()
        var list = ArrayList<LinkedHashMap<String, Value>>()
        val reader = DBFReader(FileInputStream(currentDbfFile), currentCharset)
        reader.use {
            val numberOfFields = reader.fieldCount
            val fieldNames = HashSet<String>()
            for (i in 0 until numberOfFields) {
                fieldNames.add(reader.getField(i).name)
            }

            var map: LinkedHashMap<String, Value>
            var row: DBFRow? = reader.nextRow()
            while (row != null) {
                map = LinkedHashMap()
                fieldNames.forEach {
                    map.put(it, Value(row!!.getString(it)))
                }
                row = reader.nextRow()
                list.add(map)
            }
        }
        rows.value = list.observable()
        return rows;
    }

}