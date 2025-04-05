package com.scaler.productservicejan31capstone.commons;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationCommons
{
    RestTemplate restTemplate;

    public ApplicationCommons(@Qualifier("getLoadBalancedRestTemplate") RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public void validateToken(String token)
    {
        if(token == null || token.isEmpty())
        {
            throw new RuntimeException("Invalid token: token empty");
        }

        String url = "http://UserServiceJan31Capstone/users/validate/" + token;
        Boolean isValidToken = restTemplate.getForObject(url, Boolean.class);

        if(Boolean.FALSE.equals(isValidToken))
        {
            throw new RuntimeException("Invalid token: token is invalid");
        }
    }
}
