package com.harnet.dogbreeds.util

import android.content.Context
import java.io.*
import java.nio.charset.StandardCharsets

class JsonFileReader {
    @Throws(IOException::class)
    fun readJsonFile(context: Context, jsonFile: Int): String {
        val `IS` = context.resources.openRawResource(jsonFile)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(`IS`, StandardCharsets.UTF_8))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            `IS`.close()
        }
        return writer.toString()
    }
}