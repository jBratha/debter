package net.ddns.jaronsky.debter.rest.repository

import net.ddns.jaronsky.debter.model.Debt
import net.ddns.jaronsky.debter.model.security.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

/**
 * Created by Wojciech Jaronski
 *
 */

interface DebtRepository : CrudRepository<Debt, Long> {

    fun findByCreditorOrDebtor(creditor: Long, debtor: Long): List<Debt>

    @Query("SELECT d FROM Debt d WHERE d.creditor = :user or d.debtor = :user")
    fun findMyDebts(@Param("user") user: User): List<Debt>
}