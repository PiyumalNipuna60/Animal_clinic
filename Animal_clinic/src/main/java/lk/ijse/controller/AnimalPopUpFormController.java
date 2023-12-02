package lk.ijse.controller;

public class AnimalPopUpFormController {
    static String id="";
    public static AnimalPopUpFormController animalPopUpFormController;

    public void initialize(){
        animalPopUpFormController=this;
    }

    public static void setID(String animalID){
        id=animalID;
        System.out.println(id);
    }

}