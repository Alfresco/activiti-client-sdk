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

package com.activiti.client;

import okhttp3.OkHttpClient;

import com.activiti.client.api.AboutAPI;
import com.activiti.client.api.AdminAPI;
import com.activiti.client.api.ContentAPI;
import com.activiti.client.api.ModelsAPI;
import com.activiti.client.api.ProcessDefinitionAPI;
import com.activiti.client.api.ProcessInstanceAPI;
import com.activiti.client.api.ProfileAPI;
import com.activiti.client.api.RuntimeAppDefinitionAPI;
import com.activiti.client.api.TaskAPI;
import com.activiti.client.api.UserFiltersAPI;
import com.activiti.client.api.UserGroupAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.deserializer.ResultListDeserializer;
import com.activiti.client.api.model.runtime.ProcessDefinitionRepresentation;
import com.alfresco.client.AbstractClient;
import com.alfresco.client.RestClient;
import com.alfresco.client.utils.ISO8601Utils;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BPMClient extends AbstractClient<BPMClient>
{
    protected static final Object LOCK = new Object();

    protected static BPMClient mInstance;

    protected AboutAPI aboutAPI;

    protected AdminAPI admiAPI;

    protected ContentAPI contentAPI;

    protected ModelsAPI modelsAPI;

    protected ProcessDefinitionAPI processDefinitionAPI;

    protected ProcessInstanceAPI processInstanceAPI;

    protected ProfileAPI profileAPI;

    protected RuntimeAppDefinitionAPI runtimeAppDefinitionAPI;

    protected TaskAPI taskAPI;

    protected UserFiltersAPI userFiltersAPI;

    protected UserGroupAPI userGroupAPI;

    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    // ///////////////////////////////////////////////////////////////////////////
    public static BPMClient getInstance()
    {
        synchronized (LOCK)
        {
            return mInstance;
        }
    }

    protected BPMClient(RestClient restClient, OkHttpClient okHttpClient)
    {
        super(restClient, okHttpClient);
    }

    // ///////////////////////////////////////////////////////////////////////////
    // API REGISTRY
    // ///////////////////////////////////////////////////////////////////////////
    public AboutAPI getAboutAPI()
    {
        return aboutAPI = (aboutAPI == null) ? getAPI(AboutAPI.class) : aboutAPI;
    }

    public AdminAPI getAdmiAPI()
    {
        return admiAPI = (admiAPI == null) ? getAPI(AdminAPI.class) : admiAPI;
    }

    public ContentAPI getContentAPI()
    {
        return contentAPI = (contentAPI == null) ? getAPI(ContentAPI.class) : contentAPI;
    }

    public ModelsAPI getModelsAPI()
    {
        return modelsAPI = (modelsAPI == null) ? getAPI(ModelsAPI.class) : modelsAPI;
    }

    public ProcessDefinitionAPI getProcessDefinitionAPI()
    {
        return processDefinitionAPI = (processDefinitionAPI == null) ? getAPI(ProcessDefinitionAPI.class)
                : processDefinitionAPI;
    }

    public ProcessInstanceAPI getProcessInstanceAPI()
    {
        return processInstanceAPI = (processInstanceAPI == null) ? getAPI(ProcessInstanceAPI.class)
                : processInstanceAPI;
    }

    public ProfileAPI getProfileAPI()
    {
        return profileAPI = (profileAPI == null) ? getAPI(ProfileAPI.class) : profileAPI;
    }

    public RuntimeAppDefinitionAPI getRuntimeAppDefinitionAPI()
    {
        return runtimeAppDefinitionAPI = (runtimeAppDefinitionAPI == null) ? getAPI(RuntimeAppDefinitionAPI.class)
                : runtimeAppDefinitionAPI;
    }

    public TaskAPI getTaskAPI()
    {
        return taskAPI = (taskAPI == null) ? getAPI(TaskAPI.class) : taskAPI;
    }

    public UserFiltersAPI getUserFiltersAPI()
    {
        return userFiltersAPI = (userFiltersAPI == null) ? getAPI(UserFiltersAPI.class) : userFiltersAPI;
    }

    public UserGroupAPI getUserGroupAPI()
    {
        return userGroupAPI = (userGroupAPI == null) ? getAPI(UserGroupAPI.class) : userGroupAPI;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // BUILDER
    // ///////////////////////////////////////////////////////////////////////////
    public static class Builder extends AbstractClient.Builder<BPMClient>
    {

        @Override
        public String getUSerAgent()
        {
            return "Alfresco-BPM-Client/" + Version.SDK;
        }

        @Override
        public BPMClient create(RestClient restClient, OkHttpClient okHttpClient)
        {
            return new BPMClient(new RestClient(endpoint, retrofit, username), okHttpClient);
        }

        @Override
        public GsonBuilder getDefaultGsonBuilder()
        {
            return Utils.getDefaultGsonBuilder();
        }

        // BUILD
        // ///////////////////////////////////////////////////////////////////////////

        public BPMClient build()
        {
            mInstance = super.build();
            return mInstance;
        }
    }

    // ///////////////////////////////////////////////////////////////////////////
    // UTILS
    // ///////////////////////////////////////////////////////////////////////////
    public static class Utils
    {
        public static GsonBuilder getDefaultGsonBuilder()
        {
            return new GsonBuilder().setDateFormat(ISO8601Utils.DATE_ISO_FORMAT)
                    .registerTypeAdapter(new TypeToken<ResultList<ProcessDefinitionRepresentation>>()
                    {
                    }.getType(), new ResultListDeserializer<>(ProcessDefinitionRepresentation.class));
        }
    }
}
