package com.brcevss.cv_generator_batch.jobs.writer;

import com.brcevss.cv_generator_batch.model.Candidat;
import com.brcevss.cv_generator_batch.service.ChatGptService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.borders.SolidBorder;

import java.io.FileNotFoundException;

@Component
public class CvPdfWriter implements ItemWriter<Candidat> {

    private static final Logger log = LoggerFactory.getLogger(CvPdfWriter.class);

    @Autowired
    private final ChatGptService chatGptService;

    public CvPdfWriter(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @Override
    public void write(Chunk<? extends Candidat> items) throws Exception {
        for (Candidat candidat : items) {
            String filename = candidat.getNom() + "_" + candidat.getPrenom() + "_CV.pdf";
            try {
                generatePdf(candidat, filename);
                log.info("PDF généré avec succès pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom());
                Thread.sleep(500);
            } catch (Exception e) {
                log.error("Erreur lors de la génération du PDF pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom(), e);
            }
        }
    }

    public void generatePdf(Candidat candidat, String filename) {
        try {
            log.info("Début de la génération du PDF pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom());
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Définir les marges
            document.setMargins(50, 50, 50, 50);

            // Ajouter un titre
            Paragraph title = new Paragraph("CV - " + candidat.getPrenom() + " " + candidat.getNom())
                    .setFontSize(24)
                    .setBold()
                    .setFontColor(ColorConstants.BLUE);
            document.add(title);

            // Ajouter l'email
            Paragraph email = new Paragraph("Email: " + candidat.getEmail())
                    .setFontSize(12)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(email);

            // Ajouter un sous-titre pour les compétences
            Paragraph competencesTitle = new Paragraph("Compétences:")
                    .setFontSize(16)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK);
            document.add(competencesTitle);

            // Ajouter les compétences dans un tableau
            Table competencesTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
            for (String competence : candidat.getCompetences()) {
                Cell cell = new Cell().add(new Paragraph(competence))
                        .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                        .setTextAlignment(TextAlignment.LEFT)
                        .setPadding(10);
                competencesTable.addCell(cell);
            }
            document.add(competencesTable);

            // Générer un résumé automatique avec ChatGPT
            String prompt = "Génère un résumé professionnel pour un candidat avec les compétences suivantes : " +
                    String.join(", ", candidat.getCompetences());
            String resume = chatGptService.generateText(prompt);
            document.add(new Paragraph("Résumé :"));
            document.add(new Paragraph(resume));

            document.close();
            log.info("Fin de la génération du PDF pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom());
        } catch (FileNotFoundException e) {
            log.error("Fichier non trouvé lors de la génération du PDF pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom(), e);
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF pour le candidat: {} {}", candidat.getNom(), candidat.getPrenom(), e);
        }
    }
}
