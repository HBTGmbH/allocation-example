document.addEventListener('DOMContentLoaded', () => {
    // Add event listeners for drag and drop functionality
    const dropTargets = document.querySelectorAll('.drop-target');
    dropTargets.forEach(dropTarget => {
        dropTarget.addEventListener('dragover', allowDrop);
        dropTarget.addEventListener('drop', drop);
    });

    // Load the initial state from the server when the page is loaded
    fetch('/allocation/activity/1')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to load allocation');
            }
        })
        .then(updateAllocation)
        .catch(error => {
            console.error(error);
        });
});

function updateAllocation(allocation) {
    const unassignedParticipants = document.getElementById('participant-list');
    unassignedParticipants.innerHTML = '';
    allocation.unassignedParticipants.forEach(participant => {
        addParticipant(participant, unassignedParticipants);
    });

    allocation.activity.timeSlots.forEach(timeSlot => {
        let timeSlotElement = addTimeSlot(timeSlot, document.getElementById('time-slot-list'));

        timeSlot.participants.forEach(participant => {
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
        .forEach(element => {
            element.classList.remove('full-course');
        });

    const participantCount = targetSlot.getElementsByClassName('participant').length;
    if (targetSlot.id !== 'participant-list' && participantCount >= 2) {
        targetSlot.classList.add('full-course');
        return;
    }

    let participant = draggedElement.cloneNode(true);
    participant.addEventListener('dragstart', drag);
    targetSlot.appendChild(participant);
    draggedElement.parentNode.removeChild(draggedElement);
}

function saveParticipants() {
    const timeSlots = Array.from(document.querySelectorAll('.time-slot'))
        .filter(timeSlot => timeSlot.id !== 'participant-list')
        .map(timeSlot => ({
            id: timeSlot.id.substring(timeSlot.id.indexOf('-') + 1),
            participants: Array.from(timeSlot.getElementsByClassName('participant'))
                .map(participant => ({
                    id: participant.id.substring(participant.id.indexOf('-') + 1),
                    name: participant.textContent
                }))
        }));

    fetch('/allocation/activity/1', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(timeSlots)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update allocation');
            }
        })
        .catch(error => {
            console.error(error);
        });
}