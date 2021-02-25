package com.test.nals.repository;

import com.test.nals.entity.Work;
import org.springframework.data.domain.Page;

public interface WorkRepository {

    int count();

    boolean isExist(String idWork);

    void save(Work work);

    void update(Work work);

    void delete(String workId);

    Page<Work> getListWork(org.springframework.data.domain.Pageable pageable);

}
