package com.vegapay.limitoffer.offer.repository;

import com.vegapay.limitoffer.offer.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitOfferRepository extends JpaRepository<OfferEntity, Integer> {


}
