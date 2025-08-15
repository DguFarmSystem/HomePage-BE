package org.farmsystem.homepage.domain.minigame.garden.controller;

import lombok.RequiredArgsConstructor;
import org.farmsystem.homepage.domain.minigame.garden.dto.AddTileDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.MoveObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.request.PlaceObjectRequestDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.GardenResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.MoveObjectResponseDTO;
import org.farmsystem.homepage.domain.minigame.garden.dto.response.PlaceObjectResponseDTO;
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

    //정원 타일 추가
    @PostMapping("/tile")
    public ResponseEntity<SuccessResponse<?>> addGardenTile(
            @AuthenticationPrincipal Long userId, @RequestBody AddTileDTO requestDTO
    ){
        AddTileDTO response = gardenService.addGardenTile(userId, requestDTO);
        return SuccessResponse.created(response);
    }

    //정원 오브젝트 배치 추가
    @PostMapping("/place")
    public ResponseEntity<SuccessResponse<?>> addGardenObject(
            @AuthenticationPrincipal Long userId, @RequestBody PlaceObjectRequestDTO requestDTO
    ){
        PlaceObjectResponseDTO response = gardenService.addGardenObject(userId, requestDTO);
        return SuccessResponse.created(response);

    }


    //정원 오브젝트 위치 이동
    @PatchMapping("/move")
    public ResponseEntity<SuccessResponse<?>> moveGardenObject(
            @AuthenticationPrincipal Long userId,
            @RequestBody MoveObjectRequestDTO request
    ) {
        MoveObjectResponseDTO response = gardenService.moveGardenObject(userId, request);
        return SuccessResponse.ok(response);
    }

    //정원 오브젝트 회전각도 업데이트


    //오브젝트 배치 철회 (다시 인벤토리로)


    //친구 유저 id에 따른 친구 정원 조회
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse<?>> getOtherUserGardenInfo(@PathVariable Long userId) {
        List<GardenResponseDTO> response = gardenService.getGardenInfo(userId);
        return SuccessResponse.ok(response);
    }

}
