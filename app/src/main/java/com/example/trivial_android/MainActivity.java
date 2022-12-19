package com.example.trivial_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button jugar;
    Button hack;
    TextView usuario;
    int notifID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.field_usuario);


        jugar = findViewById(R.id.btn_jugar);
        jugar.setOnClickListener((v) -> {
            String nombreUsuario = usuario.getText().toString();

            if(nombreUsuario.length()>10 || nombreUsuario.length()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error");
                builder.setMessage("La longitud del usuario debe de ser entre 1 y 10");

                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }else{
                Intent intent = new Intent(v.getContext(), Dificultades.class);
                intent.putExtra("usuario", nombreUsuario);
                startActivityForResult(intent, 0);
                finish();
            }
        });
        hack = findViewById(R.id.btn_hack);
        hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LanzarNotificación(view);
            }
        });

    }
    public void LanzarNotificación(View v) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Mi canal")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Oye, soy el admin")
                .setContentText("¿Por qué quieres hacer eso? Esta mal.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent resultadoIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder pila = TaskStackBuilder.create(this);
        pila.addParentStack(MainActivity.class);

        pila.addNextIntent(resultadoIntent);
        PendingIntent resultadoPendingIntent = pila.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultadoPendingIntent);


        NotificationManager notificador = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(
                    "Mi canal",
                    "título del canal de notificación",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificador.createNotificationChannel(canal);
        }

        notificador.notify(notifID, builder.build());
    }
}