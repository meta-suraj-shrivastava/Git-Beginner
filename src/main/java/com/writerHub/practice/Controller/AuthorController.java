package com.writerHub.practice.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.Author;
import com.writerHub.practice.models.WriterHubUser;
import com.writerHub.practice.service.AuthorService;
import com.writerHub.practice.service.WriterHubUserService;

@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private WriterHubUserService writerHubUserService;

    @GetMapping("/authors")
    public ResponseEntity<?> getAllAuthors(){
        List<Author> authorList = authorService.getAllAuthors();
        return  ResponseEntity.ok().body(authorList);
    }

    @PostMapping("/author")
    public ResponseEntity<?> saveAuthor(HttpServletRequest request, @RequestBody Author author){
        AuthenticationError error;
        ResponseEntity<?> response;
        try {
            WriterHubUser user = writerHubUserService.getUser(request.getAttribute("username").toString());
            user.setAuthor(author);
            user = writerHubUserService.saveUser(user);
            response = new  ResponseEntity<>(user.getAuthor(),HttpStatus.CREATED);
        }
        catch (IllegalArgumentException exception){
            error = new AuthenticationError();
            error.setMessage(exception.getMessage());
            error.setStatusCode(HttpStatus.BAD_REQUEST);
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
        catch (Exception exception){
            error = new AuthenticationError();
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            error.setMessage(exception.getMessage());
            response = new ResponseEntity<>(error,error.getStatusCode());
        }
        return  response;
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable Long id){
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok().body(author);
    }

    @GetMapping("/author/{authorId}/companies")
    public ResponseEntity<?> getAuthorCompanies(@PathVariable Long id){
        Author author = authorService.getAuthor(id);
        return ResponseEntity.ok().body(author.getCompany());
    }
}
