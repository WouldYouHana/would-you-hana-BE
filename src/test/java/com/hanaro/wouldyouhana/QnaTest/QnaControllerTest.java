package com.hanaro.wouldyouhana.QnaTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanaro.wouldyouhana.domain.Question;
import com.hanaro.wouldyouhana.dto.QuestionAddRequestDTO;
import com.hanaro.wouldyouhana.repository.QuestionRepository;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QnaControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper jacksonObjectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AssertTrueValidator assertTrueValidator;

    @Test
    public void addQuestion() throws Exception {
        // given
        final String url = "/post";
        final QuestionAddRequestDTO questionAddRequestDTO = new QuestionAddRequestDTO("test", 1L, 1L, "서울시 성북구", "안녕하세요", null);
        final String requestBody = jacksonObjectMapper.writeValueAsString(questionAddRequestDTO);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());
        List<Question> questions = questionRepository.findAll();
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.get(0).getTitle()).isEqualTo("test");
        assertThat(questions.get(0).getContent()).isEqualTo("안녕하세요");

    }
}
