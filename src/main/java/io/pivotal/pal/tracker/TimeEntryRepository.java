package io.pivotal.pal.tracker;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry timeEntry);
    public TimeEntry find(long id);

    public List<TimeEntry> list();

    public TimeEntry update(long id, TimeEntry timeEntry);

    public void delete(long id);
}

