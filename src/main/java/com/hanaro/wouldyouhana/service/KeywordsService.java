package com.hanaro.wouldyouhana.service;

import com.hanaro.wouldyouhana.domain.Keyword;
import com.hanaro.wouldyouhana.dto.KeywordsResponseDTO;
import com.hanaro.wouldyouhana.repository.PopularKeywordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordsService {

    private final PopularKeywordsRepository popularKeywordsRepository;

    public List<KeywordsResponseDTO> getPopularKeywords(String location) {
        List<Keyword> keywordsList = popularKeywordsRepository.findAllByLocation(location);
        return keywordsList.stream().map((popularKeywords) ->
                new KeywordsResponseDTO(popularKeywords.getKeyword())).toList();
    }
}
