package com.github.demo.domain.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * Add  <i>archived = 0</i> condition
 *
 * @author Shengzhao Li
 */
public class CustomJdbcClientDetailsService extends JdbcClientDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomJdbcClientDetailsService.class);

    private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, authorized_grant_types, " +
            "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove " +
            "from oauth_client_details where client_id = ? and archived = 0 ";


    public CustomJdbcClientDetailsService(DataSource dataSource) {
        super(dataSource);
        LOG.info("CustomJdbcClientDetailsService >>>>>>>");
        setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL);
    }


}