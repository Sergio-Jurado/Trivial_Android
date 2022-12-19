package com.example.trivial_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PreguntasD extends AppCompatActivity {
    private int ids_respuestas[] = {
            R.id.answer1D, R.id.answer2D, R.id.answer3D, R.id.answer4D
    };
    private int respuesta_correcta;
    private int pregunta_actual;
    private String[] total_preguntas;
    private boolean[] correcta;
    private int[] respuesta;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_next, btn_prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_d);

        text_question = findViewById(R.id.text_questionD);
        group = findViewById(R.id.answer_groupD);
        btn_next = findViewById(R.id.btn_checkD);
        btn_prev = findViewById(R.id.btn_prevD);

        total_preguntas = getResources().getStringArray(R.array.preguntas_dificiles);
        correcta = new boolean[total_preguntas.length];
        respuesta = new int[total_preguntas.length];
        for (int i = 0; i < respuesta.length; i++) {
            respuesta[i] = -1;
        }
        pregunta_actual = 0;
        mostrar();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRespuesta();
                if (pregunta_actual < total_preguntas.length-1) {
                    pregunta_actual++;
                    mostrar();
                } else {
                    int correctas = 0, incorrectas = 0;
                    for (boolean b : correcta) {
                        if (b) correctas++;
                        else incorrectas++;
                    }
                    String resultado =
                            String.format(Locale.getDefault(), "Correctas: %d -- Incorrectas: %d", correctas, incorrectas);

                    Toast.makeText(PreguntasD.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRespuesta();
                if (pregunta_actual > 0) {
                    pregunta_actual--;
                    mostrar();
                }
            }
        });
    }

    private void checkRespuesta() {
        int id = group.getCheckedRadioButtonId();
        int ans = -1;
        for (int i = 0; i < ids_respuestas.length; i++) {
            if (ids_respuestas[i] == id) {
                ans = i;
            }
        }
        correcta[pregunta_actual] = (ans == respuesta_correcta);
        respuesta[pregunta_actual] = ans;
    }

    private void mostrar() {
        String q = total_preguntas[pregunta_actual];
        String[] parts = q.split(";");

        group.clearCheck();

        text_question.setText(parts[0]);
        for (int i = 0; i < ids_respuestas.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_respuestas[i]);
            String ans = parts[i+1];
            if (ans.charAt(0) == '*') {
                respuesta_correcta = i;
                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (respuesta[pregunta_actual] == i) {
                rb.setChecked(true);
            }
        }
        if (pregunta_actual == 0) {
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        if (pregunta_actual == total_preguntas.length-1) {
            btn_next.setText("Terminar");
        } else {
            btn_next.setText("Siguiente");
        }
    }
}