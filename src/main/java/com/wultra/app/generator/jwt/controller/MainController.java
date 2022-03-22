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
package com.wultra.app.generator.jwt.controller;

import com.wultra.app.generator.jwt.config.JwtConfig;
import com.wultra.app.generator.jwt.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petr Dvorak, petr@wultra.com
 */
@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;

    @Autowired
    public MainController(JwtConfig jwtConfig, JwtUtil jwtUtil) {
        this.jwtConfig = jwtConfig;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("usernamePrefix", jwtConfig.getUsernamePrefix());
        model.addAttribute("deeplinkPrefix", jwtConfig.getDeeplinkPrefix());
        model.addAttribute("expiration", jwtConfig.getExpiration());
        return "index";
    }

    @RequestMapping(value = "jwt", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> jwt(
            @RequestParam("userId") String userId,
            @RequestParam(value = "expiration", required = false, defaultValue = "300") Long expiration) {
        final String jwt = jwtUtil.signedToken(jwtConfig.getAlgorithm(), jwtConfig.getPrivateKey(), userId, expiration);
        final Map<String, Object> response = new HashMap<>();
        response.put("jwt", jwt);
        logger.info("Issued JWT token for user: {}, value: {}", userId, jwt);
        return response;
    }

}
