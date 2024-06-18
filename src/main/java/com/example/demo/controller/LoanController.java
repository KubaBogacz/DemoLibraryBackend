package com.example.demo.controller;

import com.example.demo.controller.dto.CreateLoanDto;
import com.example.demo.controller.dto.CreateLoanResponseDto;
import com.example.demo.controller.dto.GetBookDto;
import com.example.demo.service.BookService;
import com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;

    @Autowired
    public LoanController(LoanService loanService, BookService bookService) {
        this.loanService = loanService;
        this.bookService = bookService;

    }

    @GetMapping
    public ResponseEntity<List<CreateLoanResponseDto>> getAll() {
        var loans = loanService.getAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateLoanResponseDto> getOne(@PathVariable long id) {
        try {
            var loan = loanService.getOne(id);
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<CreateLoanResponseDto> createLoan(@PathVariable("id") long bookId, @RequestParam("loanDate") String loanDate) {
        try {
            GetBookDto bookDto = bookService.getOne(bookId);
            if (bookDto == null || !bookDto.isAvailable()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Create CreateLoanDto from GetBookDto and loanDate
            CreateLoanDto createLoanDto = new CreateLoanDto();
            createLoanDto.setId(bookDto.getId());
            createLoanDto.setIsbn(bookDto.getIsbn());
            createLoanDto.setTitle(bookDto.getTitle());
            createLoanDto.setAuthor(bookDto.getAuthor());
            createLoanDto.setPublisher(bookDto.getPublisher());
            createLoanDto.setLoanDate(loanDate); // Set loanDate from request parameter

            var newLoan = loanService.create(createLoanDto);
            return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            loanService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}