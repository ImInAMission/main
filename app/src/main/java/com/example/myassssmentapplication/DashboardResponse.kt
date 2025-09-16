package com.example.myassssmentapplication

import java.io.Serializable

data class DashboardResponse(
    val entities: List<Map<String, Any>> = emptyList(),
    val entityTotal: Int = 0
) : Serializable
