package com.example.mycar.ui.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycar.Prevalent.Prevalent;
import com.example.mycar.R;

public class SettingsActivity extends AppCompatActivity {

    private EditText name;
    private EditText phone;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init(){
        TextView saveChange = findViewById(R.id.save_settings_tv);
        TextView closeChange = findViewById(R.id.close_settings_tv);
        closeChange.setOnClickListener(view -> onBackPressed());

        name = findViewById(R.id.settings_fullname);
        name.setText(Prevalent.currentOnLineUser.getName());

        phone = findViewById(R.id.settings_phone);
        phone.setText(Prevalent.currentOnLineUser.getPhone());

        address = findViewById(R.id.settings_address);
        address.setText(Prevalent.currentOnLineUser.getAddress());

        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prevalent.capabilities.changeYourselfData(
                        SettingsActivity.this,
                        name.getText().toString(),
                        phone.getText().toString(),
                        address.getText().toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}