package com.example.myassssmentapplication

import java.io.Serializable

data class Entity(
    val properties: Map<String, Any> = emptyMap(),
    val description: String? = null
) : Serializable
