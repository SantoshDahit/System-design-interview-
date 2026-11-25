package com.kayple.api.fakedexcarecontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class DexCareController {

    private DeviceService deviceService;

    @PostMapping("/create")
    public DeviceDto.ResponseDeviceDto create(){
        deviceService.createDevice();
        return
    }



}
