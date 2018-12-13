package com.integration.sra.drocter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.orm.SugarRecord;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
    OrdreFabrication fabrication;
    //Definition des propreités
    private AutoCompleteTextView matricule,article,of,palette,capacite,lot;
    private OrdreFabrication basedonne;
    private static Dialog alerte;
    public Dialog alerte_article,alert_quantit;
    private Button ajouter;
    public Dialog alerte_matricule,alerte_of;
    public EditText searcharticle;
    public EditText searchmatricule,searchof;
    ListView list_article,list_matricule,list_of;
    private EditText recherche;
    private StringBuilder s,q;
    private ListView listnum;
    private Set<String> tableofparams,tableofparams1,tableofparams2;
    private HashMap<String,String> maps;
    final ArrayList<Map<String, String>> listItema=new ArrayList<>();
    private ListView List ;




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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiation des Objet definis au debut de la class
        recherche= findViewById(R.id.recherche);
        matricule=findViewById(R.id.matricule);
        article=findViewById(R.id.article);
        of=findViewById(R.id.of);
        palette=findViewById(R.id.palette);
        capacite=findViewById(R.id.capacite);
        lot=findViewById(R.id.lot);
        List = findViewById(R.id.fard);
        ajouter=findViewById(R.id.ajouter);
        alerte=new Dialog(MainActivity.this);
        alerte_article=new Dialog(MainActivity.this);
        alert_quantit=new Dialog(MainActivity.this);
        alerte_matricule=new Dialog(MainActivity.this);
        alerte_of=new Dialog(MainActivity.this);
        alerte_article.setContentView(R.layout.listart);
        alerte_matricule.setContentView(R.layout.listart);
        //alert_quantit.setContentView(R.layout.alertquantite);

        alerte_of.setContentView(R.layout.listart);
        searcharticle= alerte_article.findViewById(R.id.recherche);
        searchmatricule= alerte_matricule.findViewById(R.id.recherche);
        searchof= alerte_of.findViewById(R.id.recherche);
        alerte.setContentView(R.layout.listnum);
        list_article= alerte_article.findViewById(R.id.list1);
        list_matricule=alerte_matricule.findViewById(R.id.list1);
        list_of= alerte_of.findViewById(R.id.list1);
        listnum = alerte.findViewById(R.id.list);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message="Enregistrement Ajouté à la base local avec Succée";
                /*java.util.List<OrdreFabrication> listordrer = OrdreFabrication.listAll(OrdreFabrication.class);
                for(OrdreFabrication o : listordrer){
                    System.out.println("l'article est "+o.getArticle());System.out.println("flag est "+o.getFlg());}
                    //
*/
                new UploadSoapDataAsynck().execute();

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
        new DownloadJSONLISTUNT(
                "http://196.203.89.113:8077/Sodet2-0.0.1-SNAPSHOT/services/soap_sodet_beta?wsdl"
                ,"http://connexion/Listof"
                ,new int[]{R.id.numfard}
                ,list_of
                ,searchof
                ,R.layout.item_listecol
                ,"Listof"
                ,"GRP1"
                ,MainActivity.this
                ,tableofparams2
                ,"http://connexion"
                ,maps).execute();

        of.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showalertelistof();
                return true;
            }
        });

        new DownloadJSONLISTUNT(
                "http://196.203.89.113:8077/Sodet2-0.0.1-SNAPSHOT/services/soap_sodet_beta?wsdl"
                ,"http://connexion/Listmatricule"
                ,new int[]{R.id.numfard}
                ,list_matricule
                ,searchmatricule
                ,R.layout.item_listecol
                ,"Listmatricule"
                ,"GRP1"
                ,MainActivity.this
                ,tableofparams1
                ,"http://connexion"
                ,maps).execute();

        matricule.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showalertelistmat();
                return true;
            }
        });




        //Selectionner Article
        new DownloadJSONLISTUNT(
                "http://196.203.89.113:8077/Sodet2-0.0.1-SNAPSHOT/services/soap_sodet_beta?wsdl"
                ,"http://connexion/ListArticle"
                ,new int[]{R.id.numfard}
                ,list_article
                ,searcharticle
                ,R.layout.item_listecol
                ,"ListArticle"
                ,"GRP1"
                ,MainActivity.this
                ,tableofparams
                ,"http://connexion"
                ,maps).execute();
        article.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {

                confirm();
                String message="Enregistrement Ajouté à la base local avec Succée";
                Snackbar.make(view,message, Snackbar.LENGTH_LONG)
                        .setAction(message, null).show();
            }

        });

    }
         public static void selectionitem(String nom){
             alerte.setTitle("Liste de"+nom);
             alerte.show();
         }

     class UploadSoapDataAsynck extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog1;
        private String rest;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(MainActivity.this);
            progressDialog1.setMessage("Merci de patienter...");
            progressDialog1.setIndeterminate(true);
            progressDialog1.show();
            int lng=List.getAdapter()==null?0:List.getAdapter().getCount();


            q=new StringBuilder();
            s=new StringBuilder();
            for(int i=0;i<=lng-1;i++){
                @SuppressWarnings({"unchecked"})
                HashMap<String, Object> obj = (HashMap<String, Object>) List.getAdapter().getItem(i);
                if(i<lng-1 & obj.get("numfard") != null){
                    q.append(obj.get("numfard&"));
                    s.append(obj.get("numliste&"));}
                else if (i==lng-1){
                    q.append((String)obj.get("numfard"));
                    s.append((String) obj.get("numliste"));
                }
            }

            fabrication = new OrdreFabrication(article.getText().toString()
                    ,of.getText().toString(),matricule.getText().toString()
                    , palette.getText().toString()
                    ,capacite.getText().toString()
                    ,lot.getText().toString()
                    ,new String(q),new String(s));

        }


        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unsued) {
            InputStream is;
            {
                String URL = "http://192.168.1.191:8081/Sodet2/services/soap_sodet_beta?wsdl";
                String SOAP_ACTION = "http://connexion/insert_information";
                String METHOD_NAME = "insert_information";
                String NAMESPACE = "http://connexion";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                request.addProperty("YQTY", fabrication.getQty());
                request.addProperty("YMAT", fabrication.getMatricule());
                request.addProperty("YLOT", fabrication.getLot());
                request.addProperty("YPAL", fabrication.getIdpalette());
                request.addProperty("YITM", fabrication.getArticle());
                request.addProperty("YOF", fabrication.getOf());
                request.addProperty("YSLO", fabrication.getBatch());
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE a1 = new HttpTransportSE(URL);
                a1.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                a1.debug = true;
                try {
                    a1.call(SOAP_ACTION, envelope);
                    rest = envelope.getResponse().toString();
                    System.out.println("reponse : " + rest);

                    if (rest.equals("1")) {
                        Log.i("succés", "succés");
                    } else if (rest.equals("0")) {

                        Log.i("erreur", "erreur");


                    }


                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d("erreur", e + "");

                    e.printStackTrace();
                } catch (XmlPullParserException e) {


                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    fabrication.setFlg(Flag.NotSent);
                }
                finally {
                    fabrication.save();
                }
                return "Success";

            }

        }
        @Override
        protected void onPostExecute(String sResponse) {
            progressDialog1.dismiss();

            final AlertDialog.Builder alertee = new AlertDialog.Builder(MainActivity.this);// decalartyion d'alerte dialogue
            alertee.setTitle("ajout operation avec succés");
            alertee.setIcon(R.drawable.success);

            alertee.setMessage("Votre Operation a été bien enregistrée");
            alertee.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertee.show();

        }

    }

    public void confirm() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        final View mview = getLayoutInflater().inflate(R.layout.alertquantite,null);
        final Map<String,String> map=new HashMap<>();
        alertDialogBuilder.setTitle("Ajout Ligne");
        alertDialogBuilder
                .setView(mview)
                .setCancelable(false)
                .setPositiveButton("Ajouter",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        final EditText qanty=mview.findViewById(R.id.qty);
                        final EditText batch=mview.findViewById(R.id.Batch);
                        String q=qanty.getText().toString();
                        String j=batch.getText().toString();
                        map.put("numliste",j);
                        map.put("numfard",q);
                        listItema.add(map);
                        final SimpleAdapter mSchedule = new SimpleAdapter(MainActivity.this, listItema,
                                R.layout.item_fard, new String[]{"numliste","numfard"}, new int[]{R.id.num1,R.id.num2});
                        List.setAdapter(mSchedule);
                    }
                })
                .setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                }




                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();



        // show it
        alertDialog.show();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);
        return true; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this,
                        MainActivity.class));
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }



}


