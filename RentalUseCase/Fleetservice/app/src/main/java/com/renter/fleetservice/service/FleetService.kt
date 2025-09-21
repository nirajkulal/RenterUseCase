package com.renter.fleetservice.service

import android.app.Service
import android.car.Car
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.VehiclePropertyIds
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import com.renter.fleetservice.IRentalCallback
import com.renter.fleetservice.IRentalService

class FleetService : Service() {

    private var callback: IRentalCallback? = null
    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null

    override fun onCreate() {
        super.onCreate()
        car = Car.createCar(this)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

        // Listen for car speed
        carPropertyManager?.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(value: CarPropertyValue<*>) {
                    if (value.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
                        val speed = value.value as Float
                        Log.d("FleetService", "Speed = $speed")

                        val limit = Settings.Global.getInt(contentResolver, "fleet_speed_limit", -1)
                        if (limit > 0 && speed > limit) {
                            callback?.onSpeedLimitChanged(limit)
                        }
                    }
                }
                override fun onErrorEvent(propId: Int, zone: Int) {
                    Log.e("FleetService", "Car property error: $propId")
                }
            },
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    private val binder = object : IRentalService.Stub() {

        override fun setSpeedLimit(limit: Int) {
            // Store globally (accessible system-wide)
            Settings.Global.putInt(contentResolver, "fleet_speed_limit", limit)
            callback?.onSpeedLimitChanged(limit)
        }

        override fun notifyLogin(userId: String?, startTime: Long, endTime: Long) {
            // Simplified: store globally via Settings for now
            Settings.Global.putString(contentResolver, "fleet_active_user", userId)
        }

        override fun registerCallback(cb: IRentalCallback?) {
            callback = cb
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        carPropertyManager?.unregisterCallback(null)
        car?.disconnect()
    }
}
