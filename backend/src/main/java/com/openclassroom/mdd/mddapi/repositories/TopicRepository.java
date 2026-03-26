package com.openclassroom.mdd.mddapi.repositories;

import com.openclassroom.mdd.mddapi.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {}
