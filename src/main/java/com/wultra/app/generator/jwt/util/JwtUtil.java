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
