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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.idm.LightGroupRepresentation;
import com.activiti.client.api.model.idm.LightUserRepresentation;
import com.activiti.client.api.model.idm.UserActionRepresentation;
import com.activiti.client.api.model.idm.UserRepresentation;

/**
 * Created by jpascal on 11/12/2014.
 */
public interface UserGroupAPI
{
    @GET("api/enterprise/users/{userId}")
    Call<UserRepresentation> getUser(@Path("userId") String userId);

    @GET("api/enterprise/users/{userId}")
    Observable<UserRepresentation> getUserObservable(@Path("userId") String userId);

    @PUT("api/enterprise/users/{userId}")
    Call<UserRepresentation> updateUser(@Path("userId") String userId, @Body UserRepresentation userRequest);

    @PUT("api/enterprise/users/{userId}")
    Observable<UserRepresentation> updateUserObservable(@Path("userId") String userId,
            @Body UserRepresentation userRequest);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/users/{userId}")
    Call<Void> executeAction(@Path("userId") String userId, @Body UserActionRepresentation representation);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/users/{userId}")
    Observable<Void> executeActionObservable(@Path("userId") String userId,
            @Body UserActionRepresentation representation);

    @GET("api/enterprise/users")
    Call<ResultList<LightUserRepresentation>> getUsers(@Query("filter") String filter, @Query("email") String email,
            @Query("externalId") String externalId, @Query("excludeTaskId") String excludeTaskId,
            @Query("excludeProcessId") String excludeProcessId, @Query("groupId") String groupId,
            @Query("tenantId") String tenantId);

    @GET("api/enterprise/users")
    Observable<ResultList<LightUserRepresentation>> getUsersObservable(@Query("filter") String filter,
            @Query("email") String email, @Query("externalId") String externalId,
            @Query("excludeTaskId") String excludeTaskId, @Query("excludeProcessId") String excludeProcessId,
            @Query("groupId") String groupId, @Query("tenantId") String tenantId);

    @GET("api/enterprise/users")
    Call<ResultList<LightUserRepresentation>> getUsers(@Query("filter") String filter);

    @GET("api/enterprise/users")
    Observable<ResultList<LightUserRepresentation>> getUsersObservable(@Query("filter") String filter);

    @GET("api/enterprise/groups")
    Call<ResultList<LightGroupRepresentation>> getGroups(@Query("filter") String filter,
            @Query("groupId") String groupId);

    @GET("api/enterprise/groups")
    Observable<ResultList<LightGroupRepresentation>> getGroupsObservable(@Query("filter") String filter,
            @Query("groupId") String groupId);

    @GET("api/enterprise/groups/{groupId}/users")
    Call<ResultList<LightUserRepresentation>> getUsersForGroup(@Path("groupId") String groupId);

    @GET("api/enterprise/groups/{groupId}/users")
    Observable<ResultList<LightUserRepresentation>> getUsersForGroupObservable(@Path("groupId") String groupId);

}
