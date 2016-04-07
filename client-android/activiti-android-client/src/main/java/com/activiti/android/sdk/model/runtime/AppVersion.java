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

package com.activiti.android.sdk.model.runtime;

import java.io.Serializable;

import com.activiti.client.api.model.runtime.AppVersionRepresentation;

/**
 * Created by jpascal on 12/12/2014.
 */
public class AppVersion implements Serializable
{
    public final String type;

    public final String majorVersion;

    public final String minorVersion;

    public final String revisionVersion;

    public final String edition;

    public AppVersion(AppVersionRepresentation appVersionRepresentation)
    {
        this.type = appVersionRepresentation.getType();
        this.majorVersion = appVersionRepresentation.getMajorVersion();
        this.minorVersion = appVersionRepresentation.getMinorVersion();
        this.revisionVersion = appVersionRepresentation.getRevisionVersion();
        this.edition = appVersionRepresentation.getEdition();
    }

    public AppVersion(String fullVersion)
    {
        String[] splittedVersion = fullVersion.split("\\.");
        this.majorVersion = splittedVersion[0];
        this.minorVersion = splittedVersion[1];
        this.revisionVersion = splittedVersion[2];
        this.edition = null;
        this.type = null;
    }

    public String getFullVersion()
    {
        return majorVersion.concat(".").concat(minorVersion).concat(".").concat(revisionVersion);
    }

    public int getFullVersionNumber()
    {
        return Integer.parseInt(majorVersion.concat(minorVersion).concat(revisionVersion));
    }

    public boolean is120OrAbove()
    {
        return getFullVersionNumber() >= 120;
    }
}
