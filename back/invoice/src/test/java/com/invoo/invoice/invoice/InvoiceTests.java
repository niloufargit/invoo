package com.invoo.invoice.invoice;

import com.invoo.invoice.address.Address;
import com.invoo.invoice.bank_account.BankAccount;
import com.invoo.invoice.beneficiaryDTO.Beneficiary;
import com.invoo.invoice.company.Company;
import com.invoo.invoice.company.CompanyService;
import com.invoo.invoice.company.InMemoryCompany;
import com.invoo.invoice.payment.Payment;
import com.invoo.invoice.price.Price;
import com.invoo.invoice.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.invoo.invoice.company.CompanyType.COMPANY;

public class InvoiceTests {
    InMemoryInvoice storage;
    InMemoryInvoiceRecord storageRecord;
    InMemoryCompany companyStorage;
    ProviderDate   providerDate   ;
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    public InvoiceTests() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
    }

    private InvoiceService invoiceService;

    /***
     * Write HTML content to a file just for test
     */
    void writeHtmlToFile(String html, String filePath) {
        try {
            File file = new File(filePath);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(html);
            }
            System.out.println("HTML file generated: " + file.getAbsolutePath());
        } catch (IOException e) {
            Assertions.fail("IOException occurred: " + e.getMessage());
        }
    }

    @BeforeEach
    void setUp(){
        storage = new InMemoryInvoice();
        storageRecord = new InMemoryInvoiceRecord();
        companyStorage = new InMemoryCompany();
        providerDate   = new ProviderDate();
        CompanyService companyService = new CompanyService(companyStorage);
        invoiceService = new InvoiceService(storage, templateEngine, companyService, providerDate, storageRecord);
        providerDate.date = LocalDateTime.of(2024, 3, 1, 1, 1);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createInvoice() {
        Address customerAddress = new Address(1L, "2", "Rue de la gare", "Galluis", "78490", "France");
        BankAccount customerBankAccount = new BankAccount(1L,"000001", "BNPPFRXX", "BNP PARIBAS");
        Company supplierCompany = new Company(1L, "test", "00002", customerAddress, "0671911639", "test2@gmail.com", UUID.randomUUID(), COMPANY);
        companyStorage.save(supplierCompany);
        Beneficiary beneficiary = new Beneficiary( UUID.randomUUID(), "test", "test", "test@gmail.com", 100.0, customerAddress, customerBankAccount, "000001");
        Payment paymentCustomer = new Payment(LocalDateTime.of(2024, 2, 1,1,1), "BankAccount", "01", 10.0, "eur", "Pening", "", "Ok", 0, 0);
        Price productPrice = new Price(8.0, 0, "20%", 10.0, 0);
        Product testProducts = new Product("testProduct", "test", 1, productPrice);
        List<Product> products = new ArrayList<>();
        products.add(testProducts);

        Price InvooPrice = new Price(8.0, 0, "20%", 10.0, 0);
        invoiceService.createInvoice("01", 1L, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1,1,1), products, InvooPrice);
        Invoice invoice = storage.invoices.get(0);
        String expectedHtmlContent = invoiceService.generateInvoiceHtml(new InvoiceDTO(null, "01", supplierCompany, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1,1,1), products, InvooPrice, providerDate));
        Assertions.assertEquals(expectedHtmlContent, invoice.getHtmlContent());
    }

    @Test
    void generateInvoicePdf() {
        Address customerAddress = new Address(2L,"2","Rue de la gare", "Galluis", "78490", "France");
        BankAccount customerBankAccount = new BankAccount(1L,"000001", "BNPPFRXX", "BNP PARIBAS");
        Company supplierCompany = new Company(1L, "test", "00002", customerAddress, "0671911639", "test2@gmail.com", UUID.randomUUID(), COMPANY);
        companyStorage.save(supplierCompany);
        Beneficiary beneficiary = new Beneficiary(UUID.randomUUID(), "test", "test", "test@gmail.com", 100.0, customerAddress, customerBankAccount, "000001");
        Payment paymentCustomer = new Payment(LocalDateTime.of(2024, 2, 1,1,1), "BankAccount", "01", 10.0, "eur", "Pening", "", "Ok", 0, 0);
        Price productPrice = new Price(8.0, 0, "20%", 10.0, 0);
        Product testProducts = new Product("testProduct", "test", 1, productPrice);
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            products.add(testProducts);
        }
        Price InvooPrice = new Price(8.0, 0, "20%", 10.0, 0);
        InvoiceDTO invoiceDTO = new InvoiceDTO(null, "01", supplierCompany, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1,1,1), products, InvooPrice, providerDate);

        String pdfFilePath = "src/main/resources/temp/01.pdf";
        Invoice invoice = new Invoice(1L, invoiceService.generateInvoiceHtml(invoiceDTO));
        invoiceService.generateInvoicePdf(invoice);

        File pdfFile = new File(pdfFilePath);
        Assertions.assertTrue(pdfFile.exists());
    }

    @Test
    void getInvoiceById() {
        Address customerAddress = new Address(2L, "2", "Rue de la gare", "Galluis", "78490", "France");
        BankAccount customerBankAccount = new BankAccount(1L, "000001", "BNPPFRXX", "BNP PARIBAS");
        Company supplierCompany = new Company(1L, "test", "00002", customerAddress, "0671911639", "test2@gmail.com", UUID.randomUUID(), COMPANY);
        companyStorage.save(supplierCompany);
        Beneficiary beneficiary = new Beneficiary(UUID.randomUUID(), "test", "test", "test@gmail.com", 100.0, customerAddress, customerBankAccount, "000001");
        Payment paymentCustomer = new Payment(LocalDateTime.of(2024, 2, 1, 1, 1), "BankAccount", "01", 10.0, "eur", "Pending", "", "Ok", 0, 0);
        Price productPrice = new Price(8.0, 0, "20%", 10.0, 0);
        Product testProducts = new Product("testProduct", "test", 1, productPrice);
        List<Product> products = new ArrayList<>();
        products.add(testProducts);
        Price invooPrice = new Price(8.0, 0, "20%", 10.0, 0);
        invoiceService.createInvoice("01", 1L, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice);
        String expectedHtmlContent = invoiceService.generateInvoiceHtml(new InvoiceDTO(null, "01", supplierCompany, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice, providerDate));
        Assertions.assertEquals(expectedHtmlContent, invoiceService.getInvoiceById(1L).getHtmlContent());
    }

    @Test
    void editInvoice() {
        Address customerAddress = new Address(2L, "2", "Rue de la gare", "Galluis", "78490", "France");
        BankAccount customerBankAccount = new BankAccount(1L, "000001", "BNPPFRXX", "BNP PARIBAS");
        Company supplierCompany = new Company(1L, "test", "00002", customerAddress, "0671911639", "test2@gmail.com", UUID.randomUUID(), COMPANY);
        companyStorage.save(supplierCompany);
        Beneficiary beneficiary = new Beneficiary(UUID.randomUUID(), "test", "test", "test@gmail.com", 100.0, customerAddress, customerBankAccount, "000001");
        Payment paymentCustomer = new Payment(LocalDateTime.of(2024, 2, 1, 1, 1), "BankAccount", "01", 10.0, "eur", "Pending", "", "Ok", 0, 0);
        Price productPrice = new Price(8.0, 0, "20%", 10.0, 0);
        Product testProducts = new Product("testProduct", "test", 1, productPrice);
        List<Product> products = new ArrayList<>();
        products.add(testProducts);
        Price invooPrice = new Price(8.0, 0, "20%", 10.0, 0);

        invoiceService.createInvoice("01", 1L, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice);
        invoiceService.editInvoice(1L, "02", 1L, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice);

        Invoice invoice = storage.invoices.get(0);
        String expectedHtmlContent = invoiceService.generateInvoiceHtml(new InvoiceDTO(1L, "02", supplierCompany, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice, providerDate));

        Assertions.assertEquals(expectedHtmlContent, invoice.getHtmlContent());
    }

    @Test
    void deleteInvoice() {
        Address customerAddress = new Address(2L, "2", "Rue de la gare", "Galluis", "78490", "France");
        BankAccount customerBankAccount = new BankAccount(1L, "000001", "BNPPFRXX", "BNP PARIBAS");
        Company supplierCompany = new Company(1L, "test", "00002", customerAddress, "0671911639", "test2@gmail.com", UUID.randomUUID(), COMPANY);
        companyStorage.save(supplierCompany);
        Beneficiary beneficiary = new Beneficiary(UUID.randomUUID(), "test", "test", "test@gmail.com", 100.0, customerAddress, customerBankAccount, "000001");
        Payment paymentCustomer = new Payment(LocalDateTime.of(2024, 2, 1, 1, 1), "BankAccount", "01", 10.0, "eur", "Pending", "", "Ok", 0, 0);
        Price productPrice = new Price(8.0, 0, "20%", 10.0, 0);
        Product testProducts = new Product("testProduct", "test", 1, productPrice);
        List<Product> products = new ArrayList<>();
        products.add(testProducts);
        Price invooPrice = new Price(8.0, 0, "20%", 10.0, 0);

        invoiceService.createInvoice("01", 1L, beneficiary, paymentCustomer, "First InvoiceDTO", LocalDateTime.of(2024, 3, 1, 1, 1), products, invooPrice);
        invoiceService.deleteInvoice(1L);

        Assertions.assertTrue(storage.invoices.isEmpty());
    }
}