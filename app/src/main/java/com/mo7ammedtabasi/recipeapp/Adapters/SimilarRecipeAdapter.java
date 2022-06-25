package com.mo7ammedtabasi.recipeapp.Adapters;

import android.content.Context;
import android.hardware.lights.LightState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mo7ammedtabasi.recipeapp.Listeners.RecipeClickListener;
import com.mo7ammedtabasi.recipeapp.R;
import com.mo7ammedtabasi.recipeapp.models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<similarRecipeViewHolder>{
    Context context;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public similarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new similarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull similarRecipeViewHolder holder, int position) {
        holder.textView_similar_title.setText(list.get(position).title);
        holder.textView_similar_title.setSelected(true);
        holder.textView_similar_serving.setText(list.get(position).servings+" Person");
        Picasso.get().load("https://spoonacular.com/recipeImages/"+list.get(position).id+"-556x370."+list.get(position).imageType)
                .into(holder.imageView_similar);
        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClicked(String.valueOf(list.get(position).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class similarRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView similar_recipe_holder;
    TextView textView_similar_title,textView_similar_serving;
    ImageView imageView_similar;
    public similarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        similar_recipe_holder=itemView.findViewById(R.id.similar_recipe_holder);
        textView_similar_title=itemView.findViewById(R.id.textView_similar_title);
        textView_similar_serving=itemView.findViewById(R.id.textView_similar_serving);
        imageView_similar=itemView.findViewById(R.id.imageView_similar);
    }
}
