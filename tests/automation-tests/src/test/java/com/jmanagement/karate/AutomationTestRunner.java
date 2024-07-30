package com.jmanagement.karate;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class AutomationTestRunner {

    @Test
    public void testParallelBooking() {
        Results results = Runner.path("classpath:*")
                .outputCucumberJson(true)
                .karateEnv(System.getProperty("karate.env"))
                .parallel(5);

        generateReport(results.getReportDir());
        System.out.println(results.getFailCount());
    }

    public static void generateReport(String karateOutputPath) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "Microservices");
        ReportBuilder reportBuilder =  new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
        generatePDF();
    }

    private static void generatePDF() {
        System.out.println("Generating Pdf");
        try {
            final String BASE_DIR = "target/cucumber-html-reports/";
            final String SRC = BASE_DIR + "overview-tags.html";
            final String DEST = BASE_DIR + "Overview.pdf";

            ConverterProperties properties = new ConverterProperties();
            properties.setBaseUri(BASE_DIR);

            PdfWriter pdfWriter = null;
            pdfWriter = new PdfWriter(DEST);

            PdfDocument pdf = new PdfDocument(pdfWriter);
            pdf.setDefaultPageSize(PageSize.A4.rotate());
            pdf.setTagged();
            HtmlConverter.convertToPdf(new FileInputStream(SRC), pdf, properties);

            PdfReader reader = new PdfReader(DEST);
            pdfWriter = new PdfWriter(BASE_DIR + "Test_Summary.pdf");
            PdfDocument pdfDoc = new PdfDocument(reader, pdfWriter);
            for (int page = 1; page <=pdfDoc.getNumberOfPages(); ++page) {
                PdfPage pdfPage = pdfDoc.getPage(page);
                pdfPage.getPdfObject().remove(PdfName.Annots);
            }
            pdfDoc.close();

        } catch (Exception e) {
            System.out.println("Error generating PDF");
            System.out.println(e);
        }
    }
}
