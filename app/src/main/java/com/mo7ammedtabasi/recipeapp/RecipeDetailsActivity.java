package com.mo7ammedtabasi.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mo7ammedtabasi.recipeapp.Adapters.IngredientsAdapter;
import com.mo7ammedtabasi.recipeapp.Adapters.InstructionAdapter;
import com.mo7ammedtabasi.recipeapp.Adapters.SimilarRecipeAdapter;
import com.mo7ammedtabasi.recipeapp.Listeners.InstructionsListener;
import com.mo7ammedtabasi.recipeapp.Listeners.RecipeClickListener;
import com.mo7ammedtabasi.recipeapp.Listeners.RecipeDetailsListener;
import com.mo7ammedtabasi.recipeapp.Listeners.SimilarRecipeListener;
import com.mo7ammedtabasi.recipeapp.models.InstructionsResponse;
import com.mo7ammedtabasi.recipeapp.models.RecipeDetailsResponse;
import com.mo7ammedtabasi.recipeapp.models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {

    TextView textView_meal_name,textView_meal_source,textView_meal_summery;
    ImageView imageView_mail_image;
    RecyclerView recycler_meal_ingredients,recycler_meal_similar,recycler_meal_instructions;
    RequestManger manger;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionAdapter instructionAdapter;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        findViews();
        id=Integer.parseInt(getIntent().getStringExtra("id"));
        manger=new RequestManger(this);
        manger.getRecipeDetails(recipeDetailsListener,id);
        manger.getSimilarRecipe(similarRecipeListener,id);
        manger.getInstructions(instructionsListener,id);
        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    private void findViews() {
        textView_meal_name=findViewById(R.id.textView_meal_name);
        textView_meal_source=findViewById(R.id.textView_meal_source);
        textView_meal_summery=findViewById(R.id.textView_meal_summery);
        imageView_mail_image=findViewById(R.id.imageView_mail_image);
        recycler_meal_ingredients=findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar=findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions=findViewById(R.id.recycler_meal_instructions);
    }

    private final RecipeDetailsListener recipeDetailsListener=new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summery.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_mail_image);
            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
            ingredientsAdapter=new IngredientsAdapter(getApplicationContext(),response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    };
    private final SimilarRecipeListener similarRecipeListener=new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,RecyclerView.HORIZONTAL,false));
            similarRecipeAdapter=new SimilarRecipeAdapter(RecipeDetailsActivity.this,response,recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private final RecipeClickListener recipeClickListener=new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this,RecipeDetailsActivity.class)
            .putExtra("id",id));
        }
    };

    private final InstructionsListener instructionsListener=new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL,false));
            instructionAdapter=new InstructionAdapter(RecipeDetailsActivity.this,response);
            recycler_meal_instructions.setAdapter(instructionAdapter);
        }

        @Override
        public void didError(String message) {

        }
    };
}