package kian.springframework.mymsscbreweryclient.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class is used for externalized properties
 * to prevent hard coding!
 */
@ConfigurationProperties(prefix = "requestconfig")
@Configuration("customRequestConfig")
public class CustomRequestConfig {

    /** method 1 **/
    // here we use @value to initialize timeOutMiliSec and bind value from application.properties file.
    @Value("${requestconfig.timeoutmilisec}")
    private Long timeOutMiliSec;

    /** method 2 **/
    // here we use @ConfigurationProperties and @Configuration with setters and getters
    // and let spring does the job for us.
    private Long responseTimeoutMiliSec;

    public Long getTimeOutMiliSec() {
        return timeOutMiliSec;
    }

    /** a little bit of defensive programming with method 1 **/
    // intentionally not using setter for timeOutMiliSec, since it is initiated from application.properties file.
    // and we don't want it to be changed on runtime!
    public void setTimeOutMiliSec(Long timeOutMiliSec) {
        this.timeOutMiliSec = timeOutMiliSec;
    }

    public Long getResponseTimeoutMiliSec() {
        return responseTimeoutMiliSec;
    }

    // careful while using this setter! we don't want to accidentally change this property after runtime.
    public void setResponseTimeoutMiliSec(Long responseTimeoutMiliSec) {
        this.responseTimeoutMiliSec = responseTimeoutMiliSec;
    }
}
