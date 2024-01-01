package com.example.mycar.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mycar.Model.Users;
import com.example.mycar.Prevalent.Prevalent;
import com.example.mycar.R;
import com.example.mycar.Service.Admin.AdminCapabilities;
import com.example.mycar.Service.User.UserCapabilities;
import com.example.mycar.ui.Users.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private  Button loginButton;
    private  Button regButton;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = new ProgressDialog(this);

        loginButton = (Button) findViewById(R.id.main_login_btn);
        regButton = (Button)  findViewById(R.id.main_reg_btn);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPassKey = Paper.book().read(Prevalent.UserPassKey);


        if (UserPhoneKey != "" && UserPassKey != "") {
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassKey)){

                ValidateUser(UserPhoneKey, UserPassKey);
                loadingBar.setTitle("Вход в приложение");
                loadingBar.setMessage("Пожалуйста подождите");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }


        }
    }

    private void ValidateUser(final String phone, final String pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists())
                {
                     Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                        if(userData.getPass().equals(pass))
                        {
                            userData.setPhone(phone);
                            Prevalent.currentOnLineUser = userData;
                            Prevalent.capabilities = Prevalent.currentOnLineUser.getPermissions() ? new AdminCapabilities() : new UserCapabilities();
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();
                            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(homeIntent);

                        }
                        else
                        {
                            loadingBar.dismiss();
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}