package com.example.nutrition_tracker.data

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class CSV(context: Context, fileName: String, delimiter: Char = ','): Iterable<HashMap<String, String>> {

    val reader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
    val header = reader.readLine().splitSafe(delimiter)

    val entries = ArrayList<HashMap<String, String>>()

    val size get() = entries.size

    init {
        var line: String?
        println("$#% header = ${header.size} :: $header")
        while (reader.readLine().also { line = it } != null) {
            val map = HashMap<String, String>()
            val values = line!!.splitSafe(delimiter)
            println("$#% row = ${values.size} :: ${values.joinToString ("::")}")
            values.forEachIndexed { i, v -> map[header[i]] = v }
            entries.add(map)
        }
    }

    operator fun get(index: Int) = entries[index]

    override fun iterator() = entries.iterator()

    private fun String.splitSafe(delimiter: Char): List<String> {
        val list = ArrayList<String>()
        var escape = false
        var quote = false
        var from = 0
        for ((i, c) in this.withIndex()) {
            if (c == '\"' && !escape) quote = !quote
            if (c == delimiter && !escape && !quote) {
                list.add(substring(from, i).trim('\"'))
                from = i + 1
            }
            escape = c == '\\' && !escape
        }
        if (from < length) list.add(substring(from).trim('\"'))
        else list.add("")
        return list
    }
}