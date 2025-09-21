package com.renter.fleetdashbaord.data.network


data class User(
    val id: String,
    val name: String,
    val speedLimit: Int,
    val notificationType: String,
    val startTime: String = "",
    val endTime: String = ""
)