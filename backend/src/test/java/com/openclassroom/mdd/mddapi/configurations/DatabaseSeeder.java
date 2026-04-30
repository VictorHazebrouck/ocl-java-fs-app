package com.openclassroom.mdd.mddapi.configurations;

import com.openclassroom.mdd.mddapi.entities.Topic;
import com.openclassroom.mdd.mddapi.repositories.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final TopicRepository topicRepository;

    @Override
    public void run(String... args) {
        Topic t1 = new Topic();
        t1.setName("Spring");
        t1.setDescription("Spring stuff...");

        Topic t2 = new Topic();
        t2.setName("Angular");
        t2.setDescription("Angular stuff...");

        topicRepository.save(t1);
        topicRepository.save(t2);
    }
}
