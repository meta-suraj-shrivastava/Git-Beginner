package com.writerHub.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.writerHub.practice.models.Author;
import com.writerHub.practice.repo.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public List<Author> getAllAuthors(){
        return  authorRepository.findAll();
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author getAuthor(Long id){
        Optional<Author> author = authorRepository.findById(id);
        if(author != null)
            return author.get();
        else
            return  null;
    }
    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
    }

}
