package com.example.bookstore.service.impl;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.exeption.EntityNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.mapper.BookMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(book)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find Book by id: " + id)));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setCoverImage(bookRequestDto.getCoverImage());

        return bookMapper.toDto(bookRepository.save(book));
    }
}
