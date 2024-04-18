package com.example.employeedirectory.network

import com.example.employeedirectory.constt.EmployeeConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseURL(): String = EmployeeConstants.WEB_SERVICE

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(m_loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val m_okhttpClient = OkHttpClient.Builder()

        m_okhttpClient.addInterceptor { chainn ->
            val requestt = chainn.request().newBuilder()
            val originalHttpUrl = chainn.request().url
            val urll = originalHttpUrl.newBuilder().build()
            requestt.url(urll)
            val mp_response = chainn.proceed(requestt.build())
            return@addInterceptor mp_response
        }

        m_okhttpClient.addInterceptor(m_loggingInterceptor) //debug
        m_okhttpClient.callTimeout(120, TimeUnit.SECONDS)
        m_okhttpClient.connectTimeout(120, TimeUnit.SECONDS)
        m_okhttpClient.writeTimeout(120, TimeUnit.SECONDS)
        m_okhttpClient.readTimeout(120, TimeUnit.SECONDS)
        val okhtttp = m_okhttpClient.build()
        return okhtttp

    }

    @Provides
    fun provideConvertorFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        conn: Converter.Factory,
        okHttpClientt: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(conn)
            .client(okHttpClientt)
            .build()
    }

    @Provides
    fun providesApiService(retrofitt: Retrofit): ApiInterface {
        return retrofitt.create(ApiInterface::class.java)
    }
}