package com.mo7ammedtabasi.recipeapp.Listeners;

import com.mo7ammedtabasi.recipeapp.models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
