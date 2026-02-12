package com.casino.slotsystem.service;

import com.casino.slotsystem.dto.SlotHistoryResponse;
import com.casino.slotsystem.repository.SlotHistoryRepository;
import org.springframework.cache.annotation.Cacheable;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDateTime;


@Service
public class SlotHistoryService {

    private final SlotHistoryRepository repository;

    public SlotHistoryService(SlotHistoryRepository repository) {
        this.repository = repository;
    }
    // @Cacheable(
    //         value = "slotHistory",
    //         key = "#slotId + ':' + #page"
    // )
    // public List<SlotHistoryResponse> getSlotHistory(
    //         Long slotId,
    //         int page
    // ) {
    //     // 1 month ≈ 30 records
    //     PageRequest pageable = PageRequest.of(
    //             page,
    //             30,
    //             Sort.by("changedAt").descending()
    //     );

    //     return repository
    //             .findBySlotIdOrderByChangedAtDesc(slotId, pageable)
    //             .map(h -> new SlotHistoryResponse(
    //                     h.getNumber(),
    //                     h.getChangedAt()
    //             ))
    //             .getContent();
    // }

    @Cacheable(
        value = "slotHistory",
        key = "#slotId"
)
public List<SlotHistoryResponse> getSlotHistory(Long slotId) {

    LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

    return repository
            .findBySlotIdAndChangedAtAfterOrderByChangedAtDesc(
                    slotId,
                    oneMonthAgo
            )
            .stream()
            .map(h -> new SlotHistoryResponse(
                    h.getNumber(),
                    h.getChangedAt()
            ))
            .toList();
}
}



