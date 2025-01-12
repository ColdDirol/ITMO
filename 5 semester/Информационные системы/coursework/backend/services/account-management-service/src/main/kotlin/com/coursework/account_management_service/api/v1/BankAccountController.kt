package com.coursework.account_management_service.api.v1

import com.coursework.account_management_service.domain.BankAccountService
import external.BankAccountDto
import external.TransferDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/bank-account")
class BankAccountController(
    private val service: BankAccountService
) {

    @PostMapping
    fun create(
        @RequestBody dto: BankAccountDto
    ): BankAccountDto = service.create(dto)

    @GetMapping("/{id}")
    fun getById(
        @PathVariable("id") id: Long
    ): BankAccountDto = service.getById(id)

    @GetMapping("/user/{userId}/all")
    fun getAllByUserId(
        @PathVariable("userId") userId: Long
    ): List<BankAccountDto> = service.getAllByUserId(userId)

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: Long
    ) = service.delete(id)


    // custom
    @PostMapping("/transfer")
    fun transferMoney(
        @RequestBody dto: TransferDto
    ){
        service.transfer(dto)
    }
}