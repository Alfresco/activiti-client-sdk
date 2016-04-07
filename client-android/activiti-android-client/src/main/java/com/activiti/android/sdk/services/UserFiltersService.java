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

import retrofit2.Callback;

import com.activiti.client.api.UserFiltersAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.runtime.UserFilterOrderRepresentation;
import com.activiti.client.api.model.runtime.UserProcessInstanceFilterRepresentation;
import com.activiti.client.api.model.runtime.UserTaskFilterRepresentation;
import com.alfresco.client.RestClient;

public class UserFiltersService extends ActivitiService
{
    protected UserFiltersAPI api;

    UserFiltersService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(UserFiltersAPI.class);

    }

    // ///////////////////////////////////////////////////////////////////
    // TASKS
    // ///////////////////////////////////////////////////////////////////
    public void getUserTaskFilters(Long appId, Callback<ResultList<UserTaskFilterRepresentation>> callback)
    {
        api.getUserTaskFilters(appId).enqueue(callback);
    }

    public void getUserTaskFilter(Long userFilterId, Callback<UserTaskFilterRepresentation> callback)
    {
        api.getUserTaskFilter(userFilterId).enqueue(callback);
    }

    public void createUserTaskFilter(UserTaskFilterRepresentation userTaskFilterRepresentation,
            Callback<UserTaskFilterRepresentation> callback)
    {
        api.createUserTaskFilter(userTaskFilterRepresentation).enqueue(callback);
    }

    public void updateUserTaskFilter(Long userFilterId, UserTaskFilterRepresentation userTaskFilterRepresentation,
            Callback<UserTaskFilterRepresentation> callback)
    {
        api.updateUserTaskFilter(userFilterId, userTaskFilterRepresentation).enqueue(callback);
    }

    public void orderUserTaskFilters(UserFilterOrderRepresentation filterOrderRepresentation, Callback<Void> callback)
    {
        api.orderUserTaskFilters(filterOrderRepresentation).enqueue(callback);
    }

    public void deleteUserTaskFilter(Long userFilterId, Callback<Void> callback)
    {
        api.deleteUserTaskFilter(userFilterId).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // PROCESSES
    // ///////////////////////////////////////////////////////////////////
    public void getUserProcessInstanceFilters(Long appId,
            Callback<ResultList<UserProcessInstanceFilterRepresentation>> callback)
    {
        api.getUserProcessInstanceFilters(appId).enqueue(callback);
    }

    public void getUserProcessInstanceFilter(Long userFilterId,
            Callback<UserProcessInstanceFilterRepresentation> callback)
    {
        api.getUserProcessInstanceFilter(userFilterId).enqueue(callback);
    }

    public void createUserProcessInstanceFilter(UserProcessInstanceFilterRepresentation userTaskFilterRepresentation,
            Callback<UserProcessInstanceFilterRepresentation> callback)
    {
        api.createUserProcessInstanceFilter(userTaskFilterRepresentation).enqueue(callback);
    }

    public void updateUserProcessInstanceFilter(Long userFilterId,
            UserProcessInstanceFilterRepresentation userTaskFilterRepresentation,
            Callback<UserProcessInstanceFilterRepresentation> callback)
    {
        api.updateUserProcessInstanceFilter(userFilterId, userTaskFilterRepresentation).enqueue(callback);
    }

    public void orderUserProcessInstanceFilters(UserFilterOrderRepresentation filterOrderRepresentation,
            Callback<Void> callback)
    {
        api.orderUserProcessInstanceFilters(filterOrderRepresentation).enqueue(callback);
    }

    public void deleteUserProcessInstanceFilter(Long userFilterId, Callback<Void> callback)
    {
        api.deleteUserProcessInstanceFilter(userFilterId).enqueue(callback);
    }
}
