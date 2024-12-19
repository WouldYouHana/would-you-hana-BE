package com.hanaro.wouldyouhana.repository;

import com.hanaro.wouldyouhana.domain.Location;
import com.hanaro.wouldyouhana.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByCustomer_Id(Long customerId);
    Location findByCustomerAndLocation(Customer customer, String location);
}
