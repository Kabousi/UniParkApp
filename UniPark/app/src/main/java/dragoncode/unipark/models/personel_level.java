package dragoncode.unipark.models;

public class personel_level {
    public int PersonelLevelID;
    public String PersonelLevelDesc;

    public personel_level(int personelLevelID, String personelLevelDesc) {
        PersonelLevelID = personelLevelID;
        PersonelLevelDesc = personelLevelDesc;
    }

    public personel_level() {
    }

    public int getPersonelLevelID() {
        return PersonelLevelID;
    }

    public void setPersonelLevelID(int personelLevelID) {
        PersonelLevelID = personelLevelID;
    }

    public String getPersonelLevelDesc() {
        return PersonelLevelDesc;
    }

    public void setPersonelLevelDesc(String personelLevelDesc) {
        PersonelLevelDesc = personelLevelDesc;
    }
}
