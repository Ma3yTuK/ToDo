package com.todo.todo_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@SpringBootApplication
public class TodoBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackApplication.class, args);
	}

}
