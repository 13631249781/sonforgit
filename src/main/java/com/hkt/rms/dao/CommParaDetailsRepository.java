package com.hkt.rms.dao;

import java.util.List;

import com.hkt.rms.bean.admin.CommParaDetails;
import com.hkt.rms.bean.admin.HousekeepCommand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommParaDetailsRepository extends JpaRepository<CommParaDetails,Long> {
    List<CommParaDetails> findByMasterAppCd(String masterAppCode);

    Optional<CommParaDetails> findByAppCdAndMasterAppCd(String appCd, String masterAppCd);
}
