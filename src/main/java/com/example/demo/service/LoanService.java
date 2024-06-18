package com.example.demo.service;

import com.example.demo.controller.dto.CreateLoanDto;
import com.example.demo.controller.dto.CreateLoanResponseDto;
import com.example.demo.infrastructure.entity.LoanEntity;
import com.example.demo.infrastructure.repository.LoanRepository;
import com.example.demo.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public List<CreateLoanResponseDto> getAll() {
        var loans = loanRepository.findAll();
        return loans.stream().map((loan) -> new CreateLoanResponseDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getLoanDate()
        )).toList();
    }

    public CreateLoanResponseDto getOne(long id) {
        var loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        return new CreateLoanResponseDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getLoanDate()
        );
    }

    public CreateLoanResponseDto create(CreateLoanDto loanDto) {
        var book = bookRepository.findById(loanDto.getId()).orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies for loan");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        var loanEntity = new LoanEntity();
        loanEntity.setBook(book);
        loanEntity.setLoanDate(LocalDate.parse(loanDto.getLoanDate())); // Assuming the date is in a valid format

        var newLoan = loanRepository.save(loanEntity);

        return new CreateLoanResponseDto(
                newLoan.getId(),
                newLoan.getBook().getId(),
                newLoan.getLoanDate()
        );
    }

    public void delete(long id) {
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Loan with such id has not been found");
        }
        var loan = loanRepository.findById(id).orElseThrow();
        var book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        loanRepository.deleteById(id);
    }
}
