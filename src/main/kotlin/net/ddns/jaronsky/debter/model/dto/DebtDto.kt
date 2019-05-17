package net.ddns.jaronsky.debter.model.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class DebtDto(
        @NotNull val creditor: String,
        @NotNull val debtor: String,
        @Min(0.01.toLong()) @NotNull val amount: Double,
        val description: String
)