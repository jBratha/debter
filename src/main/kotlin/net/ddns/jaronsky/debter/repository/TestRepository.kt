package net.ddns.jaronsky.debter.repository

import net.ddns.jaronsky.debter.model.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: CrudRepository<Customer, Long> {
}