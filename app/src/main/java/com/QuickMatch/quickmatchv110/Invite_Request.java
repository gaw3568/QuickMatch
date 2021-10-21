package com.QuickMatch.quickmatchv110;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Invite_Request extends AppCompatActivity {

    private TextView invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite__request);

        invite = findViewById(R.id.invite_page_btn);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Invite_Request.this, invite_page.class);
                startActivity(intent);
            }
        });
    }
}
