package dragoncode.unipark.models;

public class personel {
    public String PersonelID;
    public String PersonelTagNumber;
    public String PersonelPassword;
    public String PersonelSurname;
    public String PersonelName;
    public String PersonelPhoneNumber;
    public String PersonelEmail;
    public int PersonelLevelID;
    public int PersonelType;

    public personel(String personelID, String personelTagNumber, String personelPassword, String personelSurname, String personelName, String personelPhoneNumber, String personelEmail, int personelLevelID, int personelType) {
        PersonelID = personelID;
        PersonelTagNumber = personelTagNumber;
        PersonelPassword = personelPassword;
        PersonelSurname = personelSurname;
        PersonelName = personelName;
        PersonelPhoneNumber = personelPhoneNumber;
        PersonelEmail = personelEmail;
        PersonelLevelID = personelLevelID;
        PersonelType = personelType;
    }

    public personel() {
    }

    public String getPersonelID() {
        return PersonelID;
    }

    public void setPersonelID(String personelID) {
        PersonelID = personelID;
    }

    public String getPersonelTagNumber() {
        return PersonelTagNumber;
    }

    public void setPersonelTagNumber(String personelTagNumber) {
        PersonelTagNumber = personelTagNumber;
    }

    public String getPersonelPassword() {
        return PersonelPassword;
    }

    public void setPersonelPassword(String personelPassword) {
        PersonelPassword = personelPassword;
    }

    public String getPersonelSurname() {
        return PersonelSurname;
    }

    public void setPersonelSurname(String personelSurname) {
        PersonelSurname = personelSurname;
    }

    public String getPersonelName() {
        return PersonelName;
    }

    public void setPersonelName(String personelName) {
        PersonelName = personelName;
    }

    public String getPersonelPhoneNumber() {
        return PersonelPhoneNumber;
    }

    public void setPersonelPhoneNumber(String personelPhoneNumber) {
        PersonelPhoneNumber = personelPhoneNumber;
    }

    public String getPersonelEmail() {
        return PersonelEmail;
    }

    public void setPersonelEmail(String personelEmail) {
        PersonelEmail = personelEmail;
    }

    public int getPersonelLevelID() {
        return PersonelLevelID;
    }

    public void setPersonelLevelID(int personelLevelID) {
        PersonelLevelID = personelLevelID;
    }

    public int getPersonelType() {
        return PersonelType;
    }

    public void setPersonelType(int personelType) {
        PersonelType = personelType;
    }
}
