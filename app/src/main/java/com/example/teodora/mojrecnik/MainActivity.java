package com.example.teodora.mojrecnik;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private TextView izgovorenaRec;
    String izgovorenaRecUStringu;
    private TextView definicijaReci;
    String definicijaReciUStringu;
    private Button ubaciBtn;
    private ImageView mojSpisak;
    private ImageButton mikrofonDugme;

    private RecnikHelper recnikHelper;
    private boolean daLiJeUbaceno;
    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        izgovorenaRec = (TextView) findViewById(R.id.voiceInput);
        definicijaReci = (TextView) findViewById(R.id.engleski_textview);
        ubaciBtn = (Button)findViewById(R.id.ubaciBtn);
        mojSpisak = (ImageView)findViewById(R.id.mojSpisak);
        mikrofonDugme = (ImageButton) findViewById(R.id.btnSpeak);

        definicijaReci.setMovementMethod(new ScrollingMovementMethod());       // da moze tekst da se skroluje


        mikrofonDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startVoiceInput();

            }
        });

        ubaciBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (izgovorenaRecUStringu == null) {
                    Toast.makeText(getApplicationContext(),"Prvo pritisnite mikorofon i izgovorite reč.",Toast.LENGTH_SHORT).show();
                }
                else {

                    definicijaReciUStringu = definicijaReci.getText().toString();

                    recnikHelper = new RecnikHelper(getApplicationContext());

                    //ako vec ima reci u bazi nemoj je dodati
                    if (recnikHelper.daLiPostojiRec(izgovorenaRecUStringu)) {
                        Toast.makeText(getApplicationContext(), "Reč već postoji u spisku.",Toast.LENGTH_SHORT).show();
                    }

                    else {

                        daLiJeUbaceno = recnikHelper.ubaciUBazu(izgovorenaRecUStringu, definicijaReciUStringu);

                        if (daLiJeUbaceno) {
                            Toast.makeText(getApplicationContext(), "Ubačeno u Moj spisak", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Greška", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mojSpisak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ucitajListaActivity();

            }
        });

    }

    private void startVoiceInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Zdravo, kako ti mogu pomoci?");

        try {

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);  // ovaj req_code, ako je >=0 onda ce se rez vratiti kao resultCode u fji onActivityResult  ispod

        } catch (ActivityNotFoundException a) { }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {


            case REQ_CODE_SPEECH_INPUT: {


                if (resultCode == RESULT_OK && null != data) {


                    izgovorenaRec.setAlpha(1);


                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    izgovorenaRecUStringu = result.get(0);
                    izgovorenaRec.setText(izgovorenaRecUStringu);

                    url = dictionaryEntries(izgovorenaRecUStringu);

                    RecnikZahtev zahtev = new RecnikZahtev(definicijaReci);  // u konstruktor sam stavila textView defReci da bih tamo mogla da postavim defReci
                    zahtev.execute(url);

                }
                break;
            }
        }
    }


    //ova fja mi daje url koji treba da se posalje Oxford api-ju
    private String dictionaryEntries(String zadataRec) {
        final String language = "en";

        final String word_id = zadataRec.toLowerCase();

        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }



    private void ucitajListaActivity(){

        Intent intent = new Intent(MainActivity.this, ListaActivity.class);
        startActivity(intent);

    }


}

