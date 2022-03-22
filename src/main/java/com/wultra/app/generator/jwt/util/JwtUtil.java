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
package com.wultra.app.generator.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * Utility class for generating signed JWT tokens.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
@Service
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public String signedToken(String algorithm, String privateKey, String userId, Long expiration) {
        try {
            Algorithm a = buildAlgorithm(algorithm, privateKey);
            return JWT.create()
                    .withIssuer("wultra")
                    .withSubject(userId)
                    .withJWTId(UUID.randomUUID().toString())
                    .withExpiresAt(Date.from(LocalDateTime.now().plusSeconds(expiration).toInstant(ZoneOffset.UTC)))
                    .sign(a);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.error("Unable to create JWT signer due to cryptographic provider error. Check BouncyCastle installation.", e);
        }
        return null;
    }

    private Algorithm buildAlgorithm(String algorithm, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException {
        switch (algorithm.toUpperCase()) {
            case "RS256": {
                return Algorithm.RSA256(null, (RSAPrivateKey) loadPrivateKey("RSA", privateKey));
            }
            case "RS384": {
                return Algorithm.RSA384(null, (RSAPrivateKey) loadPrivateKey("RSA", privateKey));
            }
            case "RS512": {
                return Algorithm.RSA512(null, (RSAPrivateKey) loadPrivateKey("RSA", privateKey));
            }
            case "ES256": {
                return Algorithm.ECDSA256(null, (ECPrivateKey) loadPrivateKey("ECDSA", privateKey));
            }
            case "ES384": {
                return Algorithm.ECDSA384(null, (ECPrivateKey) loadPrivateKey("ECDSA", privateKey));
            }
            case "ES512": {
                return Algorithm.ECDSA512(null, (ECPrivateKey) loadPrivateKey("ECDSA", privateKey));
            }
            default: {
                throw new InvalidKeySpecException("Only RSA and EC algorithms are supported for JWT signatures");
            }
        }
    }

    private PrivateKey loadPrivateKey(String keyType, String value) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        final byte[] encoded = BaseEncoding.base64().decode(value);
        final KeyFactory keyFactory = KeyFactory.getInstance(keyType, "BC");
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return keyFactory.generatePrivate(keySpec);
    }
}
