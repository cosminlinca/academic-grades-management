package Utils;

import Domain.ProfesoriEntity;

public class ProfessorChangeEvent implements Event {
    private EventType eventType;
    private ProfesoriEntity data, oldData;

    public ProfessorChangeEvent(EventType eventType, ProfesoriEntity data) {
        this.eventType = eventType;
        this.data = data;
    }

    public ProfessorChangeEvent(EventType eventType, ProfesoriEntity data, ProfesoriEntity oldData) {
        this.eventType = eventType;
        this.data = data;
        this.oldData = oldData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public ProfesoriEntity getData() {
        return data;
    }

    public ProfesoriEntity getOldData() {
        return oldData;
    }
}
