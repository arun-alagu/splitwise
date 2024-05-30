package com.example.splitwise.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.splitwise.dtos.CreateSplitRequest;
import com.example.splitwise.dtos.GetSplitResponse;
import com.example.splitwise.models.Split;
import com.example.splitwise.repository.SplitRepository;

@Service
public class SplitService {
    private final SplitRepository splitRepository;
    private final UserService userService;
    
    public SplitService(SplitRepository splitRepository, UserService userService) {
        this.splitRepository = splitRepository;
        this.userService = userService;
    }

    // GET Mapping
    public Split getSplit(UUID splitId){
        return (Split) splitRepository.findById(splitId)
        .orElseThrow(() -> new RuntimeException("Split Not Found"));
    }

    // POST Mapping
    public Split createSplit(CreateSplitRequest request){
        Split split = Split.builder()
        .user(userService.getUser(request.getUser()))
        .build();

        Optional.ofNullable(request.getAmount()).ifPresent(split::setAmount);
        Optional.ofNullable(request.getPercentage()).ifPresent(split::setPercentage);
        Optional.ofNullable(request.getRatio()).ifPresent(split::setRatio);
 
        return splitRepository.save(split);
    }

    // UPDATE Mapping
    public Split updateSplit(GetSplitResponse request){
        Split split = getSplit(request.getId());

        split.setAmount(request.getAmount());
        split.setUser(userService.getUser(request.getUser()));
        split.setPercentage(request.getPercentage());
        split.setRatio(request.getRatio());

        return splitRepository.save(split);

    }
    
}
