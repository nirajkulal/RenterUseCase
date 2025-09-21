package com.renter.fleetservice.repository

import com.renter.fleetservice.network.FleetAPI

class FleetrepositoryImp : Fleetrepository {
    val api: FleetAPI = FleetAPI()

    override fun sendLimit(limit: Int) {
        api.sendLimit(limit)
    }
}