package com.unicorns.challenge.CodingAssignment.api;

import com.unicorns.challenge.CodingAssignment.dto.Unicorn;
import com.unicorns.challenge.CodingAssignment.dto.UnicornResponse;
import com.unicorns.challenge.CodingAssignment.exception.UnicornException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UnicornController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/unicorns")
    public UnicornResponse unicorns(@RequestBody Unicorn unicorn) throws UnicornException {
        try {
            log.info("New Unicorns request: {}", unicorn);
            Unicorn result = mongoTemplate.save(unicorn);
            log.info("Unicorn Inserted Id: {}", result.getUnicornId());
            return new UnicornResponse(result.getUnicornId());
        }catch (Exception e){
            throw new UnicornException("Unicorn data Insert failed");
        }
    }

    @GetMapping("/unicorns")
    public List<Unicorn> unicorns(){
        return mongoTemplate.findAll(Unicorn.class);
    }

    @GetMapping("/unicorns/{unicornId}")
    public Unicorn unicorns(@PathVariable String unicornId){
        Query query = getUnicornById(unicornId);
        return mongoTemplate.findOne(query,Unicorn.class);
    }

    private Query getUnicornById(String unicornId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(unicornId));
        return query;
    }

    @PutMapping("/unicorns/{unicornId}")
    public UnicornResponse  updateUnicorn(@RequestBody Unicorn unicorn, @PathVariable String unicornId) throws UnicornException {
        try {
            Query query = getUnicornById(unicornId);
            Unicorn result = mongoTemplate.findOne(query, Unicorn.class);

            unicorn.setUnicornId(result.getUnicornId());

            Unicorn finalResult = mongoTemplate.save(unicorn);
            log.info("Updated Unicorn id: {}", finalResult.getUnicornId());

            return new UnicornResponse(finalResult.getUnicornId());
        }catch (Exception e){
            throw new UnicornException("Unicorn data update failed.");
        }

    }
}
