package com.integration.sra.srawebservicelibrary.WebserviceLibrery;

public interface ConnexionWebserviceInterface<T> {
    static  String URL = "http://192.168.1.184:8081/Sodet2/services/soap_sodet_beta?wsdl";
    String fetchdata();
}
