package com.Learnings.practical.Repositry;

import com.Learnings.practical.Entity.RestrauntEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestrauntRepositry extends JpaRepository <RestrauntEntity, Long>{
 }
