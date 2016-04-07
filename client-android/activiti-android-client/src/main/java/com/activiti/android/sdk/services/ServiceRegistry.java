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

import com.alfresco.client.RestClient;

/**
 * Created by jpascal on 17/03/2015.
 */
public class ServiceRegistry
{
    protected RestClient restClient;

    protected ProfileService profileService;

    protected ApplicationService applicationservice;

    protected TaskService taskService;

    protected ProcessService processService;

    protected UserGroupService userGroupService;

    protected ProcessDefinitionService processDefinitionService;

    protected ContentService contentService;

    protected InfoService infoService;

    protected ModelService modelService;

    protected UserFiltersService userFiltersService;

    // ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    // ///////////////////////////////////////////////////////////////////////////
    public ServiceRegistry(RestClient adapter)
    {
        this.restClient = adapter;
    }

    // ///////////////////////////////////////////////////////////////////////////
    // SERVICES
    // ///////////////////////////////////////////////////////////////////////////
    public ProfileService getProfileService()
    {
        if (profileService == null)
        {
            profileService = new ProfileService(restClient);
        }
        return profileService;
    }

    public ApplicationService getApplicationService()
    {
        if (applicationservice == null)
        {
            applicationservice = new ApplicationService(restClient);
        }
        return applicationservice;
    }

    public TaskService getTaskService()
    {
        if (taskService == null)
        {
            taskService = new TaskService(restClient);
        }
        return taskService;
    }

    public ProcessService getProcessService()
    {
        if (processService == null)
        {
            processService = new ProcessService(restClient);
        }
        return processService;
    }

    public ApplicationService getApplicationservice()
    {
        if (applicationservice == null)
        {
            applicationservice = new ApplicationService(restClient);
        }
        return applicationservice;
    }

    public UserGroupService getUserGroupService()
    {
        if (userGroupService == null)
        {
            userGroupService = new UserGroupService(restClient);
        }
        return userGroupService;
    }

    public ProcessDefinitionService getProcessDefinitionService()
    {
        if (processDefinitionService == null)
        {
            processDefinitionService = new ProcessDefinitionService(restClient);
        }
        return processDefinitionService;
    }

    public ContentService getContentService()
    {
        if (contentService == null)
        {
            contentService = new ContentService(restClient);
        }
        return contentService;
    }

    public InfoService getInfoService()
    {
        if (infoService == null)
        {
            infoService = new InfoService(restClient);
        }
        return infoService;
    }

    public ModelService getModelService()
    {
        if (modelService == null)
        {
            modelService = new ModelService(restClient);
        }
        return modelService;
    }

    public UserFiltersService getUserFiltersService()
    {
        if (userFiltersService == null)
        {
            userFiltersService = new UserFiltersService(restClient);
        }
        return userFiltersService;
    }

}
