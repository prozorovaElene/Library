package ge.bog.library.externalApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import ge.bog.library.dto.ApiResponseDto;

import java.security.KeyManagementException;
import java.security.KeyStoreException;

public interface ExternalAPI {
    ApiResponseDto callExternalApi(String title) throws KeyStoreException, KeyManagementException, JsonProcessingException;
}
