package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.dto.BookResponse;
import roomescape.service.BookService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("books")
public class ClientReservationController {
    private final BookService bookService;

    public ClientReservationController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{date}/{theme_id}")
    public ResponseEntity<List<BookResponse>> read(@PathVariable(value = "date") LocalDate date, @PathVariable(value = "theme_id") Long themeId) {
        List<BookResponse> books = bookService.findAvaliableBookList(date, themeId);
        return ResponseEntity.ok(books);
    }
}
