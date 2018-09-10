package com.hkt.rms.dao;

import com.hkt.rms.bean.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditTrail,Integer> {
    List<AuditTrail> findByStaffNum(int staffNum);
}
