package Utils;

import Domain.NoteEntity;

public class NotaChangeEvent implements Event {
    private EventType eventType;
    private NoteEntity data, oldData;

    public NotaChangeEvent(EventType eventType, NoteEntity data) {
        this.eventType = eventType;
        this.data = data;
    }

    public NotaChangeEvent(EventType eventType, NoteEntity data, NoteEntity oldData) {
        this.eventType = eventType;
        this.data = data;
        this.oldData = oldData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public NoteEntity getData() {
        return data;
    }

    public void setData(NoteEntity data) {
        this.data = data;
    }

    public NoteEntity getOldData() {
        return oldData;
    }

    public void setOldData(NoteEntity oldData) {
        this.oldData = oldData;
    }
}
