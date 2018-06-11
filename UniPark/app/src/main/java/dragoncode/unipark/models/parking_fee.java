package dragoncode.unipark.models;

public class parking_fee {
    public int ParkingFeeID;
    public double ParkingFeeAmount;

    public parking_fee(int parkingFeeID, double parkingFeeAmount) {
        ParkingFeeID = parkingFeeID;
        ParkingFeeAmount = parkingFeeAmount;
    }

    public parking_fee() {
    }

    public int getParkingFeeID() {
        return ParkingFeeID;
    }

    public void setParkingFeeID(int parkingFeeID) {
        ParkingFeeID = parkingFeeID;
    }

    public double getParkingFeeAmount() {
        return ParkingFeeAmount;
    }

    public void setParkingFeeAmount(double parkingFeeAmount) {
        ParkingFeeAmount = parkingFeeAmount;
    }
}
