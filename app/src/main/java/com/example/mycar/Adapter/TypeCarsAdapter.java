package com.example.mycar.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.example.mycar.Enum.CarType;
import com.example.mycar.Model.Car;
import com.example.mycar.Prevalent.CarsPick;
import com.example.mycar.R;

import java.util.List;
import java.util.stream.Collectors;

public class TypeCarsAdapter extends RecyclerView.Adapter<TypeCarsAdapter.TypeCarsViewHolder> {
    private OnCarClickListener listener;


    private Context context;

    public TypeCarsAdapter(OnCarClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public TypeCarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_type, parent, false);
        return new TypeCarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeCarsAdapter.TypeCarsViewHolder holder, int position) {
        Car car = CarsPick.cars.get(position);
        holder.bind(car);
    }

    @Override
    public int getItemCount() {
        return CarsPick.cars.size();
    }

    public class TypeCarsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView carView;

        public TypeCarsViewHolder(@NonNull View itemView) {
            super(itemView);
            carView = itemView.findViewById(R.id.car_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Car car) {
            Glide.with(context)
                    .load(car.getImageUrl())
                    .into(new CustomTarget<Drawable>() {

                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
                            carView.setForeground(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Очистка загрузки, если необходимо
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onCarClick(position);
        }
    }

    public interface OnCarClickListener {
        void onCarClick(int position);
    }
}
