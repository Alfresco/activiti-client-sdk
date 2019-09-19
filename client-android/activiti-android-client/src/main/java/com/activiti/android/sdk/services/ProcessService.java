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
import com.activiti.client.api.model.runtime.*;
import okhttp3.RequestBody;
import retrofit2.Callback;

import com.activiti.client.api.ProcessInstanceAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.runtime.request.AddContentRelatedRepresentation;
import com.activiti.client.api.model.runtime.request.CreateProcessInstanceRepresentation;
import com.alfresco.client.RestClient;

import java.util.List;

/**
 * Created by jpascal on 11/12/2014.
 */
public class ProcessService extends ActivitiService
{
    protected ProcessInstanceAPI api;


    ProcessService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(ProcessInstanceAPI.class);
    }

    public String getShareUrl(String processId)
    {
        return String.format(restClient.endpoint.concat("/workflow/#/process/%s"), processId);
    }

    public String getProcessDiagramUrl(String processId, String tenantId)
    {
        return String.format(restClient.endpoint.concat("/api/runtime/process-instances/%1s/diagram?tenantId=%2s"),
                processId, tenantId);
    }

    // ///////////////////////////////////////////////////////////////////
    // START
    // ///////////////////////////////////////////////////////////////////
    public void startNewProcessInstance(CreateProcessInstanceRepresentation request,
            Callback<ProcessInstanceRepresentation> callback)
    {
        api.startNewProcessInstance(request).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // LISTING
    // ///////////////////////////////////////////////////////////////////
    public void list(ProcessesRequestRepresentation body, Callback<ResultList<ProcessInstanceRepresentation>> callback)
    {
        api.getProcessInstances(body).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // LIFECYCLE
    // ///////////////////////////////////////////////////////////////////
    public void getById(String processId, Callback<ProcessInstanceRepresentation> callback)
    {
        api.getProcessInstance(processId).enqueue(callback);
    }

    /*
     * public void start(CreateProcessInstanceRepresentation body,
     * Callback<ProcessInstanceRepresentation> callback) { // api.start(body,
     * callback); }
     */

    public void delete(String processInstanceId, Callback<Void> callback)
    {
        api.deleteProcessInstance(processInstanceId).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // CONTENT
    // ///////////////////////////////////////////////////////////////////
    public void getAttachments(String processInstanceId, Callback<ResultList<RelatedContentRepresentation>> callback)
    {
        api.getRelatedContentForProcessInstance(processInstanceId).enqueue(callback);
    }

    public void getFieldContents(String processInstanceId, Callback<ResultList<ProcessContentRepresentation>> callback)
    {
        api.getProcessInstanceContent(processInstanceId).enqueue(callback);
    }

    public void addAttachment(String processInstanceId, RequestBody resource,
            Callback<RelatedContentRepresentation> callback)
    {
        api.createRelatedContentOnProcessInstance(processInstanceId, resource).enqueue(callback);
    }

    public void linkAttachment(String processInstanceId, AddContentRelatedRepresentation representation,
            Callback<RelatedContentRepresentation> callback)
    {
        api.linkRelatedContentOnProcessInstance(processInstanceId, representation).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // COMMENT
    // ///////////////////////////////////////////////////////////////////
    public void getComments(String processInstanceId, Callback<ResultList<CommentRepresentation>> callback)
    {
        api.getProcessInstanceComments(processInstanceId).enqueue(callback);
    }

    public void addComment(String processInstanceId, CommentRepresentation request,
            Callback<CommentRepresentation> callback)
    {
        api.addProcessInstanceComment(processInstanceId, request).enqueue(callback);
    }

    public void getStartFormProcessInstance(String processInstanceId, Callback<FormDefinitionRepresentation> callback) {
        api.getStartFormProcessInstance(processInstanceId).enqueue(callback);
    }

    public void getHistoricFormVariables(String processInstanceId, Callback<List<RestVariable>> callback) {
        api.getHistoricFormVariables(processInstanceId).enqueue(callback);
    }
}
