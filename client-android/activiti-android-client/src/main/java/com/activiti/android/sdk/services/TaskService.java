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

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Callback;

import com.activiti.client.api.ContentAPI;
import com.activiti.client.api.TaskAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.editor.form.FormDefinitionRepresentation;
import com.activiti.client.api.model.editor.form.OptionRepresentation;
import com.activiti.client.api.model.editor.form.request.CompleteFormRepresentation;
import com.activiti.client.api.model.runtime.ChecklistOrderRepresentation;
import com.activiti.client.api.model.runtime.CommentRepresentation;
import com.activiti.client.api.model.runtime.RelatedContentRepresentation;
import com.activiti.client.api.model.runtime.SaveFormRepresentation;
import com.activiti.client.api.model.runtime.TaskRepresentation;
import com.activiti.client.api.model.runtime.request.AddContentRelatedRepresentation;
import com.activiti.client.api.model.runtime.request.AssignTaskRepresentation;
import com.activiti.client.api.model.runtime.request.AttachFormTaskRepresentation;
import com.activiti.client.api.model.runtime.request.InvolveTaskRepresentation;
import com.activiti.client.api.model.runtime.request.QueryTasksRepresentation;
import com.activiti.client.api.model.runtime.request.UpdateTaskRepresentation;
import com.alfresco.client.RestClient;

/**
 * Created by jpascal on 11/12/2014.
 */
public class TaskService extends ActivitiService
{
    protected TaskAPI api;

    protected ContentAPI contentApi;

    TaskService(RestClient client)
    {
        super(client);
        api = client.retrofit.create(TaskAPI.class);
        contentApi = client.retrofit.create(ContentAPI.class);
    }

    public TaskAPI getTaskAPI()
    {
        return api;
    }

    public String getShareUrl(String taskId)
    {
        return String.format(restClient.endpoint.concat("/workflow/#/task/%s"), taskId);
    }

    // ///////////////////////////////////////////////////////////////////
    // LISTING
    // ///////////////////////////////////////////////////////////////////
    public void list(QueryTasksRepresentation filter, Callback<ResultList<TaskRepresentation>> callback)
    {
        api.listTasks(filter).enqueue(callback);
    }

    public ResultList<TaskRepresentation> list(QueryTasksRepresentation filter)
    {
        try
        {
            return api.listTasks(filter).execute().body();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    // ///////////////////////////////////////////////////////////////////
    // LIFECYCLE
    // ///////////////////////////////////////////////////////////////////
    public void getById(String taskId, Callback<TaskRepresentation> callback)
    {
        api.getTask(taskId).enqueue(callback);
    }

    public void create(TaskRepresentation task, Callback<TaskRepresentation> callback)
    {
        api.createNewTask(task).enqueue(callback);
    }

    public void edit(String taskId, UpdateTaskRepresentation request, Callback<TaskRepresentation> callback)
    {
        api.updateTask(taskId, request).enqueue(callback);
    }

    public void complete(String taskId, Callback<Void> callback)
    {
        api.completeTask(taskId).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // CONTENT
    // ///////////////////////////////////////////////////////////////////
    public void getAttachments(String taskId, Callback<ResultList<RelatedContentRepresentation>> callback)
    {
        api.getRelatedContentForTask(taskId).enqueue(callback);
    }

    public void addAttachment(String taskId, RequestBody resource, Callback<RelatedContentRepresentation> callback)
    {
        api.createRelatedContentOnTask(taskId, resource).enqueue(callback);
    }

    public void deleteAttachment(Long contentId, Callback<Void> callback)
    {
        contentApi.deleteContent(Long.toString(contentId)).enqueue(callback);
    }

    public void linkAttachment(String taskId, AddContentRelatedRepresentation representation,
            Callback<RelatedContentRepresentation> callback)
    {
        api.linkRelatedContentOnTask(taskId, representation).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // COMMENT
    // ///////////////////////////////////////////////////////////////////
    public void getComments(String taskId, Callback<ResultList<CommentRepresentation>> callback)
    {
        api.getTaskComments(taskId).enqueue(callback);
    }

    public void addComment(String taskId, CommentRepresentation request, Callback<CommentRepresentation> callback)
    {
        api.addTaskComment(taskId, request).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // ACTIONS
    // ///////////////////////////////////////////////////////////////////
    public void assign(String taskId, AssignTaskRepresentation request, Callback<TaskRepresentation> callback)
    {
        api.assignTask(taskId, request).enqueue(callback);
    }

    public void involve(String taskId, InvolveTaskRepresentation request, Callback<Void> callback)
    {
        api.involveUser(taskId, request).enqueue(callback);
    }

    public void removeInvolved(String taskId, InvolveTaskRepresentation request, Callback<Void> callback)
    {
        api.removeInvolvedUser(taskId, request).enqueue(callback);
    }

    public void claimTask(String taskId, Callback<Void> callback)
    {
        api.claimTask(taskId).enqueue(callback);
    }

    public void attachForm(String taskId, AttachFormTaskRepresentation request, Callback<Void> callback)
    {
        api.attachForm(taskId, request).enqueue(callback);
    }

    public void removeForm(String taskId, Callback<Void> callback)
    {
        api.removeForm(taskId).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // FORMS
    // ///////////////////////////////////////////////////////////////////
    public void getTaskForm(String taskId, Callback<FormDefinitionRepresentation> callback)
    {
        api.getTaskForm(taskId).enqueue(callback);
    }

    public void getFormFieldValues(String taskId, String fieldId, Callback<List<OptionRepresentation>> callback)
    {
        api.getFormFieldValues(taskId, fieldId).enqueue(callback);
    }

    public void completeTaskForm(String taskId, CompleteFormRepresentation request, Callback<Void> callback)
    {
        api.completeTaskForm(taskId, request).enqueue(callback);
    }

    public void saveTaskForm(String taskId, SaveFormRepresentation request, Callback<Void> callback)
    {
        api.saveTaskForm(taskId, request).enqueue(callback);
    }

    // ///////////////////////////////////////////////////////////////////
    // CHECKLIST
    // ///////////////////////////////////////////////////////////////////
    public void getChecklist(String taskId, Callback<ResultList<TaskRepresentation>> callback)
    {
        api.getChecklist(taskId).enqueue(callback);
    }

    public void addSubtask(String taskId, TaskRepresentation request, Callback<TaskRepresentation> callback)
    {
        api.addSubtask(taskId, request).enqueue(callback);
    }

    public void orderChecklist(String taskId, ChecklistOrderRepresentation request, Callback<Void> callback)
    {
        api.orderChecklist(taskId, request).enqueue(callback);
    }
}
