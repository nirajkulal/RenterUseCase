package com.renter.fleetdashbaord.domain.usecases

import com.renter.fleetdashbaord.data.network.User
import com.renter.fleetdashbaord.domain.usecases.FleetRepository.FleetRepository

class FetchUserUseCase(private val fleetRepository: FleetRepository) {
    fun execute(username: String): User {
        return fleetRepository.fetchUserData(username)
    }
}