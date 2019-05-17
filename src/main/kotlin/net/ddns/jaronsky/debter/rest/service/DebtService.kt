package net.ddns.jaronsky.debter.rest.service

import net.ddns.jaronsky.debter.model.Debt
import net.ddns.jaronsky.debter.model.DebtStatus
import net.ddns.jaronsky.debter.model.security.AuthorityName
import net.ddns.jaronsky.debter.model.security.User
import net.ddns.jaronsky.debter.rest.repository.DebtRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * Created by Wojciech Jaronski
 *
 */

@Service
class DebtService(
        private val debtRepository: DebtRepository,
        private val userService: UserService
) {

    fun getMyDebts(): List<Debt> {
        val user = userService.findUserByName(SecurityContextHolder.getContext().authentication.name)
        return getDebtsOfUser(user)
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun getAllDebts(): List<Debt> {
        return emptyList()
    }

    fun getDebtsOfUser(user: User): List<Debt> {
        return debtRepository.findMyDebts(user)
    }


    fun saveDebt(debt: Debt): Debt {
        return debtRepository.save(debt)
    }

    fun updateDebt(debt: Debt, id: Number): Debt {
        debt.id = id as Long
        return debtRepository.save(debt)

    }

    private fun isAdmin(): Boolean {
        return SecurityContextHolder.getContext().authentication.authorities.map { a -> a.authority!! }.contains(AuthorityName.ROLE_ADMIN.name)
    }

    private fun user(): String {
        return SecurityContextHolder.getContext().authentication.name
    }

    private fun hasAccessToDebt(username: String, debt: Debt): Boolean {
        return isAdmin() || debt.creditor?.username === username || debt.debtor?.username === username
    }

    private fun canConfirmDebt(username: String, debt: Debt): Boolean {
        return isAdmin() || (debt.status === DebtStatus.NOT_CONFIRMED && debt.toConfirmBy === username)
    }

    private fun canResolveDebt(username: String, debt: Debt): Boolean {
        return isAdmin() || (debt.status === DebtStatus.CONFIRMED && debt.toConfirmBy === username)
    }

    private fun getOppositeUserOfDebt(debt: Debt): String {
        return if (user().equals(debt.creditor?.username)) debt.debtor?.username!! else debt.creditor?.username!!
    }

    fun requestConfirmingDebt(id: Number) {
        val debt = debtRepository.findById(id as Long).get()
        if (hasAccessToDebt(user(), debt)) {
            debt.toConfirmBy = getOppositeUserOfDebt(debt)
            debtRepository.save(debt)
        }
    }

    fun confirmDebt(id: Number) {
        val debt = debtRepository.findById(id as Long).get()
        if (hasAccessToDebt(user(), debt)) {
            if (!canConfirmDebt(user(), debt)) {
                System.err.println("${user()} nie może sam sobie potwierdzić długu !")
                throw SelfConfirmException("${user()} nie może sam sobie potwierdzić długu !")
            }
            debt.toConfirmBy = null
            debt.status = DebtStatus.CONFIRMED
            debtRepository.save(debt)
        }
    }

    fun requestResolvingDebt(id: Number) {
        val debt = debtRepository.findById(id as Long).get()
        if (hasAccessToDebt(user(), debt)) {
            debt.toConfirmBy = getOppositeUserOfDebt(debt)
            debtRepository.save(debt)
        }
    }

    fun resolveDebt(id: Number) {
        val debt = debtRepository.findById(id as Long).get()
        if (hasAccessToDebt(user(), debt)) {
            if (!canResolveDebt(user(), debt)) {
                System.err.println("${user()} nie może sam sobie rozwiązać długu !")
                throw SelfConfirmException("${user()} nie może sam sobie rozwiązać długu !")
            }
            debt.toConfirmBy = null
            debt.status = DebtStatus.RESOLVED
            debtRepository.save(debt)
        }
    }

}