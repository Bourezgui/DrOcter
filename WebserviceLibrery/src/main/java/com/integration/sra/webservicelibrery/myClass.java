package com.integration.sra.webservicelibrery;


public class myClass {
    public static void main(String[] args){


        ObjectTest objectTest;
        ConnexionWebservice<ObjectTest> connexionWebservice;

        objectTest = new ObjectTest("10&5","YMAT","YLOT","YPAL",
                "YITM","YOF","A&B");

        ConnexionWebservice.URL="http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl";
        connexionWebservice=new ConnexionWebservice<>( "http://connexion/insert_information",
                "insert_information",
                "http://connexion",
                "",objectTest);
        System.out.print(connexionWebservice.fetchdata());


    }
}
