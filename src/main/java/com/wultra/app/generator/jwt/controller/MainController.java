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
 * Main RESTful controller responsible for rendering the main index page and JWT token endpoint.
 *
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
