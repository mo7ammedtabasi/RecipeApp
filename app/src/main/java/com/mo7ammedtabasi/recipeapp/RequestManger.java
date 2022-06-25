package com.mo7ammedtabasi.recipeapp;

import android.content.Context;
import android.provider.ContactsContract;

import com.mo7ammedtabasi.recipeapp.Listeners.InstructionsListener;
import com.mo7ammedtabasi.recipeapp.Listeners.RandomRecipeResponseListener;
import com.mo7ammedtabasi.recipeapp.Listeners.RecipeDetailsListener;
import com.mo7ammedtabasi.recipeapp.Listeners.SimilarRecipeListener;
import com.mo7ammedtabasi.recipeapp.models.InstructionsResponse;
import com.mo7ammedtabasi.recipeapp.models.RandomRecipeApiResponse;
import com.mo7ammedtabasi.recipeapp.models.RecipeDetailsResponse;
import com.mo7ammedtabasi.recipeapp.models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RequestManger {

    Context context;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManger(Context context) {
        this.context = context;
    }

    public void  getRandomRecipe(RandomRecipeResponseListener listener,List<String> tags){
        CallRandomRecipe callRandomRecipe=retrofit.create(CallRandomRecipe.class);
        Call<RandomRecipeApiResponse> call= callRandomRecipe.callRandomRecipe(context.getString(R.string.api_key),"100",tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipe(SimilarRecipeListener listener,int id){
        CallSimilarRecipe callSimilarRecipe=retrofit.create(CallSimilarRecipe.class);
        Call<List<SimilarRecipeResponse>> call=callSimilarRecipe.callSimilarRecipe(id,"10", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void getInstructions(InstructionsListener listener,int id){

        CallInstructions callInstructions=retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call=callInstructions.callInstructor(id,String.valueOf(context.getString(R.string.api_key)));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private  interface CallRandomRecipe{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags")List<String> tags
                );
    }
    public void getRecipeDetails(RecipeDetailsListener listener,int id){
        CallRecipeDetails callRecipeDetails=retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call=callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


     private  interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
     }
     private interface CallSimilarRecipe{
        @GET("recipes/{id}/similar")
         Call<List<SimilarRecipeResponse>> callSimilarRecipe(
                 @Path("id") int id,
                 @Query("number") String number,
                 @Query("apiKey") String apiKey
        );
     }
     private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
         Call<List<InstructionsResponse>> callInstructor(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
     }
}
