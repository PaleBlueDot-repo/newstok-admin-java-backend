package com.NewsTok.Admin.Repositories;



import com.NewsTok.Admin.Models.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels,Long> {

    List<Reels> findByNewsId(Long newsId);

}
