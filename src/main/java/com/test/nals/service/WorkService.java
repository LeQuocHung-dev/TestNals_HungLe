package com.test.nals.service;

import com.test.nals.domain.WorkRequest;
import com.test.nals.entity.Work;
import com.test.nals.exception.BadRequestException;
import com.test.nals.exception.ErrorCode;
import com.test.nals.repository.WorkRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service(value = "workServiceBean")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Getter
@Setter
public class WorkService {

    @Autowired
    WorkRepository workRepository;

    public WorkService() {
    }

    public void createWork(WorkRequest workRequest) {
        log.info("Create new work {} with status {}", workRequest.getWorkName(), workRequest.getStatus());
        if (workRepository.isExist(workRequest.getIdWork())) {
            throw new BadRequestException(ErrorCode.WORK_EXISTED);
        }
        workRepository.save(createOrUpdateWork(workRequest));
        log.info("The work {} has just created", workRequest.getWorkName());
    }

    public void editWork(String idWork, WorkRequest workRequest) {
        log.info("Update work ()", idWork);
        workRepository.update(createOrUpdateWork(workRequest));
        log.info("Work {} is updated", idWork);
    }

    public void removeWork(String idWork) {
        log.info("Delete work {}", idWork);
        workRepository.delete(idWork);
        log.info("Work {} is deleted", idWork);
    }

    public Page<Work> getListWork(int page, int size, String sortType, String sortField) {
        Sort sortable = null;
        if ("ASC".equals(sortType)) {
            sortable = Sort.by(sortField).ascending();
        }
        if ("DESC".equals(sortType)) {
            sortable = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);

        return workRepository.getListWork(pageable);
    }

    private boolean isValidTime(LocalDateTime startingDate, LocalDateTime endingDate) {
        if (startingDate.isAfter(endingDate)) {
            return false;
        }
        return true;
    }

    private Work createOrUpdateWork(WorkRequest workRequest) {
        Work work = new Work();
        work.setId(workRequest.getIdWork());
        work.setWorkName(workRequest.getWorkName());
        if (!isValidTime(workRequest.getStartingDate(), workRequest.getEndingDate())) {
            throw new BadRequestException(ErrorCode.TIME_INVALID);
        }
        work.setStartingDate(workRequest.getStartingDate());
        work.setEndingDate(workRequest.getEndingDate());
        work.setStatus(workRequest.getStatus());
        return work;
    }
}
