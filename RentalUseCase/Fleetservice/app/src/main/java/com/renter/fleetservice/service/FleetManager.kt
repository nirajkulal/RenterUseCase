package com.renter.fleetservice.service

import android.os.IBinder
import android.os.RemoteException
import android.os.ServiceManager
import android.util.Log
import com.renter.fleetservice.IRentalCallback
import com.renter.fleetservice.IRentalService
import kotlinx.coroutines.*
import kotlin.math.min

class FleetManager {

    companion object {
        private const val TAG = "FleetManager"
        private const val SERVICE_NAME = "fleet"
        private const val MAX_RETRIES = 10
    }

    private var service: IRentalService? = null
    private var retryCount = 0

    // Client-side callback interface
    interface SpeedLimitListener {
        fun onSpeedLimitCrossed(userId: String, actualSpeed: Int)
    }

    private var speedLimitListener: SpeedLimitListener? = null

    init {
        connectToService()
    }

    private fun connectToService() {
        val binder: IBinder? = ServiceManager.getService(SERVICE_NAME)
        if (binder != null) {
            try {
                service = IRentalService.Stub.asInterface(binder)
                binder.linkToDeath({
                    Log.w(TAG, "FleetService binder died. Retrying...")
                    service = null
                    retryWithBackoff()
                }, 0)
                retryCount = 0 // reset retries
                Log.d(TAG, "Connected to FleetService")
            } catch (e: RemoteException) {
                Log.e(TAG, "Binder linkToDeath failed", e)
                retryWithBackoff()
            }
        } else {
            Log.w(TAG, "FleetService not available, retrying...")
            retryWithBackoff()
        }
    }

    private fun retryWithBackoff() {
        if (retryCount >= MAX_RETRIES) {
            Log.e(TAG, "Max retries reached. Giving up.")
            return
        }
        val delayTime = min(1000 * (1 shl retryCount), 30_000) // exponential, cap 30s
        retryCount++

        // Launch a coroutine to handle the delay and retry
        CoroutineScope(Dispatchers.IO).launch {
            delay(delayTime.toLong()) // Coroutine-friendly delay
            connectToService()
        }
    }

    // ========= Public APIs =========

    fun setSpeedLimit(limit: Int) {
        try {
            service?.setSpeedLimit(limit)
                ?: Log.w(TAG, "Service not connected, dropping setSpeedLimit")
        } catch (e: RemoteException) {
            Log.e(TAG, "setSpeedLimit failed", e)
        }
    }

    fun notifyLogin(userId: String, startTime: Long, endTime: Long) {
        try {
            service?.notifyLogin(userId, startTime, endTime)
                ?: Log.w(TAG, "Service not connected, dropping notifyLogin")
        } catch (e: RemoteException) {
            Log.e(TAG, "notifyLogin failed", e)
        }
    }

    // Register the client-side listener
    fun setSpeedLimitListener(listener: SpeedLimitListener) {
        this.speedLimitListener = listener
        registerCallback(object : IRentalCallback.Stub() {
            override fun onSpeedLimitCrossed(userId: String, actualSpeed: Int) {
                // Forward the callback to the client-side listener
                speedLimitListener?.onSpeedLimitCrossed(userId, actualSpeed)
            }
        })
    }

    private fun registerCallback(cb: IRentalCallback) {
        try {
            service?.registerCallback(cb)
                ?: Log.w(TAG, "Service not connected, dropping registerCallback")
        } catch (e: RemoteException) {
            Log.e(TAG, "registerCallback failed", e)
        }
    }
}