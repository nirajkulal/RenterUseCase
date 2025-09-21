package com.renter.fleetdashbaord.domain.usecases.FleetRepository

import com.renter.fleetdashbaord.data.network.User

interface FleetRepository {
    fun fetchUserData(username: String): User
}