package backend.repository;

import backend.domain.entity.Results;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultsRepository extends JpaRepository<Results, Integer> {
    List<Results> findAllByUser_Id(Integer userId);
    void deleteResultsByUser_Id(Integer userId);
}