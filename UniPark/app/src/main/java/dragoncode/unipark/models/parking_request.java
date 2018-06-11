package dragoncode.unipark.models;

import java.util.Date;

public class parking_request {
    public int ParkingRequestId;
    public Date ParkingRequestTime;
    public String PersonelID;
    public String ParkingSpaceID;

    public parking_request(int parkingRequestId, Date parkingRequestTime, String personelID, String parkingSpaceID) {
        ParkingRequestId = parkingRequestId;
        ParkingRequestTime = parkingRequestTime;
        PersonelID = personelID;
        ParkingSpaceID = parkingSpaceID;
    }

    public parking_request() {
    }

    public int getParkingRequestId() {
        return ParkingRequestId;
    }

    public void setParkingRequestId(int parkingRequestId) {
        ParkingRequestId = parkingRequestId;
    }

    public Date getParkingRequestTime() {
        return ParkingRequestTime;
    }

    public void setParkingRequestTime(Date parkingRequestTime) {
        ParkingRequestTime = parkingRequestTime;
    }

    public String getPersonelID() {
        return PersonelID;
    }

    public void setPersonelID(String personelID) {
        PersonelID = personelID;
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        ParkingSpaceID = parkingSpaceID;
    }
}
