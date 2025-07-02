package at.bif.swen.tourplanner.service;

import at.bif.swen.tourplanner.model.TourItem;
import at.bif.swen.tourplanner.model.TourLog;
import at.bif.swen.tourplanner.repository.TourItemRepository;
import at.bif.swen.tourplanner.repository.TourLogRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private TourLogRepository tourLogRepository;

    @Autowired
    private TourItemRepository tourItemRepository;

    public void generateTourReport(TourItem tourItem, String fileName) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Paragraph header = new Paragraph("Tour Report");
        document.add(header);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Description: " + tourItem.getDescription()));
        document.add(new Paragraph("From: " + tourItem.getFrom()));
        document.add(new Paragraph("To: " + tourItem.getTo()));
        document.add(new Paragraph("Transport-Type: " + tourItem.getTransportType()));
        document.add(new Paragraph("\n"));

        Paragraph logsHeader = new Paragraph("Tour Logs");
        document.add(logsHeader);

        Table table = new Table(6).useAllAvailableWidth();

        table.addHeaderCell(new Cell().add(new Paragraph("Tour ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tour Comment")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tour Time")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tour Duration")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tour Difficulty")));
        table.addHeaderCell(new Cell().add(new Paragraph("Tour Rating")));

        for (TourLog log : tourLogRepository.findAll()) {
            table.addCell(String.valueOf(log.getId()));
            table.addCell(log.getComment());
            table.addCell(log.getDatetime().toString());
            table.addCell(String.valueOf(log.getDuration()));
            table.addCell(String.valueOf(log.getDifficulty()));
            table.addCell(String.valueOf(log.getRating()));
        }
        document.add(table);
        document.close();
    }

    public void generateSummaryReport(String fileName) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(fileName));
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Paragraph header = new Paragraph("Tour Summary");
        document.add(header);

        Table table = new Table(3).useAllAvailableWidth();

        table.addHeaderCell(new Cell().add(new Paragraph("Tour ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Avg Duration")));
        table.addHeaderCell(new Cell().add(new Paragraph("Avg Rating")));

        List<TourLog> tourLogs = tourLogRepository.findAll();

        for (TourItem tourItem : tourItemRepository.findAll()) {
            List<TourLog> logs = tourLogs.stream().filter(l -> l.getRoute().getId() == tourItem.getId()).toList();

            double avgDuration = logs.stream().mapToInt(TourLog::getDuration).average().orElse(0);
            double avgRating = logs.stream().mapToInt(TourLog::getRating).average().orElse(0);

            table.addCell(String.valueOf(tourItem.getId()));
            table.addCell(String.valueOf(avgDuration));
            table.addCell(String.valueOf(avgRating));
        }
        document.add(table);
        document.close();
    }
}