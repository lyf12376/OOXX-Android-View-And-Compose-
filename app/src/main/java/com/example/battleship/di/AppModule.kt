package com.example.battleship.di

import android.content.Context
import com.coder.vincent.sharp_retrofit.call_adapter.flow.FlowCallAdapterFactory
import com.example.battleship.localDatabase.game.GameDao
import com.example.battleship.localDatabase.game.GameDatabase
import com.example.battleship.localDatabase.savedUser.SavedUserDatabase
import com.example.battleship.network.GameHistoryService
import com.example.battleship.network.UserService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val LocalUrl = "http://192.168.1.105:8080"

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor = loggingInterceptor)
            .build()
    }

    /**
     * 在目录中使用Hilt提供多个相对类型的实例对象，通过"@Named"注解进行区分
     * 也可以通过"@Qualifier"限定符定义新的注解进行区分*/

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LocalUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGameHistoryService(retrofit: Retrofit):GameHistoryService{
        return retrofit.create(GameHistoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideSavedUserDatabase(@ApplicationContext context: Context): SavedUserDatabase {
        return SavedUserDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideSavedUserDao(savedUserDatabase: SavedUserDatabase) = savedUserDatabase.savedUserDao()

    @Singleton
    @Provides
    fun provideGameDao(@ApplicationContext context: Context): GameDao {
        return GameDatabase.getDatabase(context).gameDao()
    }
}