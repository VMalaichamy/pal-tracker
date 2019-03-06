package io.pivotal.pal.tracker;

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

    public TimeEntryController(TimeEntryRepository timeEntryRepository){
        this.timeEntryRepository = timeEntryRepository;

    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){

        TimeEntry returnBody = timeEntryRepository.create(timeEntry);
        return new ResponseEntity<>(returnBody, HttpStatus.CREATED);

    }


    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable(value = "id",required = true)long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry t = timeEntryRepository.update(timeEntryId, expected);
        if (t == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(t, HttpStatus.OK);
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
        return new ResponseEntity(list,HttpStatus.OK);
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(value = "id",required = true)long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
         return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value = "id",required = true) long timeEntryId) {
       TimeEntry t = timeEntryRepository.find(timeEntryId);
       if (t == null){
           return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }else{
           return new ResponseEntity<>(t, HttpStatus.OK);
       }
    }
}
