package com.NewsTok.Admin.Repositories;

import com.NewsTok.Admin.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NewsRepository extends JpaRepository<News,Integer> {
    public List<News> findById(Long Id);
    public List<News> findByTitleAndLink(String title, String link);

}
