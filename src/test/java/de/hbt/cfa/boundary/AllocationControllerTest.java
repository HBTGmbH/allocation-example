package de.hbt.cfa.boundary;

import de.hbt.cfa.domain.allocation.Allocation;
import de.hbt.cfa.domain.allocation.AllocationService;
import de.hbt.cfa.entity.Activity;
import de.hbt.cfa.entity.Participant;
import de.hbt.cfa.entity.TimeSlot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AllocationController.class)
@AutoConfigureWebTestClient
class AllocationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AllocationService allocationService;

    @Test
    public void shouldReturnTimeSlots() {
        //given
        when(allocationService.getAllocation())
                .thenReturn(Allocation.builder()
                        .activity(Activity.builder()
                                .timeSlots(List.of(TimeSlot.builder()
                                        .name("time")
                                        .participant(Participant.builder().name("sku").build())
                                        .build()))
                                .build())
                        .build());

        //when/then
        webTestClient.get()
                .uri("/allocation")
                .exchange()
                .expectStatus()
                .isOk();
    }
}