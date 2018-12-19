package com.integration.sra.drocter;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DownloadJSONLISTUNT extends AsyncTask<Void,Void,Void> {
    JSONObject json_data;
    Context context;
    ListView list_of_item;
    private EditText search;
    public EditText searcharticle;
    private HashMap<String,String>[] props;
    private String URL = "";
    private String SOAP_ACTION ;
    private String METHOD_NAME ;
    private String NAMESPACE  ;
    private String grp;
    private Set<String> parameters;
    public int resource;


    private int[] resources;
    public DownloadJSONLISTUNT(String URL,
                               String SOAP_ACTION ,
                               int[] resources,
                               ListView list_of_item,
                               EditText search,
                               int resource,
                               String METHOD_NAME ,
                               String grp,
                               Context context,
                               Set<String> parameters,
                               String NAMESPACE,
                               HashMap<String,String>... props){
        this.resources=resources;
        this.parameters=parameters;
        this.resource=resource;
        this.search=search;
        this.context=context;
        this.list_of_item=list_of_item;
        this.URL = URL;this.grp=grp;
        this.SOAP_ACTION = SOAP_ACTION;
        this.METHOD_NAME = METHOD_NAME;
        this.NAMESPACE = NAMESPACE;
        this.props=props;
    }

    ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();



    @Override
    protected Void doInBackground(Void... params) {
        Map<String, String> Map_articles = null;
        Map<String, String> mapPPPP1 = null;
        String rest1="";
        InputStream is;
        SoapObject request = new SoapObject(this.NAMESPACE, this.METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        for (HashMap<String,String> p:props){
            for( Map.Entry<String, String> entry : p.entrySet()){
                request.addProperty(entry.getKey(),entry.getValue());
            }
        }
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transport = new HttpTransportSE(this.URL);
        transport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        transport.debug = true;

        try {
            transport.call(this.SOAP_ACTION, envelope);
            rest1 = envelope.getResponse().toString();


        } catch (NullPointerException exception) {

        } catch (IOException e) {
            Log.d("erreur", e + "");
            e.printStackTrace();
        } catch (XmlPullParserException e) {

            e.printStackTrace();
        }

        try {
            JSONObject JA = new JSONObject(rest1);
            JSONArray json = JA.getJSONArray(this.grp);

            for (int i = 0; i < json.length(); i++) {
                json_data = json.getJSONObject(i);
                Map_articles = new HashMap<String, String>();
                for(String s :parameters){
                    Map_articles.put(s, json_data.getString(s));
                    arrayList.add(Map_articles);
                }

            }


        } catch (JSONException e) {
            Log.i("tagjsonexp", "" + e.toString());
        } catch (org.apache.http.ParseException e) {
            Log.i("tagjsonpars", "" + e.toString());
        }



        return null;
    }


    @Override
    protected void onPostExecute(Void args) {


        try {
            Set<String> tmp_map= new HashSet<>();
            for(String s  : parameters) {
                tmp_map.add(s);}

            final ArrayList<Map<String, String>> list_Item = arrayList;

            String[] arrayofstring=new String[tmp_map.size()];

            final SimpleAdapter mSchedule = new SimpleAdapter(this.context,list_Item, resource,
                    (String[]) tmp_map.toArray(arrayofstring),resources);

            list_of_item.setAdapter(mSchedule);
            search.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    mSchedule.getFilter().filter(cs);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });


        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
    }

}
