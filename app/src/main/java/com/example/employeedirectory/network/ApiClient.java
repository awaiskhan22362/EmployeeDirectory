package com.example.employeedirectory.network;

import com.example.employeedirectory.constt.EmployeeConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final Object m_LOCKK = new Object();
    private static Retrofit retrofit = null;

    public static Retrofit getClientt() {
        synchronized (m_LOCKK) {
            if (retrofit == null) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient m_okHttpClient = new OkHttpClient().newBuilder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain m_chain) throws IOException {
                                Request requestt = m_chain.request()
                                        .newBuilder()
                                        .addHeader("Content-Type","application/json")
                                        .build();
                                return m_chain.proceed(requestt);
                            }
                        })
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                        .build();

                // Log.e("jjj", "=" + (MySharedPreference.getmInstance().isEnglish() ? Constant.WEB_SERVICE : Constant.WEB_SERVICE_ARABIC));
                retrofit = new Retrofit.Builder()
                        .client(m_okHttpClient)
                        .baseUrl(EmployeeConstants.WEB_SERVICE)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
            return retrofit;
        }
    }



    public static RequestBody plain(String m_content) {
        return getRequestBody("text/plain", m_content);
    }

    public static MultipartBody.Part imageDocument(String param_name, File filee) {
        return getRequest(param_name, filee);
    }

    public static RequestBody imageData(File filee) {
        return getImageBody("multipart/form-data", filee);
    }

    public static RequestBody getRequestBody(String typee, String m_content) {
        return RequestBody.create(MediaType.parse(typee), m_content);
    }

    public static RequestBody getImageBody(String typee, File filee) {
        return RequestBody.create(MediaType.parse(typee), filee);
    }

    public static MultipartBody.Part getRequest(String paramName, File filee) {
        return MultipartBody.Part.createFormData(paramName, filee.getName(), imageData(filee));
    }
}