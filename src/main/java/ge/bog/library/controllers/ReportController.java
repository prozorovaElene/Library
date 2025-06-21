package ge.bog.library.controllers;

import ge.bog.library.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate_reservations")
    public String generateReport() {
        return reportService.reservationReport();
    }

    @GetMapping("/generate_admin_books")
    public String generateAdminBooks() {
        return reportService.adminReport();
    }
}
