import com.renter.fleetdashbaord.domain.usecases.FleetServiceConnect
import com.renter.fleetdashbaord.data.repository.FleetServiceManager

class ServiceConnectImp : FleetServiceConnect {

    private val fleetServiceManager = FleetServiceManager()

    override fun connectToFleetService(username: String, limit: Int, startTime: String, endTime: String) {
        fleetServiceManager.connectToFleetService(username, limit, startTime, endTime)
    }

    override fun getSpeedLimitCallback(callback: () -> Unit) {
        fleetServiceManager.setSpeedLimitListener(object : FleetServiceManager.SpeedLimitListener {
            override fun onSpeedLimitCrossed(userId: String, actualSpeed: Int) {
                // Invoke the lambda with the parameters
                callback()
            }
        })
    }
}