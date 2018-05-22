package com.wia.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wia.R;

public class ContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        ActionBar ab = getSupportActionBar();

        //ab.setTitle("Contato");
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        LinearLayout linearAdryel = findViewById(R.id.emailLink);
        linearAdryel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sugestões WIA");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"WIA.SAapp@gmail.com"});
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Send Email"));
            }
        });
    }
}
