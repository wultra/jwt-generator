/*
 * Copyright 2022 Wultra s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wultra.app.generator.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class with various JWT token settings.
 *
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
