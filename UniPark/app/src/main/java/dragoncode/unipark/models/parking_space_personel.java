package dragoncode.unipark.models;

public class parking_space_personel {
    public String ParkingSpaceID;
    public String PersonelID;

    public parking_space_personel(String parkingSpaceID, String personelID) {
        ParkingSpaceID = parkingSpaceID;
        PersonelID = personelID;
    }

    public parking_space_personel() {
    }

    public String getParkingSpaceID() {
        return ParkingSpaceID;
    }

    public void setParkingSpaceID(String parkingSpaceID) {
        ParkingSpaceID = parkingSpaceID;
    }

    public String getPersonelID() {
        return PersonelID;
    }

    public void setPersonelID(String personelID) {
        PersonelID = personelID;
    }
}
