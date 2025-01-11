package com.brcevss.cv_generator_batch.unit.jobs;

import com.brcevss.cv_generator_batch.jobs.writer.CvPdfWriter;
import com.brcevss.cv_generator_batch.service.ChatGptService;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CvPdfWriterTest {

    @InjectMocks
    private CvPdfWriter cvPdfWriter;

    @Mock
    private ChatGptService chatGptService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*    @Test
    public void testWrite() throws Exception {
        Candidat candidat = new Candidat();
        candidat.setNom("Doe");
        candidat.setPrenom("John");
        candidat.setEmail("john.doe@example.com");
        candidat.setCompetences(Collections.singletonList("Java"));

        when(chatGptService.generateText(anyString())).thenReturn("Generated resume");

        Chunk<Candidat> chunk = new Chunk<>(Collections.singletonList(candidat));
        cvPdfWriter.write(chunk);

        verify(chatGptService, times(1)).generateText(anyString());
    }*/
}
