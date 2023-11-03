package com.studyolle.zone.interfaces

import com.studyolle.common.response.CommonResponse
import com.studyolle.common.response.ResponseDto
import com.studyolle.zone.application.ZoneFacade
import com.studyolle.zone.interfaces.dto.ZoneDto
import com.studyolle.zone.interfaces.mapper.ZoneDtoMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/zone")
class ZoneController(
    private val zoneFacade: ZoneFacade
) {
    /**
     * zone 초기화
     */
    @PostMapping("/init")
    fun initZone(
    ) {
        zoneFacade.initZone()
    }

    /**
     * zone 등록
     */
    @PostMapping
    fun registerZone(
        @RequestBody registerForm: ZoneDto.RegisterForm
    ): ResponseEntity<ResponseDto<Void>> {
        zoneFacade.registerZone(ZoneDtoMapper.of(registerForm))
        return CommonResponse.send()
    }

    /**
     * zone 삭제
     */
    @DeleteMapping
    fun deleteZone(
        @RequestBody city: String
    ): ResponseEntity<ResponseDto<Void>> {
        zoneFacade.deleteZone(city)
        return CommonResponse.send()
    }
}