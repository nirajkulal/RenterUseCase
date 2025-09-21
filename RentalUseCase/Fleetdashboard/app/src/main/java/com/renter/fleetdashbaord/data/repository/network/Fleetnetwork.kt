package com.renter.fleetdashbaord.data.repository.network

class Fleetnetwork {
    /**
     * Simulate a network call to fetch user data
     */
    fun getUserData(username: String): User {
        // Simulate fetching user data from a network source
        return User(id = "1", name = username, speedLimit = 60, notificationType = "Email")
    }
}