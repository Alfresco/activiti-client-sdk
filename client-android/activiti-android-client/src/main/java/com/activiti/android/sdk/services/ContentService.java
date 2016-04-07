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
import retrofit2.Response;

import com.activiti.client.api.ContentAPI;
import com.activiti.client.api.ProcessInstanceAPI;
import com.activiti.client.api.TaskAPI;
import com.activiti.client.api.model.runtime.RelatedContentRepresentation;
import com.activiti.client.api.model.runtime.request.AddContentRelatedRepresentation;
import com.alfresco.client.RestClient;

/**
 * Created by jpascal on 11/12/2014.
 */
public class ContentService extends ActivitiService
{
    protected ContentAPI api;

    protected TaskAPI taskApi;

    protected ProcessInstanceAPI processApi;

    ContentService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(ContentAPI.class);
        taskApi = client.retrofit.create(TaskAPI.class);
    }

    public String getDownloadUrl(Long contentId)
    {
        return String.format(restClient.endpoint.concat("/api/enterprise/content/%s/raw"), Long.toString(contentId));
    }

    public String getThumbnailUrl(Long contentId)
    {
        return String.format(restClient.endpoint.concat("/api/enterprise/content/%s/rendition/thumbnail"),
                Long.toString(contentId));
    }

    public void getByIdentifier(String contentId, Callback<RelatedContentRepresentation> callback)
    {
        api.getContent(contentId).enqueue(callback);
    }

    public void delete(Long contentId, Callback<Void> callback)
    {
        api.deleteContent(Long.toString(contentId)).enqueue(callback);
    }

    public void download(String contentId, Callback<ResponseBody> callback)
    {
        api.getRawContent(contentId).enqueue(callback);
    }

    public Response<ResponseBody> download(String contentId)
    {
        try
        {
            return api.getRawContent(contentId).execute();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public RelatedContentRepresentation createRelatedContentOnTask(String taskId, RequestBody file)
    {
        try
        {
            return taskApi.createRelatedContentOnTask(taskId, file).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public RelatedContentRepresentation createRelatedContentOnProcessInstance(String processId, RequestBody file)
    {
        try
        {
            return processApi.createRelatedContentOnProcessInstance(processId, file).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public RelatedContentRepresentation createTemporaryRawRelatedContent(RequestBody file)
    {
        try
        {
            return api.createTemporaryRawRelatedContent(file).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public void createTemporaryRelatedContent(AddContentRelatedRepresentation representation,
            Callback<RelatedContentRepresentation> callback)
    {
        api.createTemporaryRelatedContent(representation).enqueue(callback);
    }

    public void createTemporaryRawRelatedContent(AddContentRelatedRepresentation representation,
            Callback<RelatedContentRepresentation> callback)
    {
        api.createTemporaryRelatedContent(representation).enqueue(callback);
    }

}
