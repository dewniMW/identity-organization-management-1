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

package org.wso2.carbon.identity.organization.management.role.mgt.core.util;


import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.database.utils.jdbc.NamedJdbcTemplate;
import org.wso2.carbon.identity.core.persistence.UmPersistenceManager;
import org.wso2.carbon.identity.core.util.IdentityDatabaseUtil;
import org.wso2.carbon.identity.organization.management.role.mgt.core.constants.OrganizationUserRoleMgtConstants;
import org.wso2.carbon.identity.organization.management.role.mgt.core.exception.OrganizationUserRoleMgtClientException;
import org.wso2.carbon.identity.organization.management.role.mgt.core.exception.OrganizationUserRoleMgtServerException;
import org.wso2.carbon.identity.organization.management.role.mgt.core.internal.OrganizationUserRoleMgtDataHolder;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.UserStoreManager;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.UUID;

/**
 * Utility functions for role management.
 */
public class Utils {

    /**
     * Creates a new instance of NamedJdbcTemplate.
     *
     * @return new instance of NamedJdbcTemplate.
     */
    public static NamedJdbcTemplate getNewNamedJdbcTemplate() {
        return new NamedJdbcTemplate(UmPersistenceManager.getInstance().getDataSource());
    }

    /**
     * Get a new Jdbc Template.
     *
     * @return a new Jdbc Template with Identity DB Connection.
     */
    public static NamedJdbcTemplate getNewTemplateForIdentityDatabase() {

        return new NamedJdbcTemplate(IdentityDatabaseUtil.getDataSource());
    }

    /**
     * Creates a new random UUID.
     *
     * @return new random unique universally unique identifier.
     */
    public static String generateUniqueID() {

        return UUID.randomUUID().toString();
    }

    /**
     * Throw an OrganizationUserRoleMgtClientException upon client side error in organization-user-role management.
     *
     * @param error The error enum.
     * @param data  The error message data.
     * @return OrganizationUserRoleMgtClientException.
     */
    public static OrganizationUserRoleMgtClientException handleClientException(
            OrganizationUserRoleMgtConstants.ErrorMessages error, String data) {

        String description;
        if (StringUtils.isNotBlank(data)) {
            description = String.format(error.getDescription(), data);
        } else {
            description = error.getDescription();
        }
        return new OrganizationUserRoleMgtClientException(error.getMessage(), description, error.getCode());
    }

    /**
     * Throw an OrganizationUserRoleMgtServerException upon server side error in organization-user-role management.
     *
     * @param error The error enum.
     * @param data  The error message data.
     * @param e     Throwable object.
     * @return OrganizationUserRoleMgtServerException.
     */
    public static OrganizationUserRoleMgtServerException handleServerException(
            OrganizationUserRoleMgtConstants.ErrorMessages error, String data, Throwable e) {
        String message;
        if (StringUtils.isNotBlank(data)) {
            message = String.format(error.getMessage(), data);
        } else {
            message = error.getMessage();
        }
        return new OrganizationUserRoleMgtServerException(message, error.getCode(), e);
    }

    /**
     * Throw an OrganizationUserRoleMgtServerException upon server side error in organization-user-role management.
     *
     * @param error The error enum.
     * @param data  The error message data.
     * @return
     */
    public static OrganizationUserRoleMgtServerException handleServerException(
            OrganizationUserRoleMgtConstants.ErrorMessages error, String data) {

        String message;
        if (StringUtils.isNotBlank(data)) {
            message = String.format(error.getMessage(), data);
        } else {
            message = error.getMessage();
        }
        return new OrganizationUserRoleMgtServerException(message, error.getCode());
    }

    /**
     * Get user store manager using tenant ID.
     *
     * @param tenantId The id of the tenant.
     * @return a user store manager.
     * @throws UserStoreException throw UserStoreException if unable to retrieve a user store manager.
     */
    public static UserStoreManager getUserStoreManager(int tenantId)
            throws UserStoreException {

        RealmService realmService = OrganizationUserRoleMgtDataHolder.getInstance().getRealmService();
        UserRealm tenantUserRealm = realmService.getTenantUserRealm(tenantId);
        return (UserStoreManager) tenantUserRealm.getUserStoreManager();
    }

    /**
     * Getting the user ID from the username.
     *
     * @param username Username of the user.
     * @param tenantId The tenant ID.
     * @return The user ID of the user.
     * @throws OrganizationUserRoleMgtServerException An OrganizationUserRoleMgtServerException.
     */
    public static String getUserIdFromUserName(String username, int tenantId)
            throws OrganizationUserRoleMgtServerException {

        if (username == null) {
            return null;
        }
        try {
            AbstractUserStoreManager userStoreManager = (AbstractUserStoreManager) OrganizationUserRoleMgtDataHolder
                    .getInstance().getRealmService().getTenantUserRealm(tenantId).getUserStoreManager();
            return userStoreManager.getUserIDFromUserName(username);
        } catch (UserStoreException e) {
            throw handleServerException(
                    OrganizationUserRoleMgtConstants.ErrorMessages.ERROR_CODE_USER_STORE_OPERATIONS_ERROR,
                    "Error obtaining ID for the username : " + username + ", tenant id : " + tenantId);
        }
    }

    /**
     * Get the tenant domain.
     *
     * @return the tenant domain.
     */
    public static String getTenantDomain() {

        return PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
    }

    /**
     * Get the tenant ID.
     *
     * @return the tenant id.
     */
    public static int getTenantId() {

        return PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
    }
}
