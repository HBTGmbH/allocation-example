package de.hbt.cfa.boundary;

import de.hbt.cfa.domain.allocation.AllocationDTO;
import de.hbt.cfa.domain.allocation.AllocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static de.hbt.cfa.domain.allocation.AllocationFixtures.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AllocationController.class)
@AutoConfigureWebTestClient
class AllocationControllerTest {

    public static final long ACTIVITY_ID = 1L;
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AllocationService allocationService;

    @Test
    public void shouldReturnTimeSlots() {
        //given
        var expectedAllocation = allocationDTO(singleParticipantDTO("lri"),
                activityWithTimeSlotsDTO(singleTimeSlotDTOWithParticipant("sku")));
        when(allocationService.getAllocation(ACTIVITY_ID))
                .thenReturn(expectedAllocation);

        //when/then
        webTestClient.get()
                .uri("/allocation/activity/{activityId}", ACTIVITY_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AllocationDTO.class)
                .equals(expectedAllocation);
    }
}