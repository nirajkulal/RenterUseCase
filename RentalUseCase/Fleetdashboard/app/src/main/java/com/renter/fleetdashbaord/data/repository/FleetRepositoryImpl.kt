package com.renter.fleetdashbaord.data.repository

import com.renter.fleetdashbaord.data.repository.network.Fleetnetwork
import com.renter.fleetdashbaord.data.network.User
import com.renter.fleetdashbaord.domain.usecases.FleetRepository.FleetRepository

class FleetRepositoryImpl : FleetRepository {

    private val fleetnetwork = Fleetnetwork()

    override fun fetchUserData(username: String): User {
        // Simulate fetching user data
        return fleetnetwork.getUserData(username)
    }
}