package com.Learnings.practical.RestrauntService;

import com.Learnings.practical.Repositry.RestrauntRepositry;
import org.springframework.stereotype.Service;

@Service
public class RestrauntService {

    private final RestrauntRepositry restrauntRepositry;

    public RestrauntService(RestrauntRepositry restrauntRepositry) {
        this.restrauntRepositry = restrauntRepositry;
    }

}
