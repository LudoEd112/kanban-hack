package com.kanban.hack;

//import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApplication.class, args);

		/*// Загружаем переменные из .env
		Dotenv dotenv = Dotenv.load();

		// Теперь можно получить переменные
		String dbHost = dotenv.get("DB_HOST");
		String dbUser = dotenv.get("DB_USER");
		String dbPass = dotenv.get("DB_PASS");
		String dbName = dotenv.get("DB_NAME");*/
	}

}
