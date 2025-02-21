package com.example.task.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.task.model.entities.SubTask;

public interface SubtaskRepository extends JpaRepository<SubTask,Long>{

	@Query(value ="SELECT st.* from subtask st where  st.task_id = ?1 and st.name like %?2%" ,nativeQuery = true)
	List<SubTask> findBySubtaskName(@Param("id") long id ,@Param("name") String name);
}
