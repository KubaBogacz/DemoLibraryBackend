package com.example.demo.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans", schema = "library")
public class LoanEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "loan_date")
    private LocalDate loanDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
}
