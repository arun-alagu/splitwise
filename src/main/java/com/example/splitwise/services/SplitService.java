package com.example.splitwise.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.splitwise.models.PercentageSplit;
import com.example.splitwise.models.RatioSplit;
import com.example.splitwise.repository.PercentageSplitRepository;
import com.example.splitwise.repository.RatioSplitRepository;
import org.springframework.stereotype.Service;

import com.example.splitwise.models.Split;
import com.example.splitwise.repository.SplitRepository;

@Service
public class SplitService {
    private final SplitRepository splitRepository;
    private final PercentageSplitRepository percentageSplitRepository;
    private final RatioSplitRepository ratioSplitRepository;
    private final UserService userService;
    
    public SplitService(SplitRepository splitRepository
            ,PercentageSplitRepository percentageSplitRepository
            ,RatioSplitRepository ratioSplitRepository
            ,UserService userService) {

        this.splitRepository = splitRepository;
        this.percentageSplitRepository = percentageSplitRepository;
        this.ratioSplitRepository = ratioSplitRepository;
        this.userService = userService;
    }

    // GET Mapping
    public Split getSplit(UUID splitId){
        return splitRepository.findById(splitId)
        .orElseThrow(() -> new RuntimeException("Split Not Found"));
    }

    // POST Mapping
    public Split createSplit(Split split){
        return splitRepository.save(split);
    }

    public PercentageSplit createPercentageSplit(PercentageSplit percentageSplit){
        return percentageSplitRepository.save(percentageSplit);
    }

    public RatioSplit createRatioSplit(RatioSplit ratioSplit){
        return ratioSplitRepository.save(ratioSplit);
    }

    // UPDATE Mapping
    public Split updateSplit(Split newSplit, UUID id){
        Split split = getSplit(id);

        split.setAmount(newSplit.getAmount());
        split.setUser(newSplit.getUser());

        return splitRepository.save(split);

    }

    public void deleteSplits(Set<Split> splits){
        for(Split split : splits){
            splitRepository.deleteById(split.getId());
        }
    }
}
