package com.example.beerrecipes

import com.example.beerrecipes.entity.RecipeData
import retrofit2.Call
import retrofit2.http.*

interface BeerApi {

    @Headers("ContentType: application/json")
    @GET("beers?page=1&per_page=10")
    fun getBeerRecipes(): Call<ArrayList<RecipeData>>
}