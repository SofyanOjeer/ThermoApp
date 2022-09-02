package fr.sofyan.thermoapp;


import androidx.appcompat.app.AppCompatActivity;

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

public class SecondMainActivity extends AppCompatActivity {

    DatabaseReference mydb;
    TextView gaz,son;
    ImageButton btLogin;
    Button btMainActivity;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        mAuth = FirebaseAuth.getInstance();
        gaz=(TextView)findViewById(R.id.gaz);
        son=(TextView)findViewById(R.id.son);
        btLogin=(ImageButton)findViewById(R.id.btLogin);
        btMainActivity = findViewById(R.id.btMainActivity);
        mydb= FirebaseDatabase.getInstance().getReference().child("Sensor");

        try {
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String gazdata = dataSnapshot.child("gaz").getValue().toString();
                    String sondata = dataSnapshot.child("son").getValue().toString();
                    gaz.setText(gazdata + "ppm");
                    son.setText(sondata + "db");

                    /*
                    if (sondata == "true") {
                        son.setText("Il y a du son");
                    }
                    else {
                        son.setText("Il n'y a pas de son");
                    }*/
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
                Intent intent = new Intent(SecondMainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intentNext = new Intent(SecondMainActivity.this,MainActivity.class);
                startActivity(intentNext);
            }
        });

    }
}