package dragoncode.unipark.models;

public class license_plate_log {
    public int LicensePlateLogID;
    public String LicensePlateNumber;

    public license_plate_log(int licensePlateLogID, String licensePlateNumber) {
        LicensePlateLogID = licensePlateLogID;
        LicensePlateNumber = licensePlateNumber;
    }

    public license_plate_log() {
    }

    public int getLicensePlateLogID() {
        return LicensePlateLogID;
    }

    public void setLicensePlateLogID(int licensePlateLogID) {
        LicensePlateLogID = licensePlateLogID;
    }

    public String getLicensePlateNumber() {
        return LicensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        LicensePlateNumber = licensePlateNumber;
    }
}
