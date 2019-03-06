package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.pivotal.pal.tracker.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
public class TimeEntryController {

    @Autowired
    TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;
    private TimeEntryController timeEntriesRepo;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry){
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");


    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){

        TimeEntry returnBody = timeEntryRepository.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return new ResponseEntity<>(returnBody, HttpStatus.CREATED);

    }


    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable(value = "id",required = true)long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry t = timeEntryRepository.update(timeEntryId, expected);
        if (t == null){
            actionCounter.increment();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
        actionCounter.increment();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(value = "id",required = true)long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        actionCounter.increment();
         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value = "id",required = true) long timeEntryId) {
       TimeEntry t = timeEntryRepository.find(timeEntryId);
       if (t == null){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }else{
           actionCounter.increment();
           return new ResponseEntity<>(t, HttpStatus.OK);
       }
    }
}
