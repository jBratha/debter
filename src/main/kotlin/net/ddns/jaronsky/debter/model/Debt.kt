package net.ddns.jaronsky.debter.model

import net.ddns.jaronsky.debter.model.security.User
import javax.persistence.*

/**
 * Created by Wojciech Jaronski
 *
 */

@Entity
@Table(name = "DEBTS")
class Debt(
        @Id
        @GeneratedValue
        var id: Long? = null,

        @JoinColumn(name = "DEBTOR", referencedColumnName = "username")
        @OneToOne
        val debtor: User? = null,

        @JoinColumn(name = "CREDITOR", referencedColumnName = "username")
        @OneToOne
        val creditor: User? = null,
        val amount: Double? = null,
        val description: String? = null,


        @Enumerated(EnumType.STRING)
        var status: DebtStatus? = null,
        var toConfirmBy: String? = null
) {
    override fun toString(): String {
        return "Debt[$id, $debtor jest winny $creditor, \'$description\', $status, $toConfirmBy]"
    }
}