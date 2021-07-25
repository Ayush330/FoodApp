package com.example.test.Favourites;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.R;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test.Database.FoodDataClass;
import com.example.test.Repository.LocalRepository;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<FoodDataClass> dataSet;
    private Activity activity;
    private LocalRepository repository;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CardView card;
        private ImageView image;
        private ImageFilterView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }


    public FoodAdapter(List<FoodDataClass> data, Application application)
    {
        this.dataSet = data;
        this.activity = activity;
        this.repository = new LocalRepository(application);
    }

    @NotNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FoodAdapter.ViewHolder holder, int position) {

        holder.title.setText(dataSet.get(position).title);
        Picasso.get()
                .load(dataSet.get(position).imageUrl)
                .fit()
                .centerCrop()
                .into(holder.image);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.delete(dataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
