package com.Learnings.practical.RestrauntService;

import com.Learnings.practical.Entity.RestrauntEntity;
import com.Learnings.practical.Repositry.RestrauntRepositry;
import com.Learnings.practical.dto.restrauntdto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestrauntService {

    private final RestrauntRepositry restrauntRepositry;
    private final ModelMapper modelMapper;

    public RestrauntService(RestrauntRepositry restrauntRepositry, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.restrauntRepositry = restrauntRepositry;
    }

   public restrauntdto  getRestrauntById(Long id ){
    RestrauntEntity entity = restrauntRepositry.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));;
    return modelMapper.map(entity , restrauntdto.class);
   }

   public List<restrauntdto> getAllRestraunts(){
        List<RestrauntEntity> entities = restrauntRepositry.findAll();
        return entities
                .stream()
                .map(restrauntEntity -> modelMapper.map(restrauntEntity , restrauntdto.class))
                .collect(Collectors.toList());
   }

   public restrauntdto createRestraunt(restrauntdto inputrestraunt){

        RestrauntEntity toSaveentity = modelMapper.map(inputrestraunt , RestrauntEntity.class);
        RestrauntEntity savedRestrauntEntity = restrauntRepositry.save(toSaveentity);

        return modelMapper.map(savedRestrauntEntity, restrauntdto.class);

   }

}
