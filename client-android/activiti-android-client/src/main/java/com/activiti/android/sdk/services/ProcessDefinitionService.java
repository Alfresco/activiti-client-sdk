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

import com.activiti.client.api.model.editor.form.FormDefinitionRepresentation;
import com.activiti.client.api.model.editor.form.OptionRepresentation;
import retrofit2.Callback;

import com.activiti.client.api.ProcessDefinitionAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.runtime.ProcessDefinitionRepresentation;
import com.alfresco.client.RestClient;

import java.util.List;

/**
 * Created by jpascal on 11/12/2014.
 */
public class ProcessDefinitionService extends ActivitiService
{

    protected ProcessDefinitionAPI api;

    ProcessDefinitionService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(ProcessDefinitionAPI.class);
    }

    // ///////////////////////////////////////////////////////////////////
    // LISTING
    // ///////////////////////////////////////////////////////////////////
    public void getProcessDefinitions(Callback<ResultList<ProcessDefinitionRepresentation>> callback)
    {
        api.getProcessDefinitions().enqueue(callback);
    }

    public void getProcessDefinitions(Long appDefinitionId,
            Callback<ResultList<ProcessDefinitionRepresentation>> callback)
    {
        api.getProcessDefinitions(appDefinitionId).enqueue(callback);
    }

    public ResultList<ProcessDefinitionRepresentation> getProcessDefinitions(Long appDefinitionId)
    {
        try
        {
            return api.getProcessDefinitions(appDefinitionId).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public void getProcessDefinitionStartForm(String processDefinitionId, Callback<FormDefinitionRepresentation> callback) {
        api.getProcessDefinitionStartForm(processDefinitionId).enqueue(callback);
    }

    public void getRestFieldValues(String processDefinitionId, String field, Callback<List<OptionRepresentation>> callback) {
        api.getRestFieldValues(processDefinitionId, field).enqueue(callback);
    }
}
