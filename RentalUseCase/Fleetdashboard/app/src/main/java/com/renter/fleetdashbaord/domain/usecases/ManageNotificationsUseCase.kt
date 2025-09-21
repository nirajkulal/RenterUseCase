package com.renter.fleetdashbaord.domain.usecases

import com.renter.fleetdashbaord.data.repository.AWSNotificationsImp
import com.renter.fleetdashbaord.data.repository.FCMNotifcationImp
import com.renter.fleetdashbaord.domain.usecases.HandleNotification

class ManageNotificationsUseCase {
    private var manageNotification: HandleNotification? = null

    fun execute(notificationType: String) {
        when (notificationType) {
            "AWS" -> {
                manageNotification = AWSNotificationsImp()
                manageNotification?.initializeNotifications()
            }
            "FCM" -> {
                manageNotification = FCMNotifcationImp()
                manageNotification?.initializeNotifications()
            }
            else -> {
                // Handle default or unknown notification types
            }
        }
    }
}