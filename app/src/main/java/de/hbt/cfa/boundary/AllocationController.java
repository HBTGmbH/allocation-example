package de.hbt.cfa.boundary;

import de.hbt.cfa.domain.allocation.AllocationDTO;
import de.hbt.cfa.domain.allocation.AllocationService;
import de.hbt.cfa.domain.allocation.TimeSlotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/allocation")
public class AllocationController {

    private final AllocationService allocationService;

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<AllocationDTO> getAllocationForActivity(@PathVariable("activityId") Long activityId) {
        AllocationDTO allocation = allocationService.getAllocation(activityId);
        return allocation == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(allocation);
    }

    @PostMapping("/activity/{activityId}")
    public ResponseEntity<AllocationDTO> updateParticipants(@PathVariable("activityId") Long activityId, @RequestBody List<TimeSlotDTO> timeSlots) {
        AllocationDTO allocation = allocationService.updateParticipants(activityId, timeSlots);
        return allocation == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(allocation);
    }

}
