package ge.bog.library.schedulers;

import ge.bog.library.services.BookRefreshService;
import ge.bog.library.services.BookService;
import ge.bog.library.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyScheduler {

    @Autowired
    private ReportService reportService;

    @Autowired
    private BookRefreshService bookRefreshService;

//    @Scheduled(cron = "0 59 23 * * *")
    @Scheduled(fixedRate = 5000)
    public void generateReservationReport() {
        reportService.reservationReport();
    }

    @Scheduled(fixedRate = 5000)
    public void refreshAllBooks() {
        bookRefreshService.refreshAllBooks();
    }
}
