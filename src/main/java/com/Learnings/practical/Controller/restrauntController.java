package com.Learnings.practical.Controller;

import com.Learnings.practical.Entity.RestrauntEntity;
import com.Learnings.practical.Repositry.RestrauntRepositry;
import com.Learnings.practical.RestrauntService.RestrauntService;
import com.Learnings.practical.dto.restrauntdto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path ="/restraunt")
public class restrauntController {

    private  final RestrauntRepositry restrauntRepositry;

    private final RestrauntService restrauntService;

    public restrauntController(RestrauntRepositry restrauntRepositry, RestrauntService restrauntService) {
        this.restrauntRepositry = restrauntRepositry;
        this.restrauntService = restrauntService;
    }

    @PostMapping
    public ResponseEntity<restrauntdto> createRestraunt(@RequestBody restrauntdto restrauntdto) {
        return new ResponseEntity<>(restrauntService.createRestraunt(restrauntdto), HttpStatus.CREATED);
    }






//    @PostMapping
//    public restrauntdto createRestraunt(@RequestBody restrauntdto restrauntdto) {
//// converting dto into entity
//        RestrauntEntity entity = new RestrauntEntity();
//        entity.setName(restrauntdto.getName());
//// 2. Save the Entity via the Repository
//        RestrauntEntity savedEntity = restrauntRepositry.save(entity);
//
//// 3. Convert the saved Entity back to a DTO to return it to the user
//        return new restrauntdto(savedEntity.getId(), savedEntity.getName());
//
//    }


//    @GetMapping(path = "/{restrauntId}")
//    public restrauntdto getRestrauntById(@PathVariable Long restrauntId) {
//         return restrauntService.getRestrauntById(restrauntId);
//    }



    @GetMapping
    public List<restrauntdto> getAllRestraunts(){
        return restrauntService.getAllRestraunts();
    }
//@GetMapping("/{id}")
//public ResponseEntity<restrauntdto> getRestrauntById(@PathVariable Long id) {
//    Optional<restrauntdto> restrauntdto = Optional.ofNullable(restrauntService.getRestrauntById(id));
//    return restrauntdto
//            .map(ResponseEntity::ok)
//            .orElse(ResponseEntity.notFound().build());
//}


//    @GetMapping
//    public ResponseEntity<List<restrauntdto>> getAllEmployees(@RequestParam(required = false, name = "inputname") Integer id,
//                                                              @RequestParam(required = false) String sortBy) {
//        return ResponseEntity.ok(restrauntService.getAllRestraunts());
//    }
//    @GetMapping
//    public List<restrauntdto> getAllRestraunts(@RequestParam(required = false) String name,
//                                               @RequestParam(required = false) String sortBy) {
//        List<RestrauntEntity> entities = restrauntRepositry.findAll();
//        return entities.stream()
//                .map(entity -> new restrauntdto(entity.getId(), entity.getName()))
//                .toList();
//    }
    @DeleteMapping(path = "/{restrauntId}")
    public ResponseEntity<Boolean> deleteRestraunt(@PathVariable Long restrauntId) {
        if (!restrauntRepositry.existsById(restrauntId)) {
            return ResponseEntity.notFound().build();
        }
        restrauntRepositry.deleteById(restrauntId);
        return ResponseEntity.ok(true);
    }

    @PutMapping(path = "/{restrauntId}")
    public ResponseEntity<restrauntdto> updateRestraunt(@PathVariable Long restrauntId, @RequestBody restrauntdto inputDto) {
        if (!restrauntRepositry.existsById(restrauntId)) {
            return ResponseEntity.notFound().build();
        }
        RestrauntEntity existingEntity = restrauntRepositry.findById(restrauntId).get();
        existingEntity.setName(inputDto.getName());
        RestrauntEntity updatedEntity = restrauntRepositry.save(existingEntity);
        return ResponseEntity.ok(new restrauntdto(updatedEntity.getId(), updatedEntity.getName()));
    }



  /*
  * // 1. POST with RequestParam
    // Logic: Data is passed in the URL like /restraunt?name=PizzaHut
    @PostMapping
    public restrauntdto createRestraunt(@RequestParam String name) {
        // STEP 1: CONVERSION (Param -> Entity)
        // We take the single string from the URL and build our Entity.
        RestrauntEntity entity = new RestrauntEntity();
        entity.setName(name);

        // STEP 2: PERSISTENCE
        // Save to DB to generate the ID.
        RestrauntEntity savedEntity = restrauntRepositry.save(entity);

        // STEP 3: RESPONSE (Entity -> DTO)
        return new restrauntdto(savedEntity.getId(), savedEntity.getName());
    }

  *@PutMapping("/{restrauntId}")
    public restrauntdto updateRestraunt(
            @PathVariable Long restrauntId,
            @RequestParam String newName) {

        // STEP 1: RETRIEVAL
        RestrauntEntity entity = restrauntRepositry.findById(restrauntId)
                .orElseThrow(() -> new RuntimeException("Not Found"));

        // STEP 2: UPDATE
        entity.setName(newName);

        // STEP 3: PERSISTENCE & RETURN
        RestrauntEntity saved = restrauntRepositry.save(entity);
        return new restrauntdto(saved.getId(), saved.getName());
    }
   */






















}
