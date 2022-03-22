/*
 * JWT Token Deep Link Generator
 * Copyright (C) 2022 Wultra s.r.o.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.wultra.app.generator.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petr Dvorak, petr@wultra.com
 */
@Configuration
public class JwtConfig {

    @Value("${powerauth.jwt.algorithm}")
    private String algorithm;

    @Value("${powerauth.jwt.private-key}")
    private String privateKey;

    @Value("${powerauth.jwt.config.username-prefix}")
    private String usernamePrefix;

    @Value("${powerauth.jwt.config.deeplink-prefix}")
    private String deeplinkPrefix;

    @Value("${powerauth.jwt.config.expiration}")
    private String expiration;

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getUsernamePrefix() {
        return usernamePrefix;
    }

    public String getDeeplinkPrefix() {
        return deeplinkPrefix;
    }

    public String getExpiration() {
        return expiration;
    }
}
