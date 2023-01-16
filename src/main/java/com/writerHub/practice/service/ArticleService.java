package com.writerHub.practice.service;

import com.writerHub.practice.models.Article;
import com.writerHub.practice.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> getAllArticles(){
        return  articleRepository.findAll();
    }

    public Article saveArticle(Article article){
        return articleRepository.save(article);
    }

    public Article getArticle(Long id){
        Optional<Article> article = articleRepository.findById(id);
        if(article != null)
            return article.get();
        else
            return  null;
    }

    public List<Article> getArticleByAuthorId(Long id){
        return articleRepository.findByAuthorId(id);
    }

    public List<Article> getArticleByCompany(Long id){
        return articleRepository.findByCompanyId(id);
    }

    public void deleteArticle(Long id){
        articleRepository.deleteById(id);
    }

}
