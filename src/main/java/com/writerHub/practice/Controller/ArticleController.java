package com.writerHub.practice.Controller;

import java.util.List;

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

import com.writerHub.practice.models.Article;
import com.writerHub.practice.models.AuthenticationError;
import com.writerHub.practice.models.Author;
import com.writerHub.practice.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles(){
        List<Article> articleList = articleService.getAllArticles();
        return  ResponseEntity.ok().body(articleList);
    }

    @GetMapping("/author/{authorId}/articles")
    public ResponseEntity<?> getAllArticlesByAuthor(@PathVariable Long authorId){
        List<Article> articleList = articleService.getArticleByAuthorId(authorId);
        return  ResponseEntity.ok().body(articleList);
    }

    @PostMapping("/author/{authorId}/article")
    public ResponseEntity<?> saveArticle(@RequestBody Article article,@PathVariable Long authorId){
        AuthenticationError error = new AuthenticationError();
        try {
            Author author = new Author(authorId);
            article.setAuthor(author);
            Article savedArticle = articleService.saveArticle(article);
            return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
        }
        catch (Exception exception){
            error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            error.setMessage(exception.getMessage());
        }
        return new ResponseEntity<>(error,error.getStatusCode());
    }

    @DeleteMapping("author/{authorId}/article/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id,@PathVariable Long authorId){
        Article article = articleService.getArticle(id);
        if(article != null && article.getAuthor().getId() == authorId){
            articleService.deleteArticle(id);
            return ResponseEntity.ok().build();
        }
        else{
            AuthenticationError error = new AuthenticationError();
            error.setStatusCode(HttpStatus.UNAUTHORIZED);
            error.setMessage("This article is not belongs to you.");
            return new ResponseEntity<>(error,error.getStatusCode());
        }
    }

    @GetMapping("article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id){
        Article article = articleService.getArticle(id);
        return ResponseEntity.ok().body(article);
    }

}
