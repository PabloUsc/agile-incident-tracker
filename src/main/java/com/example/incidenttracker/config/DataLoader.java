package com.example.incidenttracker.config;

import com.example.incidenttracker.model.Project;
import com.example.incidenttracker.model.User;
import com.example.incidenttracker.repository.ProjectRepository;
import com.example.incidenttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        //Create user if no users in userRepository
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .username("suziru")
                    .email("irumasuz@test.com")
                    .fullName("Suzuki Iruma")
                    .build();
            userRepository.save(user);
        }

        //Create project if no projects in projectRepository
        if (projectRepository.count() == 0) {
            Project project = Project.builder()
                    .name("Mairimashita School Platform")
                    .projectKey("MAIRU")
                    .description("School handling platform for personnel")
                    .build();
            projectRepository.save(project);
        }
    }
}
