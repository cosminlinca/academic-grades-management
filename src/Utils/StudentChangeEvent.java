package Utils;

import Domain.Student;
import Domain.StudentiEntity;
import Utils.*;

public class StudentChangeEvent implements Event {
    private EventType eventType;
    private StudentiEntity data, oldData;

    public StudentChangeEvent(EventType eventType, StudentiEntity data) {
        this.eventType = eventType;
        this.data = data;
    }

    public StudentChangeEvent(EventType eventType, StudentiEntity data, StudentiEntity oldData) {
        this.eventType = eventType;
        this.data = data;
        this.oldData = oldData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public StudentiEntity getData() {
        return data;
    }

    public StudentiEntity getOldData() {
        return oldData;
    }
}
