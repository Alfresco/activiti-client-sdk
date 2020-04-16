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

package com.activiti.android.sdk;

import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.activiti.android.sdk.services.ServiceRegistry;
import com.activiti.client.Version;
import com.activiti.client.api.constant.ActivitiAPI;
import com.activiti.client.api.model.editor.form.AmountFieldRepresentation;
import com.activiti.client.api.model.editor.form.ContainerRepresentation;
import com.activiti.client.api.model.editor.form.DynamicTableRepresentation;
import com.activiti.client.api.model.editor.form.FormFieldRepresentation;
import com.activiti.client.api.model.editor.form.FormFieldTypes;
import com.activiti.client.api.model.editor.form.HyperlinkRepresentation;
import com.activiti.client.api.model.editor.form.RestFieldRepresentation;
import com.alfresco.client.AbstractClient;
import com.alfresco.client.RestClient;
import com.alfresco.client.utils.ISO8601Utils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

/**
 * Created by jpascal on 17/03/2015.
 */
public class ActivitiSession extends AbstractClient<ActivitiSession>
{
    protected static final Object LOCK = new Object();

    protected static HashMap<String, WeakReference<ActivitiSession>> cache = new HashMap<>();

    protected ServiceRegistry registry;

    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    // ///////////////////////////////////////////////////////////////////////////

    protected ActivitiSession(RestClient restClient, OkHttpClient okHttpClient)
    {
        super(restClient, okHttpClient);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // GETTERS
    // ///////////////////////////////////////////////////////////////////////////
    public ServiceRegistry getServiceRegistry()
    {
        if (registry == null)
        {
            registry = new ServiceRegistry(restClient);
        }
        return registry;
    }

    public boolean isActivitiOnTheCloud()
    {
        if (restClient == null || restClient.endpoint == null) { return false; }
        return restClient.endpoint.startsWith(ActivitiAPI.SERVER_URL_ENDPOINT);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // CACHING
    // ///////////////////////////////////////////////////////////////////////////
    public static ActivitiSession with(String identifier)
    {
        WeakReference<ActivitiSession> weakRef = cache.get(identifier);
        return weakRef != null ? weakRef.get() : null;
    }

    public void register(String identifier)
    {
        cache.put(identifier, new WeakReference(this));
    }

    // ///////////////////////////////////////////////////////////////////////////
    // BUILDER
    // ///////////////////////////////////////////////////////////////////////////
    public static class Builder extends AbstractClient.Builder<ActivitiSession>
    {
        @Override
        public String getUserAgent()
        {
            return "Activiti-Android-Client/" + Version.SDK;
        }

        @Override
        public ActivitiSession create(RestClient restClient, OkHttpClient okHttpClient)
        {
            return new ActivitiSession(new RestClient(endpoint, retrofit, username), okHttpClient);
        }

        // ///////////////////////////////////////////////////////////////////////////
        // BUILD
        // ///////////////////////////////////////////////////////////////////////////
        public ActivitiSession build()
        {
            return super.build();
        }

        @Override
        public GsonBuilder getDefaultGsonBuilder()
        {

            // Prepare Retrofit
            GsonFireBuilder fireBuilder = new GsonFireBuilder().registerTypeSelector(FormFieldRepresentation.class,
                    new TypeSelector<FormFieldRepresentation>()
                    {
                        @Override
                        public Class<? extends FormFieldRepresentation> getClassForElement(JsonElement readElement)
                        {
                            try
                            {
                                String type = readElement.getAsJsonObject().get("type").getAsString();
                                if (type.equals(FormFieldTypes.HYPERLINK))
                                {
                                    return HyperlinkRepresentation.class;
                                }
                                else if (type.equals(
                                        FormFieldTypes.DYNAMIC_TABLE)) { return DynamicTableRepresentation.class; }
                                if (readElement.getAsJsonObject().get("fieldType") == null) { return null; }
                                String fieldType = readElement.getAsJsonObject().get("fieldType").getAsString();
                                if (fieldType.equals("HyperlinkRepresentation"))
                                {
                                    return HyperlinkRepresentation.class;
                                }
                                else if (fieldType.equals("DynamicTableRepresentation"))
                                {
                                    return DynamicTableRepresentation.class;
                                }
                                else if (fieldType.equals("RestFieldRepresentation"))
                                {
                                    return RestFieldRepresentation.class;
                                }
                                else if (fieldType.equals("AmountFieldRepresentation"))
                                {
                                    return AmountFieldRepresentation.class;
                                }
                                else if (fieldType.equals("ContainerRepresentation"))
                                {
                                    return ContainerRepresentation.class;
                                }
                                else
                                {
                                    return null;
                                }
                            }
                            catch (Exception e)
                            {
                                return null;
                            }
                        }
                    });

            Type mapStringObjectType = new TypeToken<Map<String, Object>>()
            {
            }.getType();

            return fireBuilder.createGsonBuilder().setDateFormat(ISO8601Utils.DATE_ISO_FORMAT)
                    .registerTypeAdapter(mapStringObjectType, new RandomMapKeysAdapter());
        }
    }

    protected static class RandomMapKeysAdapter implements JsonDeserializer<Map<String, Object>>
    {
        @Override
        public Map<String, Object> deserialize(JsonElement json, Type unused, JsonDeserializationContext context)
                throws JsonParseException
        {
            // if not handling primitives, nulls and arrays, then just
            if (!json.isJsonObject()) throw new JsonParseException("some meaningful message");

            Map<String, Object> result = new HashMap<String, Object>();
            JsonObject jsonObject = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet())
            {
                String key = entry.getKey();
                JsonElement element = entry.getValue();
                if (element.isJsonPrimitive())
                {
                    result.put(key, element.getAsString());
                }
                else if (element.isJsonObject())
                {
                    result.put(key, context.deserialize(element, unused));
                }
                // if not handling nulls and arrays
                else
                {
                    // Do nothing
                }
            }
            return result;
        }
    }
}
