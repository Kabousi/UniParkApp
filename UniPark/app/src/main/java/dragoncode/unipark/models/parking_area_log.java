package dragoncode.unipark.models;

import java.sql.Time;
import java.util.Date;

public class parking_area_log {
    public int AreaLogID;
    public Date AreaLoginTime;
    public Date AreaLogoutTime;
    public int PersonelLogID;
    public String ParkingAreaID;

    public parking_area_log(int areaLogID, Date areaLoginTime, Date areaLogoutTime, int personelLogID, String parkingAreaID) {
        AreaLogID = areaLogID;
        AreaLoginTime = areaLoginTime;
        AreaLogoutTime = areaLogoutTime;
        PersonelLogID = personelLogID;
        ParkingAreaID = parkingAreaID;
    }

    public parking_area_log() {
    }

    public int getAreaLogID() {
        return AreaLogID;
    }

    public void setAreaLogID(int areaLogID) {
        AreaLogID = areaLogID;
    }

    public Date getAreaLoginTime() {
        return AreaLoginTime;
    }

    public void setAreaLoginTime(Date areaLoginTime) {
        AreaLoginTime = areaLoginTime;
    }

    public Date getAreaLogoutTime() {
        return AreaLogoutTime;
    }

    public void setAreaLogoutTime(Date areaLogoutTime) {
        AreaLogoutTime = areaLogoutTime;
    }

    public int getPersonelLogID() {
        return PersonelLogID;
    }

    public void setPersonelLogID(int personelLogID) {
        PersonelLogID = personelLogID;
    }

    public String getParkingAreaID() {
        return ParkingAreaID;
    }

    public void setParkingAreaID(String parkingAreaID) {
        ParkingAreaID = parkingAreaID;
    }
}
