package ge.bog.library.externalApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.bog.library.dto.ApiResponseDto;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.time.Duration;
import java.util.List;

@Service
public class CallExternalApi  implements ExternalAPI {

    @Override
    public ApiResponseDto callExternalApi(String title) throws KeyStoreException, KeyManagementException, JsonProcessingException {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(90));
        requestFactory.setReadTimeout(Duration.ofSeconds(90));
        requestFactory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("thegate.bog.ge", 8080)));
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        restTemplate.setInterceptors(
                List.of(new ClientHttpRequestInterceptor() {
                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                        System.out.println("Request: " + request.getURI());
                        System.out.println("Request headers: " + request.getHeaders());
                        return execution.execute(request, body);
                    }
                })
        );

        // Create HttpHeaders instance and set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Connection", "keep-alive");
        headers.set("Accept", "*/*");
        headers.set("Keep-Alive", "1000");
        headers.set("Cache-Control", "no-cache");
        headers.set("User-Agent", "PostmanRuntime/7.37.3");
        headers.set("Host", "openlibrary.org");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Use the RestTemplate to make the API call
        ResponseEntity<String> response = restTemplate.exchange("http://openlibrary.org/search.json?q=" + title,
                HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), ApiResponseDto.class);
    }
}
