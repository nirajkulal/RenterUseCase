package com.renter.fleetservice;

interface IRentalService {
    void setSpeedLimit(int limit);
    void notifyLogin(String userId);
    void registerCallback(IRentalCallback cb);
}