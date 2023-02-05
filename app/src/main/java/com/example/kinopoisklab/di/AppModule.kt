package com.example.kinopoisklab.di

import android.content.Context
import com.example.kinopoisklab.data.local.AppDatabase
import com.example.kinopoisklab.data.local.FavoriteDao
import com.example.kinopoisklab.data.remote.FilmRemoteDataSource
import com.example.kinopoisklab.data.remote.FilmService
import com.example.kinopoisklab.data.repository.FilmRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit {

            val headerInterceptor: Interceptor = Interceptor { chain ->
                var request: Request = chain.request()

                request =
                    request.newBuilder().addHeader("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b").build()
                val response: Response = chain.proceed(request)
                response
            }

            val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)

       return Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech/api/v2.2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttp.build())
        .build()
        }
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): FilmService = retrofit.create(FilmService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(characterService: FilmService) = FilmRemoteDataSource(characterService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: FilmRemoteDataSource,
                          localDataSource: FavoriteDao) =
        FilmRepository(remoteDataSource, localDataSource)
}