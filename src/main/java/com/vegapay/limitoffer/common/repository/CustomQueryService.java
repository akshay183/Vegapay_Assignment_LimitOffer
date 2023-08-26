//package com.vegapay.limitoffer.common.repository;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class CustomQueryService {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public void updateLimitOfferStatus(Integer limitOfferId, String status) {
//
//        String nativeUpdateQuery = "UPDATE offer SET limit_offer_id = ?, " +
//                "status = ? WHERE condition_column = ?";
//
//        entityManager.createNativeQuery(nativeUpdateQuery)
//                .setParameter(1, limitOfferId)
//                .setParameter(2, status)
//                .executeUpdate();
//
//    }
//}
