package com.integration.sra.drocter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.integration.sra.srawebservicelibrary.Utile.OrdreFabrication;
import com.integration.sra.srawebservicelibrary.WebserviceEntities.ObjectTest;
import com.integration.sra.srawebservicelibrary.WebserviceLibrery.ConnexionWebserviceInterface;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class fragment_ajout extends Fragment {
    View v;
    private AutoCompleteTextView matricule,article,of,palette,capacite,lot;
    private OrdreFabrication basedonne;
    private static Dialog alerte;
    private ObjectTest objectTest;
    private Button annulea,annulem,annulef;
    public Dialog alerte_article,alert_quantit;
    private Button ajouter;
    public Dialog alerte_matricule,alerte_of;
    public EditText searcharticle;
    public EditText searchmatricule,searchof;
    ListView list_article,list_matricule,list_of;
    private EditText recherche;
    ConnexionWebserviceInterface<ObjectTest> connexionWebservice;
    private StringBuilder s,q;
    private ListView listnum;
    private Set<String> tableofparams,tableofparams1,tableofparams2;
    private HashMap<String,String> maps;
    final ArrayList<Map<String, String>> listItema=new ArrayList<>();
    private SwipeMenuListView List ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public void showalertelisteart(){

        alerte_article.setTitle("Liste des articles"); // titre de l'alert
        alerte_article.show();
    }

    public void showalertelistof(){

        alerte_of.setTitle("Liste des of"); // titre de l'alerte
        alerte_of.show();
    }

    public void showalertelistmat(){


        alerte_matricule.setTitle("Liste des matricules"); // titre de l'alerte
        alerte_matricule.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_fragment_ajout, container, false);
        //palette.setText("c10");
        recherche= v.findViewById(R.id.recherche);
        matricule=v.findViewById(R.id.matricule);
        article=v.findViewById(R.id.article);
        of=v.findViewById(R.id.of);
        //palette=v.findViewById(R.id.palette);
        capacite=v.findViewById(R.id.capacite);
        lot=v.findViewById(R.id.lot);
        List = v.findViewById(R.id.fard);
        ajouter=v.findViewById(R.id.ajouter);
        alerte=new Dialog(getActivity());
        alerte_article=new Dialog(getActivity());
        alert_quantit=new Dialog(getActivity());
        alerte_matricule=new Dialog(getActivity());
        alerte_of=new Dialog(getActivity());
        alerte_article.setContentView(R.layout.listart);
        alerte_matricule.setContentView(R.layout.listart);

        alerte_of.setContentView(R.layout.listart);
        searcharticle= alerte_article.findViewById(R.id.recherche);
        searchmatricule= alerte_matricule.findViewById(R.id.recherche);
        searchof= alerte_of.findViewById(R.id.recherche);
        alerte.setContentView(R.layout.listnum);
        list_article= alerte_article.findViewById(R.id.list1);
        list_matricule=alerte_matricule.findViewById(R.id.list1);
        list_of= alerte_of.findViewById(R.id.list1);
        listnum = alerte.findViewById(R.id.list);

        annulem = (Button) alerte_matricule.findViewById(R.id.annulation);
        annulem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Unite_Cond.setText("");
                alerte_matricule.dismiss();
            }
        });
        annulea = (Button) alerte_article.findViewById(R.id.annulation);
        annulea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Unite_Cond.setText("");
                alerte_article.dismiss();
            }
        });
        annulef = (Button) alerte_of.findViewById(R.id.annulation);
        annulef.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Unite_Cond.setText("");
                alerte_of.dismiss();
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.LTGRAY));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setIcon(R.drawable.ic_action_name);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.LTGRAY));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }

        };

// set creator
        List.setMenuCreator(creator);
        List.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i1) {
                switch (i1){
                    case 0 : confirm(i,0);
                        break;
                    case 1 : confirm(i,-1);
                }
                return false;
            }
        });
        // ########################################################################################
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message="Enregistrement Ajouté à la base local avec Succée";
                basedonne=new OrdreFabrication();
                basedonne.setArticle(article.getText().toString());
                basedonne.setOf(of.getText().toString());
                basedonne.setLot(lot.getText().toString());
                basedonne.setIdpalette("PAL"+
                       String.format("%06d",OrdreFabrication.count(OrdreFabrication.class,"",null)));
                basedonne.save();
                //Sauvegarde de Données
                new UploadSoapDataAsynck().execute();
                article.setText("");matricule.setText("");
                of.setText("");
                listItema.clear();
                final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema,
                        R.layout.item_fard, new String[]{"numliste", "numfard"}, new int[]{R.id.num1, R.id.num2});
                List.setAdapter(mSchedule);



            }
        });

        //fonction a appler
        maps=new HashMap<>();

        tableofparams=new HashSet<String>(){{
            add("DES");add("ART");
        }};
        tableofparams1=new HashSet<String>(){{
            add("DES");add("MAT");

        }};
        tableofparams2=new HashSet<String>(){{
            add("NUM");

        }};


        of.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                maps.put("YMAT",article.getText().toString());
                new DownloadJSONLISTUNT(
                        "http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl"
                        ,"http://connexion/Listof"
                        ,new int[]{R.id.numfard}
                        ,list_of
                        ,searchof
                        ,R.layout.item_listecol
                        ,"Listof"
                        ,"GRP2"
                        ,getActivity()
                        ,tableofparams2
                        ,"http://connexion"
                        ,maps).execute();
                showalertelistof();
                return true;
            }
        });



        matricule.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new DownloadJSONLISTUNT(
                        "http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl"
                        ,"http://connexion/Listmatricule"
                        ,new int[]{R.id.numfard}
                        ,list_matricule
                        ,searchmatricule
                        ,R.layout.item_listecol
                        ,"Listmatricule"
                        ,"GRP1"
                        ,getActivity()
                        ,tableofparams1
                        ,"http://connexion"
                        ,maps).execute();
                showalertelistmat();
                return true;
            }
        });




        //Selectionner Article

        article.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new DownloadJSONLISTUNT(
                        "http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl"
                        ,"http://connexion/ListArticle"
                        ,new int[]{R.id.numfard}
                        ,list_article
                        ,searcharticle
                        ,R.layout.item_listecol
                        ,"ListArticle"
                        ,"GRP1"
                        ,getActivity()
                        ,tableofparams
                        ,"http://connexion"
                        ,maps).execute();
                showalertelisteart();
                return true;
            }
        });

        list_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                @SuppressWarnings({"unchecked"})
                final HashMap<String, Object> map = (HashMap<String, Object>) list_article.getItemAtPosition(position);
                String numart=(String) map.get("ART");
                article.setText(numart);
                alerte_article.dismiss();
            }

        });

        list_of.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                @SuppressWarnings({"unchecked"})
                final HashMap<String, Object> map = (HashMap<String, Object>) list_of.getItemAtPosition(position);
                String numart=(String) map.get("NUM");
                of.setText(numart);
                alerte_of.dismiss();
            }

        });
        list_matricule.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                @SuppressWarnings({"unchecked"})
                final HashMap<String, Object> map = (HashMap<String, Object>) list_matricule.getItemAtPosition(position);
                String numart=(String) map.get("MAT");
                matricule.setText(numart);
                alerte_matricule.dismiss();
            }

        });



        //Selectionner Lot
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirm();
                String message="Enregistrement Ajouté à la base local avec Succée";
                Snackbar.make(view,message, Snackbar.LENGTH_LONG)
                        .setAction(message, null).show();
            }

        });








        return v;
    }
    public void confirm(int... s) {
        final int[] z = s;
        boolean stat=true;
        switch(s.length){
            case 0 : stat=true;break;
            case 2 : stat=s[1]==-1?false:true;
        }
        AlertDialog.Builder alertDialogBuilder;
        if(stat) {
            alertDialogBuilder = new AlertDialog.Builder(getActivity());
            final View mview = getLayoutInflater().inflate(R.layout.alertquantite, null);
            final Map<String, String> map = new HashMap<>();
            alertDialogBuilder.setTitle("Ajout Ligne");
            alertDialogBuilder
                    .setView(mview)
                    .setCancelable(false)
                    .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final EditText qanty = mview.findViewById(R.id.qty);
                            final EditText batch = mview.findViewById(R.id.Batch);


                            String q = qanty.getText().toString();
                            String j = batch.getText().toString();


                            if (z.length > 0 & !j.equals("") & !q.equals("")) {
                                listItema.get(z[0]).put("numliste", j);
                                listItema.get(z[0]).put("numfard", q);
                            } else if (z.length == 0 & !j.equals("") & !q.equals("")) {
                                map.put("numliste", j);
                                map.put("numfard", q);
                                listItema.add(map);
                            }

                            final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema,
                                    R.layout.item_fard, new String[]{"numliste", "numfard"}, new int[]{R.id.num1, R.id.num2});
                            List.setAdapter(mSchedule);
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            }


                    );

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            alertDialogBuilder = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setTitle("Suppression Ligne ")
                    .setCancelable(false)
                    .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id){

                            listItema.remove(z[0]);
                            final SimpleAdapter mSchedule = new SimpleAdapter(getActivity(), listItema,
                                    R.layout.item_fard, new String[]{"numliste", "numfard"}, new int[]{R.id.num1, R.id.num2});
                            List.setAdapter(mSchedule);

                        }

                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            }


                    );

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }

    }

    class UploadSoapDataAsynck extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog1;
        private String rest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1 = new ProgressDialog(getActivity());
            progressDialog1.setMessage("Merci de patienter...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
            int lng=List.getAdapter()==null?0:List.getAdapter().getCount();
            q=new StringBuilder();
            s=new StringBuilder();
            for(int i=0;i<=lng-1;i++){
                @SuppressWarnings({"unchecked"})
                HashMap<String, String> obj = (HashMap<String, String>) listItema.get(i);
                if(i<lng-1){
                    q.append(obj.get("numfard")+"&");
                    s.append(obj.get("numliste")+"&");}
                else if (i==lng-1){
                    q.append((String)obj.get("numfard"));
                    s.append((String) obj.get("numliste"));
                }
                System.out.println(q);

            }

            objectTest = new ObjectTest(
                    new String(q),
                    matricule.getText().toString()
                    ,lot.getText().toString()
                    ,"10"
                    ,article.getText().toString()
                    ,of.getText().toString(),new String(s));
        }
        //##########################################################################################

        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {
            String SOAP_ACTION = "http://connexion/insert_information";
            String METHOD_NAME = "insert_information";
            String NAMESPACE = "http://connexion";
            if(List.getAdapter()!=null) {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                request.addProperty("YQTY",new String(q));
                request.addProperty("YMAT", objectTest.getYMAT());
                request.addProperty("YLOT", objectTest.getYLOT());
                request.addProperty("YPAL", objectTest.getYPAL());
                request.addProperty("YITM", objectTest.getYITM());
                request.addProperty("YOF", objectTest.getYOF());
                request.addProperty("YSLO", new String(s));
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE a1 = new HttpTransportSE("http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl");
                a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                a1.debug = true;
                try {
                    a1.call(SOAP_ACTION, envelope);
                    System.out.println(envelope.getResponse().toString());

                } catch (NullPointerException exception) {

                } catch (XmlPullParserException e) {


                } catch (IOException e) {
                }
            }

            return "";

        }

        @Override
        protected void onPostExecute(String sResponse) {
            progressDialog1.dismiss();
            final AlertDialog.Builder alertee = new AlertDialog.Builder(getActivity());// decalartyion d'alerte dialogue
            if (    List.getAdapter()!=null
                    & !matricule.getText().toString().equals("")
                    & !article.getText().toString().equals("")
                    & !of.getText().toString().equals("")
                    & !capacite.getText().toString().equals("")
                    & !lot.getText().toString().equals("")){
                alertee.setTitle("ajout operation avec succés");
                alertee.setIcon(R.drawable.success);
                alertee.setMessage("Votre Operation a été bien enregistrée");
                alertee.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });}
            else {

                alertee.setTitle("Operation Avortée");
                alertee.setIcon(R.drawable.fail);
                alertee.setMessage("Veuillez Remplire les Champs manquants");
                alertee.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });


            }
            alertee.show();
        }



    }

}
