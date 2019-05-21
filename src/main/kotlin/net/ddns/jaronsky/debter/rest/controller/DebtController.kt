package net.ddns.jaronsky.debter.rest.controller

import net.ddns.jaronsky.debter.model.Debt
import net.ddns.jaronsky.debter.model.dto.DebtDto
import net.ddns.jaronsky.debter.rest.service.DebtService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Created by Wojciech Jaronski
 *
 */
@RestController
@RequestMapping("/api/debts")
@PreAuthorize("isAuthenticated()")
class DebtController(
        private val debtService: DebtService
) {

    @GetMapping
    fun getMyDebts(): List<Debt> {
        return debtService.getMyDebts()
    }

    @GetMapping("/{id}/confirm")
    fun confirmDebt(@PathVariable id: Long) {
        debtService.confirmDebt(id)
    }

    @GetMapping("/{id}/resolve")
    fun resolveDebt(@PathVariable id: Long) {
        debtService.requestOrResolveDebt(id)
    }

    @PostMapping
    fun createDebt(@RequestBody @Valid debt: DebtDto) {
        val newDebt = debtService.saveDebt(debt)
        debtService.requestConfirmingDebt(newDebt.id!!)
    }

//    @GetMapping("")
//    fun requestResolvingDebt(@PathVariable id: Long) {
//        debtService.requestResolvingDebt(id)
//    }




}