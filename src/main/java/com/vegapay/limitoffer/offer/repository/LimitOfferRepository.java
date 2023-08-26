package com.vegapay.limitoffer.offer.repository;

import com.vegapay.limitoffer.offer.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LimitOfferRepository extends JpaRepository<OfferEntity, Integer> {

    @Query("SELECT lo FROM OfferEntity lo " +
            "WHERE lo.accountId = :accountId " +
            "AND lo.status = 'Pending' " +
            "AND lo.offerActivationTime < :activeDate " +
            "AND lo.offerExpiryTime > :activeDate")
    List<OfferEntity> findActiveLimitOffers(
            @Param("accountId") Integer accountId,
            @Param("activeDate") LocalDate activeDate
    );

    @Query("SELECT lo FROM OfferEntity lo " +
            "WHERE lo.accountId = :accountId " +
            "AND lo.status = 'Pending'")
    List<OfferEntity> findActiveLimitOffers(
            @Param("accountId") Integer accountId
    );

}
