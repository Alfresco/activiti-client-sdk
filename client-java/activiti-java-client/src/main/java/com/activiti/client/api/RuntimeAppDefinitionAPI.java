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
import retrofit2.http.GET;
import rx.Observable;

import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.runtime.AppDefinitionRepresentation;

/**
 * Created by jpascal on 12/12/2014.
 */
public interface RuntimeAppDefinitionAPI
{
    @GET("api/enterprise/runtime-app-definitions")
    Call<ResultList<AppDefinitionRepresentation>> getAppDefinitions();

    @GET("api/enterprise/runtime-app-definitions")
    Observable<ResultList<AppDefinitionRepresentation>> getAppDefinitionsObservable();
}
