package com.vivek.recipeapp.di

import android.app.Application
import androidx.room.Room
import com.vivek.recipeapp.data.remote.gateway.RecipeApiService
import com.vivek.recipeapp.data.local.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRecipeSearchApi(): RecipeApiService {
        return Retrofit.Builder()
            .baseUrl(RecipeApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "stockdb.db"
        ).build()
    }

}