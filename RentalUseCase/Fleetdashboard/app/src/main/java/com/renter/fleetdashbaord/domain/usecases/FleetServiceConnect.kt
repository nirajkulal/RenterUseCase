package com.renter.fleetdashbaord.domain.usecases

interface FleetServiceConnect {
    fun connectToFleetService(username: String, limit: Int, startTime: String, endTime: String)

    fun getSpeedLimitCallback(callback: () -> Unit)
}
