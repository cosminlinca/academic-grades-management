package Utils;

import Domain.Tema;
import Domain.TemeEntity;

public class TemaChangeEvent implements Event {
    private EventType eventType;
    private TemeEntity data, oldData;

    public TemaChangeEvent(EventType eventType, TemeEntity data) {
        this.eventType = eventType;
        this.data = data;
    }

    public TemaChangeEvent(EventType eventType, TemeEntity data, TemeEntity oldData) {
        this.eventType = eventType;
        this.data = data;
        this.oldData = oldData;
    }

    public TemeEntity getData() {
        return data;
    }

    public TemeEntity getOldData() {
        return oldData;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
