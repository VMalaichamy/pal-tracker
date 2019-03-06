package io.pivotal.pal.tracker;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    List<TimeEntry> timeEntryList = new ArrayList<>();
    long counter = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        counter++;
        timeEntry.setId(counter);
        timeEntryList.add(timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        TimeEntry timeEntry = null;
        for (TimeEntry t : timeEntryList){
             if ( id == t.getId()){
                 return  t;
             }
        }
        return null;
    }

    public List<TimeEntry> list() {
        return timeEntryList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        for (TimeEntry t : timeEntryList){
            if ( id == t.getId()){
                timeEntryList.remove(t);
                timeEntry.setId(id);
                timeEntryList.add(timeEntry);
                return  timeEntry;
            }
        }
        return null;
    }

    public void delete(long id) {
        for (TimeEntry t : timeEntryList){
            if ( id == t.getId()){
                timeEntryList.remove(t);
                break;
            }
        }
    }
}
