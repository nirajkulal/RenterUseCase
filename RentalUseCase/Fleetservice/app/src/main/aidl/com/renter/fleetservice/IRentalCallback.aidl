package com.renter.fleetservice;

interface IRentalCallback {
    void onSpeedLimitCrossed(String userId, int actualSpeed);
}