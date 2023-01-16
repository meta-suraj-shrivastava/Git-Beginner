package com.writerHub.practice.repo;

import com.writerHub.practice.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long> {
    public List<Article> findByAuthorId(Long id);
    public List<Article> findByCompanyId(Long id);

}
