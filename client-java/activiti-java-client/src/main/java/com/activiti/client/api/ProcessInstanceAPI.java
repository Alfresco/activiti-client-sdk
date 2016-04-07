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

package com.activiti.client.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.runtime.CommentRepresentation;
import com.activiti.client.api.model.runtime.ProcessContentRepresentation;
import com.activiti.client.api.model.runtime.ProcessInstanceFilterRequestRepresentation;
import com.activiti.client.api.model.runtime.ProcessInstanceRepresentation;
import com.activiti.client.api.model.runtime.ProcessesRequestRepresentation;
import com.activiti.client.api.model.runtime.RelatedContentRepresentation;
import com.activiti.client.api.model.runtime.request.AddContentRelatedRepresentation;
import com.activiti.client.api.model.runtime.request.CreateProcessInstanceRepresentation;

/**
 * Created by jpascal on 11/12/2014.
 */
public interface ProcessInstanceAPI
{
    @GET("api/enterprise/process-instances/{processInstanceId}")
    Call<ProcessInstanceRepresentation> getProcessInstance(@Path("processInstanceId") String taskId);

    @GET("api/enterprise/process-instances/{processInstanceId}")
    Observable<ProcessInstanceRepresentation> getProcessInstanceObservable(@Path("processInstanceId") String taskId);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/query")
    Call<ResultList<ProcessInstanceRepresentation>> getProcessInstances(@Body ProcessesRequestRepresentation body);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/query")
    Observable<ResultList<ProcessInstanceRepresentation>> getProcessInstancesObservable(
            @Body ProcessesRequestRepresentation body);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/filter")
    Call<ResultList<ProcessInstanceRepresentation>> filterProcessInstances(
            @Body ProcessInstanceFilterRequestRepresentation body);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/filter")
    Observable<ResultList<ProcessInstanceRepresentation>> filterProcessInstancesObservable(
            @Body ProcessInstanceFilterRequestRepresentation body);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances")
    Call<ProcessInstanceRepresentation> startNewProcessInstance(@Body CreateProcessInstanceRepresentation request);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances")
    Observable<ProcessInstanceRepresentation> startNewProcessInstanceObservable(
            @Body CreateProcessInstanceRepresentation request);

    @DELETE("api/enterprise/process-instances/{processInstanceId}")
    Call<Void> deleteProcessInstance(@Path("processInstanceId") String processInstanceId);

    @DELETE("api/enterprise/process-instances/{processInstanceId}")
    Observable<Void> deleteProcessInstanceObservable(@Path("processInstanceId") String processInstanceId);

    // COMMENTS
    // ///////////////////////////////////////////////////////////////////
    @GET("api/enterprise/process-instances/{processInstanceId}/comments")
    Call<ResultList<CommentRepresentation>> getProcessInstanceComments(
            @Path("processInstanceId") String processInstanceId);

    @GET("api/enterprise/process-instances/{processInstanceId}/comments")
    Observable<ResultList<CommentRepresentation>> getProcessInstanceCommentsObservable(
            @Path("processInstanceId") String processInstanceId);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/{processInstanceId}/comments")
    Call<CommentRepresentation> addProcessInstanceComment(@Path("processInstanceId") String processInstanceId,
            @Body CommentRepresentation request);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/process-instances/{processInstanceId}/comments")
    Observable<CommentRepresentation> addProcessInstanceCommentObservable(
            @Path("processInstanceId") String processInstanceId, @Body CommentRepresentation request);

    // CONTENTS
    // ///////////////////////////////////////////////////////////////////
    @GET("api/enterprise/process-instances/{processInstanceId}/content")
    Call<ResultList<RelatedContentRepresentation>> getRelatedContentForProcessInstance(
            @Path("processInstanceId") String processInstanceId);

    @GET("api/enterprise/process-instances/{processInstanceId}/content")
    Observable<ResultList<RelatedContentRepresentation>> getRelatedContentForProcessInstanceObservable(
            @Path("processInstanceId") String processInstanceId);

    @GET("api/enterprise/process-instances/{processInstanceId}/field-content")
    Call<ResultList<ProcessContentRepresentation>> getProcessInstanceContent(
            @Path("processInstanceId") String processInstanceId);

    @GET("api/enterprise/process-instances/{processInstanceId}/field-content")
    Observable<ResultList<ProcessContentRepresentation>> getProcessInstanceContentObservable(
            @Path("processInstanceId") String processInstanceId);

    @Multipart
    @POST("api/enterprise/process-instances/{processInstanceId}/raw-content")
    Call<RelatedContentRepresentation> createRelatedContentOnProcessInstance(
            @Path("processInstanceId") String processInstanceId, @Part("file") RequestBody resource);

    @Multipart
    @POST("api/enterprise/process-instances/{processInstanceId}/raw-content")
    Observable<RelatedContentRepresentation> createRelatedContentOnProcessInstanceObservable(
            @Path("processInstanceId") String processInstanceId, @Part("file") RequestBody resource);

    @POST("api/enterprise/process-instances/{processInstanceId}/content")
    Call<RelatedContentRepresentation> linkRelatedContentOnProcessInstance(
            @Path("processInstanceId") String processInstanceId, @Body AddContentRelatedRepresentation representation);

    @POST("api/enterprise/process-instances/{processInstanceId}/content")
    Observable<RelatedContentRepresentation> linkRelatedContentOnProcessInstanceObservable(
            @Path("processInstanceId") String processInstanceId, @Body AddContentRelatedRepresentation representation);
}
