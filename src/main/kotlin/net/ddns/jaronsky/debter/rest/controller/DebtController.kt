package net.ddns.jaronsky.debter.rest.controller

import net.ddns.jaronsky.debter.model.Debt
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

    @GetMapping("/confirm/{id}")
    fun confirmDebt(@PathVariable id: Number) {
        debtService.confirmDebt(id)
    }

    @PostMapping
    fun createDebt(@RequestBody @Valid debt: Debt) {
        debtService.saveDebt(debt)
        debtService.requestConfirmingDebt(debt.id!!)
    }

}