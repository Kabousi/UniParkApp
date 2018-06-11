package dragoncode.unipark.models;

public class parking_space {
    public String ParkingSpaceID;
    public String ParkingTyoe;
    public int Available;
    public String ParkingAreaID;
    public int ParkingFeeID;

    public parking_space(String parkingSpaceID, String parkingTyoe, int available, String parkingAreaID, int parkingFeeID) {
        ParkingSpaceID = parkingSpaceID;
        ParkingTyoe = parkingTyoe;
        Available = available;
        ParkingAreaID = parkingAreaID;
        ParkingFeeID = parkingFeeID;
    }

    public parking_space() {
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        ParkingSpaceID = parkingSpaceID;
    }

    public String getParkingTyoe() {
        return ParkingTyoe;
    }

    public void setParkingTyoe(String parkingTyoe) {
        ParkingTyoe = parkingTyoe;
    }

    public int getAvailable() {
        return Available;
    }

    public void setAvailable(int available) {
        Available = available;
    }

    public String getParkingAreaID() {
        return ParkingAreaID;
    }

    public void setParkingAreaID(String parkingAreaID) {
        ParkingAreaID = parkingAreaID;
    }

    public int getParkingFeeID() {
        return ParkingFeeID;
    }

    public void setParkingFeeID(int parkingFeeID) {
        ParkingFeeID = parkingFeeID;
    }
}
