package com.hedza06.cleanarch.customer.adapter.out.persistence.repositories;

import com.hedza06.cleanarch.customer.adapter.out.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> { }
