package com.renter.fleetdashbaord.domain.usecases

import com.renter.fleetdashbaord.data.repository.serviceconnect.FleetServiceConnect

class ConnectFleetServiceUseCase(
    private val fleetServiceConnect: FleetServiceConnect
) {
    fun execute(username: String, speedLimit: Int, startTime: String, endTime: String) {
        fleetServiceConnect.connectToFleetService(username, speedLimit, startTime, endTime)
    }
}