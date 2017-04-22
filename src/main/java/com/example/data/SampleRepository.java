package com.example.data;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SampleRepository extends CrudRepository<Sample, Integer> {

    @Query("select s from Sample s where s.id in :ids")
    List<Sample> queryIn(@Param("ids") List<Integer> ids);
}
