package kian.springframework.mymsscbreweryclient.web.config;


import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultConnectionKeepAliveStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/***
 * This component uses four different methods in spring to use
 * externalized properties and configurations and prevent hard coding.
 */
@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    /** method 1 **/
    // Spring Boot 3.1 introduced @PropertySource annotation
    // Since we are using SB 2.7 we can't use it

    /** method 2**/
    // externalizing properties to avoid hard coding
    // accessing to application.properties demonstration
    Environment environment;

    /** method 3 **/
    // using custom externalized configuration
    CustomRequestConfig customRequestConfig;

    /**  method 4 **/
    // direct usage of spring @value bindings
    private final Integer defaultMaxPerRoute;

    public BlockingRestTemplateCustomizer(
            Environment environment,
            CustomRequestConfig customRequestConfig,
            @Value("${connectionmanager.perroute.max}") Integer defaultMaxPerRoute
    ) {
        this.environment = environment;
        this.customRequestConfig = customRequestConfig;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory(){
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(
                Integer.parseInt(Objects.requireNonNull(environment.getProperty("connectionmanager.total.max")))
        );
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(customRequestConfig.getTimeOutMiliSec()))
                .setResponseTimeout(Timeout.ofMicroseconds(customRequestConfig.getResponseTimeoutMiliSec()))
                .build();

        HttpClient httpClient = HttpClients
                .custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
    }
}