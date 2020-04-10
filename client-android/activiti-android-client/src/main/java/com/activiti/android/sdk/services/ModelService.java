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

package com.activiti.android.sdk.services;

import okhttp3.ResponseBody;
import retrofit2.Callback;

import com.activiti.client.api.ModelsAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.editor.ModelRepresentation;
import com.alfresco.client.RestClient;

/**
 * Created by jpascal on 12/12/2014.
 */
public class ModelService extends ActivitiService
{
    protected static final String MODEL_FORMS = "2";

    protected static final String MODEL_APPS = "3";

    protected ModelsAPI api;

    ModelService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(ModelsAPI.class);
    }

    // ///////////////////////////////////////////////////////////////////
    // SYNC
    // ///////////////////////////////////////////////////////////////////
    public String getModelThumbnailUrl(String modelId)
    {
        return String.format(restClient.endpoint.concat("api/enterprise/models/%s/thumbnail"), modelId);
    }

    public ResultList<ModelRepresentation> getAppDefinitionModels()
    {
        try
        {
            return api.getModels("myApps", MODEL_APPS, "modifiedDesc", null).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public ResultList<ModelRepresentation> getFormModels()
    {
        try
        {
            return api.getModels(null, MODEL_FORMS, "modifiedDesc", null).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // ///////////////////////////////////////////////////////////////////
    // ASYNC
    // ///////////////////////////////////////////////////////////////////
    public void getById(String id, Callback<ModelRepresentation> callback)
    {
        api.getById(id).enqueue(callback);
    }

    public void getAppDefinitionModels(Callback<ResultList<ModelRepresentation>> callback)
    {
        api.getModels("myApps", MODEL_APPS, "modifiedDesc", null).enqueue(callback);
    }

    public void getFormModels(Callback<ResultList<ModelRepresentation>> callback)
    {
        api.getModels(null, MODEL_FORMS, "modifiedDesc", null).enqueue(callback);
    }

    public void getModelThumbnail(String modelId, Callback<ResponseBody> callback)
    {
        api.getModelThumbnail(modelId).enqueue(callback);
    }

}
