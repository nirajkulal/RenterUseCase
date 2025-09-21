package com.renter.fleetdashbaord.viewmodel

import com.renter.fleetdashbaord.domain.usecases.ConnectFleetServiceUseCase
import com.renter.fleetdashbaord.domain.usecases.ManageNotificationsUseCase

class FleetViewmodel : MviViewModel<FleetState, FleetEvent, FleetEffect>(
    initialState = FleetState()
) {

    private val serviceConnectImp = ServiceConnectImp()
    private val connectToFleetServiceUseCase = ConnectFleetServiceUseCase(serviceConnectImp)
    private val manageNotificationsUseCase = ManageNotificationsUseCase()
    private val getSpeedCallbackUseCase = GetSpeedCallbackUseCase(serviceConnectImp)

    override suspend fun handleEvents(event: FleetEvent) {
        when (event) {
            is FleetEvent.FetchUserData -> {
                val user = connectToFleetServiceUseCase.execute(event.username)
                user?.let {
                    setEvent(FleetEvent.ConnectToFleetService(it.name, it.speedLimit, it.startTime, it.endTime))
                    setEvent(FleetEvent.ManageNotification(it.notificationType))
                }
            }
            is FleetEvent.ConnectToFleetService -> {
                connectToFleetServiceUseCase.execute(event.username, event.speedLimit, event.startTime, event.endTime)
            }
            is FleetEvent.ManageNotification -> {
                manageNotificationsUseCase.execute(event.notificationType)
            }
            is FleetEvent.SpeedCallback -> {
                getSpeedCallbackUseCase.execute { speed ->
                    //UI needed on speed limit reached
                    setState { copy(speedLimit = speed) }
                }
            }
        }
    }

    override suspend fun handleEffects(effect: FleetEffect) {
    }


    sealed class FleetEvent {
        data class FetchUserData(val username: String) : FleetEvent()
        data class ConnectToFleetService(val username: String, val speedLimit: Int, val startTime: String, val endTime: String) : FleetEvent()
        data class ManageNotification(val notificationType: String) : FleetEvent()
        object SpeedCallback : FleetEvent()
    }

    data class FleetState(
       val speedLimit: Int = 0,
       val notificationType: String = "",
    ) : MviState
}