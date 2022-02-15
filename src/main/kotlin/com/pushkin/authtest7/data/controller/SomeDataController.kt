package com.pushkin.authtest7.data.controller

import com.pushkin.authtest7.base.BaseController
import com.pushkin.authtest7.data.vo.SomeDataVO
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/data")
@PreAuthorize("hasRole(\"USER\")")
class SomeDataController : BaseController() {

    @GetMapping("/{id}")
    fun findSomeData(@PathVariable id: Long): ResponseEntity<SomeDataVO> =
        ResponseEntity.ok(SomeDataVO("some tag $id", 456))

}
