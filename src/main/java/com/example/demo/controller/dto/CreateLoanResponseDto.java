package com.example.demo.controller.dto;

import java.time.LocalDate;

public class CreateLoanResponseDto {
    private long loanId;
    private long bookId;
    private LocalDate loanDate;

    public CreateLoanResponseDto() {
    }

    public CreateLoanResponseDto(long loanId, long bookId, LocalDate loanDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.loanDate = loanDate;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
}