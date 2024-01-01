package com.example.mycar.Service.User;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycar.Enum.CarType;
import com.example.mycar.Model.Car;
import com.example.mycar.Model.Order;
import com.example.mycar.Model.Users;
import com.example.mycar.Prevalent.CarsPick;
import com.example.mycar.Prevalent.Orders;
import com.example.mycar.Prevalent.Prevalent;
import com.example.mycar.ui.Admin.AdminAddNewProductActivity;
import com.example.mycar.ui.Users.CarActivity;
import com.example.mycar.ui.Users.CarTypeActivity;
import com.example.mycar.ui.Users.HomeActivity;
import com.example.mycar.ui.Users.OrdersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class UserCapabilities{
    protected final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
    protected final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders");
    protected final DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Products");

    public void changeYourselfData(Activity activity, String name, String phone, String address){

        Paper.init(activity);
        if(!name.isEmpty())
            userRef.child(Prevalent.currentOnLineUser.getPhone()).child("name").setValue(name);
        if(!address.isEmpty())
            userRef.child(Prevalent.currentOnLineUser.getPhone()).child("address").setValue(address);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child(phone).exists()){
                    if(!phone.equals(Prevalent.currentOnLineUser.getPhone())){
                        //замена номера в key
                        Object value = snapshot.child(Prevalent.currentOnLineUser.getPhone()).getValue(Users.class);
                        userRef.child(Prevalent.currentOnLineUser.getPhone()).removeValue();
                        userRef.child(phone).setValue(value);
                        Prevalent.currentOnLineUser.setAddress(address);
                        Prevalent.currentOnLineUser.setName(name);

                        updateOrdersPhone(Prevalent.currentOnLineUser.getPhone(), phone);
                        Paper.book().write(Prevalent.UserPhoneKey, phone);
                        Prevalent.currentOnLineUser.setPhone(phone);
                        Toast.makeText(activity, "Данные изменены", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(activity, "Телефон уже занят другим человеком", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateOrdersPhone(String oldPhone, String newPhone){
        if(!oldPhone.equals(newPhone)) {
            orderRef.child(oldPhone).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Order order = child.getValue(Order.class);
                        order.setPhone(newPhone);
                        order.setTime(child.getKey());

                        HashMap<String, Object> orderCreating = new HashMap<>();
                        orderCreating.put("nameUser", order.getNameUser());
                        orderCreating.put("category", order.getCategory());
                        orderCreating.put("productPrice", order.getProductPrice());
                        orderCreating.put("productName", order.getProductName());
                        orderCreating.put("status", order.getStatus());
                        orderCreating.put("productId", order.getProductId());

                        orderRef.child(newPhone).child(order.getTime()).updateChildren(orderCreating).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    orderRef.child(oldPhone).child(child.getKey()).removeValue();
                                }

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void onClckCategory(Activity activity, CarType category, RecyclerView.Adapter adapter) {

        productRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CarsPick.cars.clear();
                for(DataSnapshot car: snapshot.getChildren()){
                    Car item = car.getValue(Car.class);
                    item.setKey(car.getKey());
                    if(item.getCategory().equals(category))
                        CarsPick.cars.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sendOrder(CarActivity carActivity, Car car) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm-ss");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        HashMap<String, Object> orderCreating = new HashMap<>();

        orderCreating.put("nameUser", Prevalent.currentOnLineUser.getName());
        orderCreating.put("category", CarType.valueOfStr(car.getCategory()));
        orderCreating.put("productPrice", car.getProductPrice());
        orderCreating.put("productName", car.getProductName());
        orderCreating.put("status", "wait");
        orderCreating.put("productId", car.getKey());

        orderRef.child(Prevalent.currentOnLineUser.getPhone()).child(saveCurrentDate + " " + saveCurrentTime).updateChildren(orderCreating).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(carActivity, "Заказ сформирован", Toast.LENGTH_SHORT).show();
                    carActivity.finish();

                } else {
                    String message = task.getException().toString();
                    Toast.makeText(carActivity, "Ошибка: " + message, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void getOrdersUser(Activity activity, ProgressDialog loadingBar, String phone, RecyclerView.Adapter adapter){
        orderRef.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Orders.myOrder.clear();
                for(DataSnapshot orderItem: snapshot.getChildren()){
                    Order order = orderItem.getValue(Order.class);
                    order.setTime(orderItem.getKey());
                    order.setPhone(snapshot.getKey());
                    Orders.myOrder.add(order);
                }

                adapter.notifyDataSetChanged();
                loadingBar.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateStatus(String date, String phone, String answer){
    }
}
