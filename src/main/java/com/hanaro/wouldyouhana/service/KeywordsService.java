package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.PopularKeywords;
import com.hanaro.wouldyouhana.dto.KeywordsResponseDTO;
import com.hanaro.wouldyouhana.repository.PopularKeywordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordsService {

    private final PopularKeywordsRepository popularKeywordsRepository;

    public List<KeywordsResponseDTO> getPopularKeywords(String location) {
        List<PopularKeywords> keywordsList = popularKeywordsRepository.findAllByLocation(location);
        return keywordsList.stream().map((popularKeywords) ->
                new KeywordsResponseDTO(popularKeywords.getKeyword())).toList();
    }
}
