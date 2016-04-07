/*
 *  Copyright (C) 2005-2016 Alfresco Software Limited.
 *
 *  This file is part of Alfresco Activiti Client.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alfresco.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import com.alfresco.client.utils.Base64;
import com.google.gson.GsonBuilder;

/**
 * Created by jpascal on 22/01/2016.
 */
public abstract class AbstractClient<T>
{

    protected RestClient restClient;

    protected OkHttpClient okHttpClient;

    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    // ///////////////////////////////////////////////////////////////////////////
    protected AbstractClient(RestClient restClient, OkHttpClient okHttpClient)
    {
        this.restClient = restClient;
        this.okHttpClient = okHttpClient;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    // ///////////////////////////////////////////////////////////////////////////
    public OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }

    public RestClient getRestClient()
    {
        return restClient;
    }

    public <T> T getAPI(final Class<T> service)
    {
        return restClient.retrofit.create(service);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // BASIC AUTH
    // ///////////////////////////////////////////////////////////////////////////
    protected static String getBasicAuth(String username, String password)
    {
        // Prepare Basic AUTH
        if (username != null && password != null)
        {
            String credentials = username + ":" + password;
            return "Basic " + Base64.encodeBytes(credentials.getBytes());
        }
        throw new IllegalArgumentException("Invalid Credentials");
    }

    // ///////////////////////////////////////////////////////////////////////////
    // BUILDER
    // ///////////////////////////////////////////////////////////////////////////
    public static abstract class Builder<T>
    {
        protected String endpoint, username, password, auth;

        protected OkHttpClient okHttpClient;

        protected Retrofit retrofit;

        protected GsonBuilder gsonBuilder;

        protected HttpLoggingInterceptor.Level logginLevel = HttpLoggingInterceptor.Level.NONE;

        public Builder<T> connect(String endpoint, String username, String password)
        {
            this.endpoint = endpoint;
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder<T> httpLogging(HttpLoggingInterceptor.Level level)
        {
            this.logginLevel = level;
            return this;
        }

        public Builder<T> okHttpClient(OkHttpClient okHttpClient)
        {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Builder<T> retrofit(Retrofit retrofit)
        {
            this.retrofit = retrofit;
            return this;
        }

        public Builder<T> gsonBuilder(GsonBuilder gsonBuilder)
        {
            this.gsonBuilder = gsonBuilder;
            return this;
        }

        public T build()
        {
            // Check Parameters
            if (endpoint == null || endpoint.isEmpty()) { throw new IllegalArgumentException("Invalid url"); }

            // Prepare OKHTTP Layer
            if (okHttpClient == null)
            {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();

                ArrayList<Protocol> protocols = new ArrayList<>(1);
                protocols.add(Protocol.HTTP_1_1);
                builder.protocols(protocols);
                builder.connectTimeout(10, TimeUnit.SECONDS);

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(logginLevel);
                builder.addInterceptor(logging);

                if (username != null && password != null)
                {

                    String credentials = username + ":" + password;
                    auth = "Basic " + Base64.encodeBytes(credentials.getBytes());

                    builder.addInterceptor(new Interceptor()
                    {
                        @Override
                        public Response intercept(Chain chain) throws IOException
                        {
                            Request newRequest = chain.request().newBuilder().addHeader("Authorization", auth)
                                    .removeHeader("User-Agent").addHeader("User-Agent", getUSerAgent()).build();
                            return chain.proceed(newRequest);
                        }
                    });
                }

                okHttpClient = builder.build();
            }

            // Prepare Retrofit
            if (retrofit == null)
            {
                Retrofit.Builder builder = new Retrofit.Builder().baseUrl(endpoint).client(okHttpClient)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

                if (gsonBuilder == null)
                {
                    gsonBuilder = getDefaultGsonBuilder();
                }
                builder.addConverterFactory(GsonConverterFactory.create(gsonBuilder.create())).build();

                retrofit = builder.build();
            }

            return create(new RestClient(endpoint, retrofit, username), okHttpClient);
        }

        public abstract GsonBuilder getDefaultGsonBuilder();

        public abstract String getUSerAgent();

        public abstract T create(RestClient restClient, OkHttpClient okHttpClient);

    }
}
