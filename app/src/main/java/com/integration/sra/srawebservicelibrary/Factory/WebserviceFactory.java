package com.integration.sra.srawebservicelibrary.Factory;

import com.integration.sra.srawebservicelibrary.WebserviceLibrery.ConnexionWebserviceInterface;
import com.integration.sra.srawebservicelibrary.WebservicelibreryImplementation.ConnexionWebservice;

public class WebserviceFactory<T> {

    public ConnexionWebserviceInterface getConnection(T s,String... args){

        return new ConnexionWebservice(args[0],args[1],s);

    }

}
