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

package com.activiti.android.sdk.model;

import com.activiti.client.api.constant.RequestConstant;

/**
 * Created by jpascal on 13/03/2015.
 */
public enum TaskSorting
{
    CREATED_ASC(RequestConstant.SORT_CREATED_ASC), CREATED_DESC(RequestConstant.SORT_CREATED_DESC), DUE_ASC(
            RequestConstant.SORT_DUE_ASC), DUE_DESC(RequestConstant.SORT_DUE_DESC);

    private final String value;

    TaskSorting(String v)
    {
        value = v;
    }

    public static TaskSorting fromValue(String v)
    {
        for (TaskSorting c : TaskSorting.values())
        {
            if (c.value.equals(v)) { return c; }
        }
        throw new IllegalArgumentException(v);
    }

    public String value()
    {
        return value;
    }
}
