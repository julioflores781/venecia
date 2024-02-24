package com.test.venecia.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceshipAspect {

    private static final Logger logger = LoggerFactory.getLogger(SpaceshipAspect.class);

    @Before("execution(* com.test.venecia.controller.SpaceshipController.getById(Long)) && args(id)")
    public void logNegativeIdRequest(Long id) {
        if (id < 0) {
            logger.warn("Requested spaceship with negative ID: {}", id);
        }
    }

}

