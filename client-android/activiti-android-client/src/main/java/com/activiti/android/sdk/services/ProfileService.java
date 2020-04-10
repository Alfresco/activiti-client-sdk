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

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import com.activiti.client.api.ProfileAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.idm.UserRepresentation;
import com.activiti.client.api.model.idm.request.ChangePasswordRepresentation;
import com.activiti.client.api.model.idm.request.UpdateProfileRepresentation;
import com.activiti.client.api.model.runtime.integration.dto.AlfrescoEndpointRepresentation;
import com.alfresco.client.RestClient;

/**
 * Created by jpascal on 17/03/2015.
 */
public class ProfileService extends ActivitiService
{
    protected ProfileAPI api;

    ProfileService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(ProfileAPI.class);
    }

    public String getProfilePictureURL()
    {
        return restClient.endpoint.concat("api/enterprise/profile-picture");
    }

    public void getProfile(Callback<UserRepresentation> callback)
    {
        api.getProfile().enqueue(callback);
    }

    public UserRepresentation getProfile()
    {
        try
        {
            return api.getProfile().execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public ResultList<AlfrescoEndpointRepresentation> getAlfrescoRepositories()
    {
        try
        {
            return api.getRepositories().execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public void getAlfrescoRepositories(Callback<ResultList<AlfrescoEndpointRepresentation>> callback)
    {
        api.getRepositories().enqueue(callback);
    }

    public void getProfilePicture(Callback<Void> callback)
    {
        api.getProfilePicture().enqueue(callback);
    }

    public void updateProfile(UpdateProfileRepresentation request, Callback<UserRepresentation> callback)
    {
        api.updateUser(request).enqueue(callback);
    }

    public void updatePassword(ChangePasswordRepresentation request, Callback<UserRepresentation> callback)
    {
        api.changePassword(request).enqueue(callback);
    }

    public ResponseBody updateProfilePicture(RequestBody resource)
    {
        try
        {
            return api.uploadProfilePicture(resource).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
