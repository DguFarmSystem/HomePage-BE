package org.farmsystem.homepage.domain.minigame.garden.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.PlaceObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.UpdateGardenRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.GardenResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.ChangePlacedObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.UpdateGardenResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.service.GardenService;
import org.farmsystem.homepage.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/garden")
public class GardenController {

    private final GardenService gardenService;

    //정원 정보 전체조회
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getGardenInfo(@AuthenticationPrincipal Long userId) {
        List<GardenResponseDTO> response = gardenService.getGardenInfo(userId);
        return SuccessResponse.ok(response);
    }

    //정원 단일 타일 전체 상태 업데이트
    @PatchMapping("/update/{x}/{y}")
    public ResponseEntity<SuccessResponse<?>> updateGarden(
            @AuthenticationPrincipal Long userId,
            @PathVariable int x,
            @PathVariable int y,
            @RequestBody UpdateGardenRequestDTO requestDTO
    ){
        UpdateGardenResponseDTO response = gardenService.updateGarden(userId, x, y, requestDTO);
        return SuccessResponse.ok(response);
    }

    //정원 오브젝트 회전각도 업데이트
    @PatchMapping("/rotate")
    public ResponseEntity<SuccessResponse<?>> rotateGardenObject(
            @AuthenticationPrincipal Long userId,
            @RequestBody PlaceObjectRequestDTO requestDTO
    ){
        ChangePlacedObjectResponseDTO response = gardenService.rotateGardenObject(userId, requestDTO);
        return SuccessResponse.ok(response);
    }

    //친구 유저 id에 따른 친구 정원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> getOtherUserGardenInfo(@PathVariable Long userId) {
        List<GardenResponseDTO> response = gardenService.getGardenInfo(userId);
        return SuccessResponse.ok(response);
    }

}
