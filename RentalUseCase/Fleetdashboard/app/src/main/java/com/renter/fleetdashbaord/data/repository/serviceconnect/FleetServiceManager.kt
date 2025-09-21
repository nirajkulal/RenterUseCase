package com.renter.fleetdashbaord.data.repository.serviceconnect

import com.renter.fleetservice.FleetManager

class FleetServiceManager {

    private val fleetManager = FleetManager()

    fun connectToFleetService(username: String, limit: Int, startTime: String, endTime: String) {
        fleetManager.setSpeedLimit(limit)
        fleetManager.notifyLogin(username, startTime.toLong(), endTime.toLong())
    }
}