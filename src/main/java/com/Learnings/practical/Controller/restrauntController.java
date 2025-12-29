package com.Learnings.practical.Controller;

import com.Learnings.practical.Entity.RestrauntEntity;
import com.Learnings.practical.Repositry.RestrauntRepositry;
import com.Learnings.practical.RestrauntService.RestrauntService;
import com.Learnings.practical.dto.restrauntdto;
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
    public restrauntdto createRestraunt(@RequestBody restrauntdto restrauntdto) {
        return restrauntService.createRestraunt(restrauntdto);
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
//    @GetMapping
//    public List<restrauntdto> getAllRestraunts(@RequestParam(required = false) String name,
//                                               @RequestParam(required = false) String sortBy) {
//        List<RestrauntEntity> entities = restrauntRepositry.findAll();
//        return entities.stream()
//                .map(entity -> new restrauntdto(entity.getId(), entity.getName()))
//                .toList();
//    }
    @DeleteMapping(path = "/{restrauntId}")
    public String deleteRestraunt(@PathVariable Long restrauntId) {
        // 1. VALIDATION: Check the Database to see if the ID exists before acting.
//        if(!restrauntRepositry.existsById(restrauntId)) {
//            throw new RuntimeException("Logic Error: Cannot delete what does not exist. ID: " + restrauntId);
//        }

        // 2. ACTION: Tell the repository to remove the record from the disk.
        restrauntRepositry.deleteById(restrauntId);

        // 3. RESPONSE: Return a simple string confirmation (No DTO needed).
        return "Restaurant with ID " + restrauntId + " has been deleted.";
    }

    @PutMapping(path = "/{restrauntId}")
    public restrauntdto updateRestraunt(@PathVariable Long restrauntId, @RequestBody restrauntdto inputDto) {
        // 1. RETRIEVAL: Find the existing record that we want to overwrite.
         //  type 1: most basic way
        RestrauntEntity existingEntity = restrauntRepositry.findById(restrauntId)
                .orElseThrow(() -> new RuntimeException("Update Failed: Restaurant not found."));



        /* logic brute force checking  the manual way
        Optional<RestrauntEntity> optionalEntity = restrauntRepositry.findById(restrauntId);

        if (optionalEntity.isPresent()) {
      // Use .get() to take the Entity out of the Optional box
        RestrauntEntity entity = optionalEntity.get();

       // Now you can use setName
        entity.setName(inputDto.getName());
          restrauntRepositry.save(entity);
       } manual way */

/* direct way
restrauntRepositry.findById(restrauntId).ifPresent(entity -> {
    // This code ONLY runs if the entity was found
    entity.setName(inputDto.getName());
    restrauntRepositry.save(entity);
});
*/


        //Optional<RestrauntEntity> existingEntity = restrauntRepositry.findById(restrauntId);
       // this is wrong  at we caanot use setname as we are  holding the contioner which will have wether
        // the  entity has value rather then  hopling the restrroentity   so this is the mistake

        // 2. MAPPING (DTO -> Entity): Overwrite the old database values with the new User input.
        existingEntity.setName(inputDto.getName());

        // 3. PERSISTENCE: Save the updated Entity back to the database.
        RestrauntEntity updatedEntity = restrauntRepositry.save(existingEntity);

        // 4. RESPONSE (Entity -> DTO): Return the updated data back to the User.
        return new restrauntdto(updatedEntity.getId(), updatedEntity.getName());
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
