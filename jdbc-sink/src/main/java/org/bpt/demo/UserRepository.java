package org.bpt.demo;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<Customer, Long> {
}
