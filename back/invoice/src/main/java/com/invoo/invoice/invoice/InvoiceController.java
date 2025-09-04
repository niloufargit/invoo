package com.invoo.invoice.invoice;

import com.invoo.invoice.statistics.products.domaine.ProductsStatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
@Slf4j
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ProductsStatisticsService productsStatisticsService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest request) {
        productsStatisticsService.saveProductStatistics( request, request.getCompanyId() );
        return invoiceService.createInvoice(
                request.getInvoiceNumber(),
                request.getCompanyId(),
                request.getBeneficiary(),
                request.getInvoiceTitle(),
                request.getDeliveryDate(),
                request.getProducts(),
                request.getPrice()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        log.info("Start retrieve invoice with id:  {}", id);
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/record/{companyId}")
    public ResponseEntity<?> getCompanyInvoices(@PathVariable Long companyId) {
        log.info("Start retrieve catalog with company id:  {}", companyId);
        return ResponseEntity.ok(invoiceService.getAllInvoicesMetadataByCompany(companyId));
    }

    @GetMapping("/record/metadata/{companyId}")
    public ResponseEntity<?> getAllInvoicesMetadataByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(invoiceService.getAllInvoicesMetadataByCompany(companyId));
    }

    @GetMapping("/info-stripe")
    public ResponseEntity<?> getInvoiceInfo(@RequestParam Long id) {
        var rec = invoiceService.getInvoiceInfo(id);

        var map = Map.of(
                "name", rec.getInvoiceNumber(),
                "amount", rec.getTotalIncludingTax()
        );

        return ResponseEntity.ok(map);
    }

    @GetMapping("/download/{invoiceId}")
    public ResponseEntity<?> downloadInvoicePdf(@PathVariable Long invoiceId) {
        log.info("Downloading invoice PDF with id: {}", invoiceId);
        return invoiceService.downloadInvoicePdf(invoiceId);
    }
}