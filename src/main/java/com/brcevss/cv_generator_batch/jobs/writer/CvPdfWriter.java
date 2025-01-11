package com.brcevss.cv_generator_batch.jobs.writer;

import com.brcevss.cv_generator_batch.model.Candidat;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

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

    @Override
    public void write(Chunk<? extends Candidat> items) throws Exception {
        for (Candidat candidat : items) {
            String filename = candidat.getNom() + "_" + candidat.getPrenom() + "_CV.pdf";
            generatePdf(candidat, filename);
        }
    }

/*    public static void generatePdf(Candidat candidat, String filename) {
        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("CV - " + candidat.getPrenom() + " " + candidat.getNom()));
            document.add(new Paragraph("Email: " + candidat.getEmail()));
            document.add(new Paragraph("Compétences:"));
            document.add(new Paragraph(String.join(", ", candidat.getCompetences())));

            // Ajoute d'autres informations selon ton besoin (expérience, formation, etc.)

            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static void generatePdf(Candidat candidat, String filename) {
        try {
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
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
