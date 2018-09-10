package com.hkt.rms.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hkt.rms.bean.admin.HousekeepCommand;

public interface HousekeepCommandRepository extends JpaRepository<HousekeepCommand,String>{
   /* @Query("select description, sql_command, seq_no, enable_ind, create_date, last_updated_date, last_updated_by from housekeep_command order by seq_no")
    List<HousekeepCommand> findByOrderByseq_no();*/

    HousekeepCommand findByDescription(String description);
    
    List<HousekeepCommand> findByOrderBySeqNoAsc();
}
