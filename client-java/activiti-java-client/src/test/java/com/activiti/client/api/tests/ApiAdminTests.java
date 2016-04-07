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

package com.activiti.client.api.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import retrofit2.Response;

import com.activiti.client.api.ActivitiAPITestCase;
import com.activiti.client.api.AdminAPI;
import com.activiti.client.api.UserGroupAPI;
import com.activiti.client.api.model.common.ResultList;
import com.activiti.client.api.model.idm.LightUserRepresentation;
import com.activiti.client.api.model.idm.UserRepresentation;

public class ApiAdminTests extends ActivitiAPITestCase
{
    @BeforeClass
    public void prepare() throws Exception
    {
        client = prepareClient(TEST_ENDPOINT, TEST_USERNAME, TEST_PASSWORD);
    }

    @Test
    public void manageUsers() throws IOException
    {
        UserGroupAPI userGroupAPI = client.getAPI(UserGroupAPI.class);
        AdminAPI api = client.getAPI(AdminAPI.class);

        // Check if the user is present
        Response<ResultList<LightUserRepresentation>> users = userGroupAPI.getUsers("First Name").execute();
        if (!users.body().getList().isEmpty())
        {
            // TODO Delete User
        }

        // Create User Object
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName("First Name");
        userRep.setLastName("Last Name");
        userRep.setCompany("Alfresco");
        userRep.setPassword("VeryStrongPassword");
        userRep.setEmail("f.l@alfresco.com");
        userRep.setType("enterprise");
        userRep.setStatus("active");
        userRep.setTenantId(1L);

        // Create a new User
        Response<UserRepresentation> response = api.createNewUser(userRep).execute();
        Assert.assertNotNull(response);
        Assert.assertEquals(response.isSuccessful(), true);

        // Check Response
        UserRepresentation user = response.body();
        Assert.assertEquals(user.getEmail(), "f.l@alfresco.com");
        Assert.assertEquals(user.getFirstName(), "First Name");
        Assert.assertEquals(user.getLastName(), "Last Name");
        Assert.assertEquals(user.getCompany(), "Alfresco");
    }
}
