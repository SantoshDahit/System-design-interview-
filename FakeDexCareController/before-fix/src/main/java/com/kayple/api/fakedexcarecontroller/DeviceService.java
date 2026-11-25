package com.kayple.api.fakedexcarecontroller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DeviceService {

    @Value("${DexCare.client_id}")
    private  String DEXCARE_CLIENT_ID;

    @Value("${DexCare.client_secret}")
    private  String DEXCARE_CLIENT_SECRETE;

    @Value("${DexCare.URI}")
    private  String DEXCARE_URI;

    private final RestClient restClient ;

    public DeviceDto.ResponseDeviceDto createDevice(){
        String accessToken = getAccessToken();

        DeviceDto.ResponseDeviceDto body = restClient.post()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentLength(MediaType.APPLICATION_JSON)
                .body(createDeviceDto)
                .retrieve();
        return body;
    }

    private String getAccessToken(){
        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "clinet_credentials");
        formData.put("client_id", DEXCARE_CLIENT_ID);
        formData.put("client_secret", DEXCARE_CLIENT_SECRETE);

        Map<String, Object> response = restClient.post()
                .uri(DEXCARE_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);


        if (response == null || !response.containsKey("access_token")){
            throw new RuntimeException("Failed to retrieve access token from DexCare");
        }

        return response.get("access_token").toString();
    }

}
