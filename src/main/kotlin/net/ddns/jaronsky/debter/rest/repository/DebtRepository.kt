package net.ddns.jaronsky.debter.rest.repository

import net.ddns.jaronsky.debter.model.Debt
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

/**
 * Created by Wojciech Jaronski
 *
 */

interface DebtRepository : CrudRepository<Debt, Long> {

    fun findByCreditorOrDebtor(creditor: String, debtor: String): List<Debt>

    @Query("SELECT d FROM Debt d WHERE LOWER(d.creditor) = LOWER(:me) or LOWER(d.debtor) = LOWER(:me)")
    fun findMyDebts(@Param("me") me: String): List<Debt>
}