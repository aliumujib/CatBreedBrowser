package com.aliumujib.network

import com.aliumujib.network.model.BreedAPIModel
import com.aliumujib.network.model.BreedImageAPIModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatAPIService {

    @GET("breeds")
    suspend fun getBreeds(): List<BreedAPIModel>

    @GET("breeds/{breedId}")
    suspend fun getBreedById(
        @Path("breedId") breedId: String,
        @Query("attach_image") attachImage: Int,
    ): BreedAPIModel

}
