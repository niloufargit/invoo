package com.invoo.invoice.invoice;

import com.invoo.invoice.beneficiaryDTO.Beneficiary;
import com.invoo.invoice.company.Company;
import com.invoo.invoice.company.CompanyService;
import com.invoo.invoice.invoice.provider.IProviderDate;
import com.invoo.invoice.invoice.storage.IInvoiceRecordStorage;
import com.invoo.invoice.invoice.storage.IInvoiceStorage;
import com.invoo.invoice.payment.Payment;
import com.invoo.invoice.price.Price;
import com.invoo.invoice.product.Product;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class InvoiceService {

    private final IInvoiceStorage invoiceStorage;
    private final SpringTemplateEngine templateEngine;
    private final CompanyService companyService;
    private final IProviderDate providerDate;
    private final IInvoiceRecordStorage invoiceRecordStorage;

    public InvoiceService(IInvoiceStorage invoiceStorage, SpringTemplateEngine templateEngine, CompanyService companyService, IProviderDate providerDate, IInvoiceRecordStorage invoiceRecordStorage) {
        this.invoiceStorage = invoiceStorage;
        this.templateEngine = templateEngine;
        this.companyService = companyService;
        this.providerDate = providerDate;
        this.invoiceRecordStorage = invoiceRecordStorage;
    }

    public Invoice getInvoiceById(Long id) {
        Invoice invoice = invoiceStorage.findById(id);
        if (invoice == null) {
            throw new RuntimeException("Invoice not found");
        }
        return invoice;
    }

    public InvoiceRecord getInvoiceByCompanyId(Long id) {
        InvoiceRecord invoiceRecord = invoiceRecordStorage.findInvoiceByCompanyId(id);
        log.info("InvoiceRecord found: {}", invoiceRecord);
        if (invoiceRecord == null) {
            throw new RuntimeException("Invoice not found");
        }
        return invoiceRecord;
    }

    public ResponseEntity<?> createInvoice(String invoiceNumber, Long companyId, Beneficiary beneficiary, String invoiceTitle, LocalDateTime deliveryDate, List<Product> products, Price price) {
        Company company =  companyService.getCompanyById(companyId);
        InvoiceDTO invoiceDTO = new InvoiceDTO(null, invoiceNumber, company, beneficiary, invoiceTitle, deliveryDate, products, price, providerDate);
        System.out.println("Creating invoice with DTO: " + invoiceDTO);
        var invoice = Invoice.create(generateInvoiceHtml(invoiceDTO));
        var inv = invoiceStorage.save(invoice);

        var invoiceRecord = new InvoiceRecord();
        invoiceRecord.setInvoiceId(inv.getId());
        invoiceRecord.setInvoiceNumber(invoiceNumber);
        invoiceRecord.setSupplierCompanyId(companyId);
        invoiceRecord.setCustomerId(beneficiary.getId());
        invoiceRecord.setInvoiceTitle( invoiceTitle );
        invoiceRecord.setTotalIncludingTax( price.getTotalIncludingTax() );
        invoiceRecord.setTotalExcludingTax( price.getTotalExcludingTax() );
        invoiceRecordStorage.save( invoiceRecord );
        generateInvoicePdf(invoice);

        return ResponseEntity.ok(invoiceRecord);
    }

    public String generateInvoiceHtml(InvoiceDTO invoiceDTO) {
        Context context = new Context();
        context.setVariable("invoiceDTO", invoiceDTO);
        return templateEngine.process("template-pdf", context);
    }

    public void generateInvoicePdf(Invoice invoice) {

        String html = invoice.getHtmlContent();
        try (FileOutputStream outputStream = new FileOutputStream("invoice/src/main/resources/temp/" + invoice.getId() + ".pdf")) {
            HtmlConverter.convertToPdf(html, outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    public ResponseEntity<?> downloadInvoicePdf(Long invoiceId) {
        File file = new File("invoice/src/main/resources/temp/" + invoiceId + ".pdf");

        if (!file.exists()) {
            throw new RuntimeException("PDF file not found for invoice ID: " + invoiceId);
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(file.length())
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the PDF file", e);
        }
    }

    public void editInvoice(Long id, String invoiceNumber, Long companyId, Beneficiary beneficiary, Payment payment, String invoiceTitle, LocalDateTime deliveryDate, List<Product> products, Price price) {
        Company company = companyService.getCompanyById(companyId);
        InvoiceDTO invoiceDTO = new InvoiceDTO(id, invoiceNumber, company, beneficiary, invoiceTitle, deliveryDate, products, price, providerDate);
        invoiceStorage.edit(id, generateInvoiceHtml(invoiceDTO));
    }

    public void deleteInvoice(Long id) {
        invoiceStorage.delete(id);
    }

    public InvoiceRecord getInvoiceInfo(Long id) {
        return invoiceRecordStorage.findByInvoiceId(id);
    }

    public List<InvoiceRecord> getAllInvoicesMetadataByCompany(Long companyId) {
        return invoiceRecordStorage.findAllByCompanyId(companyId);
    }
}