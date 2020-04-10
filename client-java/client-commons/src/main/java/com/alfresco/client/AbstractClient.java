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

import okhttp3.Credentials;
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
    // BUILDER
    // ///////////////////////////////////////////////////////////////////////////
    public static abstract class Builder<T>
    {
        protected String endpoint, username, password;

        protected OkHttpClient okHttpClient;

        protected Retrofit retrofit;

        protected GsonBuilder gsonBuilder;

        protected HttpLoggingInterceptor.Level logginLevel = HttpLoggingInterceptor.Level.NONE;

        protected Interceptor authInterceptor;

        public Builder<T> connect(String endpoint)
        {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * Provide basic authorization credentials
         * @deprecated
         * Please consider providing {@link #authInterceptor} instead.
         */
        @Deprecated
        public Builder<T> credentials(String username, String password)
        {
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

        public Builder<T> authInterceptor(Interceptor interceptor)
        {
            this.authInterceptor = interceptor;
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

                // Add interceptor to update any headers
                builder.addInterceptor(new Interceptor()
                {
                    @Override
                    public Response intercept(Chain chain) throws IOException
                    {
                        Request newRequest = chain.request()
                            .newBuilder()
                            .removeHeader("User-Agent")
                            .addHeader("User-Agent", getUserAgent())
                            .build();
                        return chain.proceed(newRequest);
                    }
                });

                // Add logging interceptor before authorization to avoid leaking private data
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(logginLevel);
                builder.addInterceptor(logging);

                // Add auth interceptor if available or create one for basic auth
                if (authInterceptor != null)
                {
                    builder.addInterceptor(authInterceptor);
                }
                else
                {
                    final String credentials = Credentials.basic(username, password);

                    builder.addInterceptor(new Interceptor()
                    {
                        @Override
                        public Response intercept(Chain chain) throws IOException
                        {
                            Request newRequest = chain.request()
                                .newBuilder()
                                .addHeader("Authorization", credentials)
                                .build();
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

        public abstract String getUserAgent();

        public abstract T create(RestClient restClient, OkHttpClient okHttpClient);

    }
}
