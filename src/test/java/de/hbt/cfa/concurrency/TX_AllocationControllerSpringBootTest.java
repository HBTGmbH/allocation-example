package de.hbt.cfa.concurrency;

import de.hbt.cfa.domain.allocation.AllocationDTO;
import de.hbt.cfa.domain.allocation.AllocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static de.hbt.cfa.domain.allocation.AllocationFixtures.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TX_AllocationControllerSpringBootTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AllocationService allocationService;

    /**
     * Set a breakpoint in {@link de.hbt.cfa.boundary.AllocationController#getAllocationForActivity} and look at which thread is executing the method
     * Then compare to the thread that is executing when running {@link de.hbt.cfa.boundary.AllocationControllerTest}, which is a WebMvcTest
     */
    @Test
    public void shouldReturnTimeSlots() {
        //given
        var expectedAllocation = allocationDTO(singleParticipantDTO("lri"),
                activityWithTimeSlotsDTO(singleTimeSlotDTOWithParticipant("Slot 1", "sku")));
        when(allocationService.getAllocation(1L))
                .thenReturn(expectedAllocation);

        //when/then
        webTestClient.get()
                .uri("/allocation/activity/{activityId}", 1L)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AllocationDTO.class)
                .isEqualTo(expectedAllocation);
    }
}
