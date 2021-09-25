package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import storage.TodoMap;

@Configuration
public class TodoConfig {

    @Bean
    public TodoMap todoMap(){
        return new TodoMap();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
