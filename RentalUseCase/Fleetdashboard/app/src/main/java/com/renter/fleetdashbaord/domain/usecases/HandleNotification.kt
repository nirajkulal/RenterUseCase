package com.renter.fleetdashbaord.domain.usecases

interface ManageNotifications {
    fun initializeNotifications()

    fun onMessageReceived(message: String)
}