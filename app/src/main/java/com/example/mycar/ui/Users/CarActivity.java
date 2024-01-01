package com.example.mycar.ui.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mycar.Enum.CarType;
import com.example.mycar.Model.Car;
import com.example.mycar.Prevalent.CarsPick;
import com.example.mycar.Prevalent.Prevalent;
import com.example.mycar.R;

public class CarActivity extends AppCompatActivity {

    TextView type, name, price, description, id;
    Button acceptOrder;
    ImageView imageCar, imageView;
    private int idCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        idCar = getIntent().getIntExtra("id", -1);
        Car car = CarsPick.cars.get(idCar);
        init();

        type.setText(CarType.valueOfStr(car.getCategory()));
        name.setText("Название: " + car.getProductName());
        price.setText("Цена: " + car.getProductPrice());
        description.setText("Описание: " + car.getDescription());
        id.setText("Номер в базе: " + car.getKey());
        description.setText("Описание: " + car.getDescription());
        Glide.with(this)
                .load(car.getImageUrl())
                .into(imageCar);

        if(Prevalent.currentOnLineUser.getPermissions())
            id.setVisibility(View.VISIBLE);

        acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog loadingBar = new ProgressDialog(CarActivity.this);
                loadingBar.setTitle("Загрузка данных");
                loadingBar.setMessage("Пожалуйста подождите");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Prevalent.capabilities.sendOrder(CarActivity.this, car);
                loadingBar.dismiss();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init(){
        type = findViewById(R.id.car_type);
        name = findViewById(R.id.car_name);
        price = findViewById(R.id.car_price);
        description = findViewById(R.id.car_description);
        id = findViewById(R.id.car_id);

        imageCar = findViewById(R.id.imageCar);
        imageView = findViewById(R.id.imageView);

        acceptOrder = findViewById(R.id.accept_order);
    }
}