package dragoncode.unipark.models;

public class parking_area {
    public String ParkingAreaID;
    public String ParkingAreaName;
    public String ParkingAreaLocation;
    public int ParkingAreaAccessLevel;

    public parking_area(){}

    public parking_area(String ID, String name, String location, int level){
        ParkingAreaID = ID;
        ParkingAreaName = name;
        ParkingAreaLocation = location;
        ParkingAreaAccessLevel = level;
    }

    public String getParkingAreaID() {
        return ParkingAreaID;
    }

    public String getParkingAreaName() {
        return ParkingAreaName;
    }

    public String getParkingAreaLocation() {
        return ParkingAreaLocation;
    }

    public int getParkingAreaAccessLevel() {
        return ParkingAreaAccessLevel;
    }

    public void setParkingAreaID(String parkingAreaID) {
        ParkingAreaID = parkingAreaID;
    }

    public void setParkingAreaName(String parkingAreaName) {
        ParkingAreaName = parkingAreaName;
    }

    public void setParkingAreaLocation(String parkingAreaLocation) {
        ParkingAreaLocation = parkingAreaLocation;
    }

    public void setParkingAreaAccessLevel(int parkingAreaAccessLevel) {
        ParkingAreaAccessLevel = parkingAreaAccessLevel;
    }
}
