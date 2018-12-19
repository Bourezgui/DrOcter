package com.integration.sra.drocter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class SharedpreferenceActivity extends AppCompatActivity {
    Button confirmer,annuler;
    private AutoCompleteTextView code,empl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreference);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        confirmer=(Button)findViewById(R.id.ajouter);
        annuler=(Button)findViewById(R.id.annulation);

        empl=(AutoCompleteTextView)findViewById(R.id.empl) ;
        code=(AutoCompleteTextView)findViewById(R.id.code);
        SharedPreferences prefs = getSharedPreferences("Sodet_Pref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = getSharedPreferences("Sodet_Pref", MODE_PRIVATE).edit();
        String sharedempl= prefs.getString("empl","");
        String sharedcode= prefs.getString("code","");

        code.setText(sharedcode);
        empl.setText(sharedempl);
        annuler.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SharedpreferenceActivity.this,
                                MainActivity.class));
                    }
                }
        );
        confirmer.setOnClickListener(

                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        editor.putString("empl",empl.getText().toString());
                        editor.putString("code",code.getText().toString());
                        editor.apply();
                        startActivity(new Intent(SharedpreferenceActivity.this,
                                MainActivity.class));
                    }
                }

        );

    }

}
