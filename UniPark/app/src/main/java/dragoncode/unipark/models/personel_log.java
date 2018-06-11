package dragoncode.unipark.models;

import java.util.Date;

public class personel_log {
    public int PersonelLogID;
    public Date PersonelLoginTime;
    public Date PersonelLogoutTime;
    public String PersonelID;
    public int MainGateID;
    public int LicensePlateLogID;

    public personel_log(int personelLogID, Date personelLoginTime, Date personelLogoutTime, String personelID, int mainGateID, int licensePlateLogID) {
        PersonelLogID = personelLogID;
        PersonelLoginTime = personelLoginTime;
        PersonelLogoutTime = personelLogoutTime;
        PersonelID = personelID;
        MainGateID = mainGateID;
        LicensePlateLogID = licensePlateLogID;
    }

    public personel_log() {
    }

    public int getPersonelLogID() {
        return PersonelLogID;
    }

    public void setPersonelLogID(int personelLogID) {
        PersonelLogID = personelLogID;
    }

    public Date getPersonelLoginTime() {
        return PersonelLoginTime;
    }

    public void setPersonelLoginTime(Date personelLoginTime) {
        PersonelLoginTime = personelLoginTime;
    }

    public Date getPersonelLogoutTime() {
        return PersonelLogoutTime;
    }

    public void setPersonelLogoutTime(Date personelLogoutTime) {
        PersonelLogoutTime = personelLogoutTime;
    }

    public String getPersonelID() {
        return PersonelID;
    }

    public void setPersonelID(String personelID) {
        PersonelID = personelID;
    }

    public int getMainGateID() {
        return MainGateID;
    }

    public void setMainGateID(int mainGateID) {
        MainGateID = mainGateID;
    }

    public int getLicensePlateLogID() {
        return LicensePlateLogID;
    }

    public void setLicensePlateLogID(int licensePlateLogID) {
        LicensePlateLogID = licensePlateLogID;
    }
}
