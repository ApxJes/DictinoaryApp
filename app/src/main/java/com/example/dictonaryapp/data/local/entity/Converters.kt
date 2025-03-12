package com.example.dictonaryapp.data.local.entity

import androidx.room.TypeConverter
import com.example.dictonaryapp.data.util.GsonParser
import com.example.dictonaryapp.domain.model.Meaning
import com.google.gson.reflect.TypeToken

class Converters(
    private val jsonParer: GsonParser
) {

    @TypeConverter
    fun fromMeaningJson(json: String): List<Meaning> {
        return jsonParer.fromJson<ArrayList<Meaning>>(
            json,
            object: TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(meaning: List<Meaning>): String {
        return jsonParer.toJson(
            meaning,
            object: TypeToken<ArrayList<Meaning>>(){}.type
        ) ?: "[]"
    }

}