package com.NewsTok.Admin.Repositories;

import com.NewsTok.Admin.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {

}
