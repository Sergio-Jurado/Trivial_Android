package com.example.trivial_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Dificultades extends AppCompatActivity {

    Button facil;
    Button intermedio;
    Button dificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificultades);

        facil = findViewById(R.id.btn_facil);
        facil.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Preguntas.class);
            startActivityForResult(intent, 0);
        });

        intermedio = findViewById(R.id.btn_intermedia);
        intermedio.setOnClickListener((v) -> {
            Intent intent = new Intent(v.getContext(), PreguntasI.class);
            startActivityForResult(intent, 0);
        });

        dificil = findViewById(R.id.btn_dificil);
        dificil.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PreguntasD.class);
            startActivityForResult(intent, 0);
        });
    }
}