package com.example.test.Search;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test.Database.FoodDataClass;
import com.example.test.POJO.Meal;
import com.example.test.R;
import com.example.test.Repository.LocalRepository;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Meal> dataSet;
    private Boolean checked;
    private LocalRepository repository;
    private Context context;
    private ArrayList<String> ingredients;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextInputEditText searchBarData;
        private ImageView image;
        private TextView title;
        private TextView ingredients;
        private ChipGroup chipGroup;
        private ImageView favourite;
        private FloatingActionButton searchButton;

        public ViewHolder(View itemView) {
            super(itemView);
            searchBarData = itemView.findViewById(R.id.searchBarData);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            ingredients = itemView.findViewById(R.id.ingredients);
            favourite = itemView.findViewById(R.id.favourite);
            // = itemView.findViewById(R.id.searchButton);
            chipGroup = itemView.findViewById(R.id.chipGroup);
        }
    }


    public SearchAdapter(List<Meal> data, Application application, Context context)
    {
        Log.i("Adapter","Adapter Called");
        this.dataSet = data;
        this.repository = new LocalRepository(application);
        this.checked = false;
        ingredients =  new ArrayList<String>();
        this.context = context;
    }

    @NotNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_search_fragment, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position)
    {
        Log.i("ViewHolder","ViewHolderCalled: "+dataSet.get(position).getStrMealThumb());
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked)
                {
                    holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_liked));
                    checked= true;

                        Meal meal = dataSet.get(position);
                        FoodDataClass data = new FoodDataClass(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb());
                        repository.insert(data);

                }
                else
                {
                    holder.favourite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    checked = false;
                        Meal meal = dataSet.get(position);
                        FoodDataClass data = new FoodDataClass(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb());
                        repository.delete(data);
                }
            }
        });

        holder.title.setText(dataSet.get(position).getStrMeal());
        holder.image.setClipToOutline(true);

        ingredients.clear();

        ingredients.add(dataSet.get(position).getStrIngredient1());
        ingredients.add(dataSet.get(position).getStrIngredient2());
        ingredients.add(dataSet.get(position).getStrIngredient3());
        ingredients.add(dataSet.get(position).getStrIngredient4());
        ingredients.add(dataSet.get(position).getStrIngredient5());

        // add data to the chipgroup
        for(int i = 0;i<ingredients.size();i++)
        {
            Chip chip = new Chip(context);
            chip.setText(ingredients.get(i));
            holder.chipGroup.addView(chip);
        }

        Picasso.get()
                .load(dataSet.get(position).getStrMealThumb())
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

