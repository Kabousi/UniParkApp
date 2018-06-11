package dragoncode.unipark.models;

public class personel_type {
    public int PersonelTypeID;
    public String PersonelTypeDesc;

    public personel_type(int personelTypeID, String personelTypeDesc) {
        PersonelTypeID = personelTypeID;
        PersonelTypeDesc = personelTypeDesc;
    }

    public personel_type() {
    }

    public int getPersonelTypeID() {
        return PersonelTypeID;
    }

    public void setPersonelTypeID(int personelTypeID) {
        PersonelTypeID = personelTypeID;
    }

    public String getPersonelTypeDesc() {
        return PersonelTypeDesc;
    }

    public void setPersonelTypeDesc(String personelTypeDesc) {
        PersonelTypeDesc = personelTypeDesc;
    }
}
