package dragoncode.unipark.models;

public class main_gate {
    public  int MainGateID;
    public String MainGateName;

    public main_gate(){

    }

    public main_gate(int ID, String name){
        MainGateID = ID;
        MainGateName = name;
    }

    public  void setMainGateID(int ID){
        MainGateID = ID;
    }

    public void setMainGateName(String name){
        MainGateName = name;
    }

    public int getMainGateID() {
        return MainGateID;
    }

    public String getMainGateName() {
        return MainGateName;
    }
}
