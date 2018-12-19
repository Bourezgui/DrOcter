package com.integration.sra.srawebservicelibrary.WebservicelibreryImplementation;


import com.integration.sra.srawebservicelibrary.WebserviceLibrery.ConnexionWebserviceInterface;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConnexionWebservice<T> implements ConnexionWebserviceInterface{

    //Action Methode et NameSpace doit etre Unique lors de l'instatiation de l'objet
    private final String SOAP_ACTION ;
    private final String METHOD_NAME ;
    private final String NAMESPACE="http://connexion"   ;
    //Autre Attrebute
    private SoapObject request;
    private SoapSerializationEnvelope envelope ;
    private T ObjectSerialise;
    private Class c;
    private Method m ;
    private String Result;
    private String Grp;
    //Constructeur Par defaut
    private ConnexionWebservice(String METHOD_NAME
            ){

        super();
        this.SOAP_ACTION=NAMESPACE+"/"+METHOD_NAME;
        this.METHOD_NAME=METHOD_NAME;

    }
    public ConnexionWebservice(String METHOD_NAME
                              ,String Grp
                              ,T ObjectSerialise){
        this(METHOD_NAME);
        this.Grp=Grp;
        this.ObjectSerialise=ObjectSerialise;
        this.c=ObjectSerialise.getClass();
    }


    public  String fetchdata(){
        request=new SoapObject(NAMESPACE,METHOD_NAME);
        envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Ajout des properties

                try{
                    for(Field f:c.getDeclaredFields()){
                        //Appel des Champs d'une maniere Reflexive
                        m=c.getMethod("get"+f.getName(),null);
                        //Appel des Methodes d'une maniere Reflexive
                        request.addProperty(f.getName(),m.invoke(ObjectSerialise,null));
                    }
                }
                catch(NoSuchMethodException e){

                    e.getStackTrace();


                }
                catch(IllegalAccessException e){

                    e.getStackTrace();

                }

                catch(InvocationTargetException e){

                    e.getStackTrace();

                }




        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transport=new HttpTransportSE(URL);
        transport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        transport.debug=true;

        try{
        transport.call(this.SOAP_ACTION,envelope);
        Result=envelope.getResponse().toString();

        }catch(NullPointerException exception){

        }catch (XmlPullParserException e){


        }catch(IOException e){}



        return Result;
        }





}
