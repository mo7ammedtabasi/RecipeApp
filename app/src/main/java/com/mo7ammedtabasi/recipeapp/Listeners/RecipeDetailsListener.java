package com.mo7ammedtabasi.recipeapp.Listeners;

import android.os.Message;

import com.mo7ammedtabasi.recipeapp.models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response,String message);
    void didError(String message);
}
