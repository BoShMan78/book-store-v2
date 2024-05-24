package mate.academy.bookstorev2.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstorev2.dto.BookDto;
import mate.academy.bookstorev2.dto.CreateBookRequestDto;
import mate.academy.bookstorev2.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/{id}")
        public BookDto getBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
}
