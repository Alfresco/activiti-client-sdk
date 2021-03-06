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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.idm.ResetPasswordRepresentation;
import com.activiti.client.api.model.idm.UserRepresentation;
import com.activiti.client.api.model.idm.request.ChangePasswordRepresentation;
import com.activiti.client.api.model.idm.request.UpdateProfileRepresentation;
import com.activiti.client.api.model.runtime.integration.dto.AlfrescoEndpointRepresentation;

/**
 * Created by jpascal on 11/12/2014.
 */
public interface ProfileAPI
{
    @GET("api/enterprise/profile")
    Call<UserRepresentation> getProfile();

    @GET("api/enterprise/profile")
    Observable<UserRepresentation> getProfileObservable();

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/profile")
    Call<UserRepresentation> updateUser(@Body UpdateProfileRepresentation request);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/profile")
    Observable<UserRepresentation> updateUserObservable(@Body UpdateProfileRepresentation request);

    @GET("api/enterprise/profile-picture")
    Call<Void> getProfilePicture();

    @GET("api/enterprise/profile-picture")
    Observable<Void> getProfilePictureObservable();

    @Multipart
    @POST("api/enterprise/profile-picture")
    Call<ResponseBody> uploadProfilePicture(@Part("file") RequestBody resource);

    @Multipart
    @POST("api/enterprise/profile-picture")
    Observable<ResponseBody> uploadProfilePictureObservable(@Part("file") RequestBody resource);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/profile-password")
    Call<UserRepresentation> changePassword(@Body ChangePasswordRepresentation request);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/profile-password")
    Observable<UserRepresentation> changePasswordObservable(@Body ChangePasswordRepresentation request);

    @GET("api/enterprise/profile/accounts/alfresco")
    Call<ResultList<AlfrescoEndpointRepresentation>> getRepositories();

    @GET("api/enterprise/profile/accounts/alfresco")
    Observable<ResultList<AlfrescoEndpointRepresentation>> getRepositoriesObservable();

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/idm/passwords")
    Call<Void> requestPasswordReset(@Body ResetPasswordRepresentation request);

    @Headers({ "Content-type: application/json" })
    @POST("api/enterprise/idm/passwords")
    Observable<Void> requestPasswordResetObservable(@Body ResetPasswordRepresentation request);
}
