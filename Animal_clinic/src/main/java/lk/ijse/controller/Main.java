package lk.ijse.controller;

import lk.ijse.controller.mail.Mail;

public class Main {
    public static void main(String[] args) {
        String msg= "College Email \nHello"+" \n"+"You are selected As 'Admin'.\n Please use Given username and password for login\n Username:"+"  "+"Password:"+" ";
        System.out.println(msg);

        Mail mail = new Mail(); //creating an instance of Mail class
        mail.setMsg("Hi");//email message
        mail.setTo("paniyapranks@gmail.com"); //receiver's mail
        mail.setSubject("Test"); //email subject

        Thread thread = new Thread(mail);
        thread.start();

        System.out.println("end");
    }
}