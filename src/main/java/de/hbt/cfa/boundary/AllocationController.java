package de.hbt.cfa.boundary;

import de.hbt.cfa.domain.allocation.Allocation;
import de.hbt.cfa.domain.allocation.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/allocation")
public class AllocationController {

    private final AllocationService allocationService;

    @GetMapping
    public ResponseEntity<Allocation> getAllocations() {
        return ResponseEntity.ok(allocationService.getAllocation());
    }

    @PostMapping
    public ResponseEntity<Allocation> saveAllocations(@RequestBody Allocation allocation) {
        return ResponseEntity.ok(allocationService.saveAllocation(allocation));
    }

}
