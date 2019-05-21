package net.ddns.jaronsky.debter.model

import net.ddns.jaronsky.debter.model.security.User
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * Created by Wojciech Jaronski
 *
 */

@Entity
@Table(name = "DEBTS")
class Debt(
        @Id
//        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debt_seq")
//        @SequenceGenerator(name = "debt_seq", sequenceName = "debt_seq", initialValue = 7) //TODO remove initialValue
        @GeneratedValue
        var id: Long? = null,

        @JoinColumn(name = "debtor", referencedColumnName = "id")
        @OneToOne
        val debtor: User? = null,

        @JoinColumn(name = "creditor", referencedColumnName = "id")
        @OneToOne
        val creditor: User? = null,
        val amount: Double? = null,
        val description: String? = null,


        @Enumerated(EnumType.STRING)
        var status: DebtStatus? = null,
        var toConfirmBy: String? = null,
        @Temporal(TemporalType.TIMESTAMP)
        @NotNull
        val date: Date? = Date()

) {
    companion object {
//        fun newDebt(debtDTO: DebtDto){
//            return Debt(
//                    debtor = debtDTO.debtor,
//                    creditor =
//            )
//        }
    }

    override fun toString(): String {
        return "Debt[$id, $debtor jest winny $creditor, \'$description\', $status, $toConfirmBy]"
    }
}