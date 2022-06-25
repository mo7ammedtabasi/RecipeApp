package com.mo7ammedtabasi.recipeapp.Listeners;

import com.mo7ammedtabasi.recipeapp.models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);
    void didError(String message);
}
