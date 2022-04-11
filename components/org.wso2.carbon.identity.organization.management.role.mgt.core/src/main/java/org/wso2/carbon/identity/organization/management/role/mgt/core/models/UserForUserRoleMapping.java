/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.com).
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.organization.management.role.mgt.core.models;

/**
 * User object for User Role Mapping
 */
public class UserForUserRoleMapping {

    private String userId;
    private boolean forcedRolePrivilege;
    private boolean includeSubOrgsPrivilege;

    public UserForUserRoleMapping(String userId, boolean forcedRolePrivilege, boolean includeSubOrgsPrivilege) {

        this.userId = userId;
        this.forcedRolePrivilege = forcedRolePrivilege;
        this.includeSubOrgsPrivilege = includeSubOrgsPrivilege;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public boolean hasForcedPrivilege() {

        return forcedRolePrivilege;
    }


    public boolean hasIncludeSubOrgsPrivilege() {

        return includeSubOrgsPrivilege;
    }

}
