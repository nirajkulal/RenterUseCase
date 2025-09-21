package com.renter.fleetdashbaord.domain.usecases

class GetSpeedCallbackUseCase (
    private val fleetServiceConnect: FleetServiceConnect
) {
    fun execute(callback: () -> Unit) {
        fleetServiceConnect.getSpeedLimitCallback(callback )
    }
}