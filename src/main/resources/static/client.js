document.addEventListener('DOMContentLoaded', function () {
    // Add event listeners for drag and drop functionality
    const timeSlotElements = document.querySelectorAll('.drop-target');
    timeSlotElements.forEach(function (timeSlotElement) {
        timeSlotElement.addEventListener('dragover', allowDrop);
        timeSlotElement.addEventListener('drop', drop);
    });

    // Load the initial state from the server when the page is loaded
    fetch('/allocation')
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to load participant data');
            }
        })
        .then(updateAllocation)
        .catch(function (error) {
            console.error(error);
        });
});

function updateAllocation(allocation) {
    const unallocatedParticipants = document.getElementById('participant-list');
    unallocatedParticipants.innerHTML = '';
    allocation.unassignedParticipants.forEach(function (participant) {
        addParticipant(participant, unallocatedParticipants);
    });

    allocation.activities[0].timeSlots.forEach(function (timeSlot) {
        let timeSlotElement = addTimeSlot(timeSlot, document.getElementById('time-slot-list'));

        timeSlot.participants.forEach(function (participant) {
            addParticipant(participant, timeSlotElement);
        });
    });
}

function addParticipant(participant, parentElement) {
    const participantElement = document.createElement('div');
    participantElement.id = 'participant-' + participant.id;
    participantElement.className = 'participant';
    participantElement.draggable = true;
    participantElement.textContent = participant.name;
    participantElement.addEventListener('dragstart', drag);
    parentElement.appendChild(participantElement);
}

function addTimeSlot(timeSlot, parentElement) {
    const timeSlotHeader = document.createElement('div');

    timeSlotHeader.className = 'time-slot-header';
    timeSlotHeader.textContent = timeSlot.name;

    const timeSlotElement = document.createElement('div');

    timeSlotElement.className = 'time-slot drop-target';
    timeSlotElement.id = 'timeSlot-' + timeSlot.id;
    timeSlotElement.appendChild(timeSlotHeader);
    timeSlotElement.addEventListener('dragover', allowDrop);
    timeSlotElement.addEventListener('drop', drop);

    parentElement.appendChild(timeSlotElement);

    return timeSlotElement;
}

function drag(event) {
    event.dataTransfer.setData("text", event.target.id);
}

function allowDrop(event) {
    event.preventDefault();
}

function drop(event) {
    event.preventDefault();
    const data = event.dataTransfer.getData("text");
    const draggedElement = document.getElementById(data);
    const targetSlot = event.target.closest('.time-slot');

    Array.from(document.getElementsByClassName('full-course'))
        .forEach(function (element) {
            element.classList.remove('full-course');
        });

    const participantCount = targetSlot.getElementsByClassName('participant').length;
    if (participantCount >= 2) {
        targetSlot.classList.add('full-course');
        return;
    }

    let participant = draggedElement.cloneNode(true);
    participant.addEventListener('dragstart', drag);
    targetSlot.appendChild(participant);
    draggedElement.parentNode.removeChild(draggedElement);

    updateTimeSlots();
}

function updateTimeSlots() {
    // const timeSlots = Array.from(document.querySelectorAll('.time-slot'));
    // const participants = [];
    //
    // timeSlots.forEach(function(timeSlot) {
    //     const participantElements = Array.from(timeSlot.getElementsByClassName('participant'));
    //     participantElements.forEach(function(participantElement) {
    //         participants.push({
    //             id: participantElement.id,
    //             name: participantElement.textContent,
    //             timeSlot: timeSlot.id
    //         });
    //     });
    // });
    //
    // fetch('/participants', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(participants)
    // })
    //     .then(function(response) {
    //         if (!response.ok) {
    //             throw new Error('Failed to update participant distribution');
    //         }
    //     })
    //     .catch(function(error) {
    //         console.error(error);
    //     });
}