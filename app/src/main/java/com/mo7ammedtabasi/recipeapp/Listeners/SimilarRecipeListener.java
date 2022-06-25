package com.mo7ammedtabasi.recipeapp.Listeners;

import com.mo7ammedtabasi.recipeapp.models.RecipeDetailsResponse;
import com.mo7ammedtabasi.recipeapp.models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);
}
