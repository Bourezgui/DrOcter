package com.integration.sra.drocter;

import com.integration.sra.srawebservicelibrary.Factory.WebserviceFactory;
import com.integration.sra.srawebservicelibrary.WebserviceEntities.ObjectTest;
import com.integration.sra.srawebservicelibrary.WebserviceLibrery.ConnexionWebserviceInterface;


public class Main {


    public static void main(String[] args) {
        ObjectTest objectTest;
        objectTest = new ObjectTest("1&2", "a", "a", "a", "a", "a", "a&b");
        ConnexionWebserviceInterface<ObjectTest> connexionWebservice;
        connexionWebservice = new WebserviceFactory<ObjectTest>().getConnection(objectTest,"insert_information","");
        System.out.println(connexionWebservice.fetchdata());

    }
}
