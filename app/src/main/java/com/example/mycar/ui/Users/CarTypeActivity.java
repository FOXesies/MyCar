package com.example.mycar.ui.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycar.Adapter.TypeCarsAdapter;
import com.example.mycar.Enum.CarType;
import com.example.mycar.Model.Car;
import com.example.mycar.Prevalent.CarsPick;
import com.example.mycar.Prevalent.Prevalent;
import com.example.mycar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarTypeActivity extends AppCompatActivity implements TypeCarsAdapter.OnCarClickListener {

    private TextView title;
    private TextView infoType;
    private RecyclerView carsView;
    private ImageView imageView;
    private List<Car> filteredList = new ArrayList<>();
    private CarType desiredType;

    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_car_type);

        init();

        title.setText(getIntent().getStringExtra("category"));
        infoType.setText(CarType.getTextForType(
                getResources().getStringArray(R.array.text_carTypes), //Список описания каждого типа из res/string
                title.getText().toString())
        );

        desiredType = CarType.valueOfCar(title.getText().toString());

        adapter = new TypeCarsAdapter(this::onCarClick, this);
        Prevalent.capabilities.onClckCategory(CarTypeActivity.this, desiredType, adapter);

        carsView.setAdapter(adapter);
        carsView.setLayoutManager(new LinearLayoutManager(CarTypeActivity.this, RecyclerView.VERTICAL, false));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void init(){
        title = findViewById(R.id.type_car);
        infoType = findViewById(R.id.info_type);
        carsView = findViewById(R.id.cars_list);
        imageView = findViewById(R.id.imageView);

    }

    @Override
    public void onCarClick(int position) {
        Intent intent = new Intent(CarTypeActivity.this, CarActivity.class);
        Car curval = (CarsPick.cars.stream()
                .filter(car -> car.getCategory() == desiredType)
                .collect(Collectors.toList())).get(position);
        int id = CarsPick.cars.indexOf(curval);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}