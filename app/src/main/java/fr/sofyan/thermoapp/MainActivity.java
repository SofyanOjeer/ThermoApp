package fr.sofyan.thermoapp;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends Activity {
    DatabaseReference mydb;
    TextView temp,hum,air,infoAir,volet,infoHum;
    ImageButton btLogin;
    Button btSecondMainActivity;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        temp=(TextView)findViewById(R.id.temp);
        hum=(TextView)findViewById(R.id.hum);
        infoHum=(TextView)findViewById(R.id.infoHum);
        air=(TextView)findViewById(R.id.air);
        //volet=(TextView)findViewById(R.id.volet);
        infoAir=(TextView)findViewById(R.id.infoAir);
        btLogin=(ImageButton)findViewById(R.id.btLogin);
        btSecondMainActivity = findViewById(R.id.btSecondMainActivity);
        mydb= FirebaseDatabase.getInstance().getReference().child("Sensor");

        try {
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String tempdata = dataSnapshot.child("temp").getValue().toString();
                    String humdata = dataSnapshot.child("hum").getValue().toString();
                    String airdata = dataSnapshot.child("air").getValue().toString();
                    //String voletdata = dataSnapshot.child("volet").getValue().toString();
                    temp.setText(tempdata + "°C");
                    hum.setText(humdata + "%");
                    air.setText(airdata + "%");


                    int humdatanumber = Integer.parseInt(humdata);

                    if (humdatanumber >= 70) {
                        infoHum.setText("Attention l'humidité est élevée");
                    } else {
                        infoHum.setText("");
                    }

                    int airdatanumber = Integer.parseInt(airdata);

                    if (airdatanumber >= 70) {
                        infoAir.setText("Très bonne");
                    } else if (airdatanumber >= 40 && airdatanumber < 70) {
                        infoAir.setText("Bonne" + "%");
                    } else if (airdatanumber < 40) {
                        infoAir.setText("Mauvaise");
                    }


                    //if (voletdata == "true"){
                      //  volet.setText("Les volets sont fermés");
                    //}
                    //else{
                      //  volet.setText("Les volets sont ouverts");
                    //}



                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }

        catch(Exception e)
        {
        }


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btSecondMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentNext = new Intent(MainActivity.this,SecondMainActivity.class);
                startActivity(intentNext);
            }
        });

    }
}