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

import java.util.List;

/**
 * User Role Mapping.
 */
public class UserRoleMapping {

    private List<UserForUserRoleMapping> users;
    private String roleId;

    public UserRoleMapping(String roleId, List<UserForUserRoleMapping> users) {

        this.roleId = roleId;
        this.users = users;
    }

    public void setRoleId(String roleId) {

        this.roleId = roleId;
    }

    public void setUsers(List<UserForUserRoleMapping> users) {

        this.users = users;
    }

    public String getRoleId() {

        return roleId;
    }

    public List<UserForUserRoleMapping> getUsers() {

        return users;
    }
}
