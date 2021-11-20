package com.skilldistillery.filmquery.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		startUserInterface(input);
		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("Hello, Clarice...welcome to the Film Query App");
		menuFunctions(input);
	}

	private void menuFunctions(Scanner input) {
		boolean appRunning = true;
		
		while (appRunning) {
			printMenu();
			int userChoice = input.nextInt();
			input.nextLine();

			switch (userChoice) {
			case 1:
				findFilmById(input);
				break;
			case 2:
				findFilmsByKeyword(input);
				break;
			case 3:
				appRunning = false;
				System.out.println("\nHasta la vista, baby");
				break;
			default:
				System.err.println("Wrong sir, wrong!");
				System.err.println("Not a valid choice, try again");
			}
		}
	}

	private void printMenu() {
		System.out.println("\n=======================================");
		System.out.println("Please select from the following menu:");
		System.out.println("\n 1. Look up a film by its id.");
		System.out.println(" 2. Look up a film by a search keyword.");
		System.out.println(" 3. Exit the application.				");
		System.out.println("=======================================");
	}

	private Film findFilmById(Scanner input) {
		Film result = null;
		System.out.println("\nEnter the ID of your select film");
		int filmID = input.nextInt();
		input.nextLine();

		if (filmID < 1 || filmID > 1000) {
			System.err.println("\nFilm ID " + filmID + " not found.");
		} else {
			result = db.findFilmById(filmID);
			printFilmDetails(result);
		}
		return result;
	}

	private void printFilmDetails(Film film) {
		System.out.println("\nFilm details: ");
		System.out.println("\tFilm ID: " + film.getId());
		System.out.println("\tTitle: " + film.getTitle());
		System.out.println("\tYear: " + film.getReleaseYear());
		System.out.println("\tRating: " + film.getRating());
		System.out.println("\tDescription: " + film.getDescription());
		System.out.print("\tLanguage: ");
		db.printFilmLanguage(film.getId());
		System.out.print("\tActors: ");
		db.printActorsInFilm(film.getId());
	}

	private List<Film> findFilmsByKeyword(Scanner input) {
		List<Film> films = new ArrayList<>();
		Film result = null;
		System.out.println("\nEnter your keyword:");
		String keyword = input.nextLine();

		films = db.findFilmsByKeyword(keyword);

		if (films.size() == 0) {
			System.out.println("\nNo film contains " + keyword + " in the title or description.");
		} else {
			System.out.println("\nFilms containing " + keyword + " in the title or description: ");
			printFilmList(films);
		}
		return films;
	}

	private void printFilmList(List<Film> films) {
		int count = 0;
		for (Film film : films) {
			printFilmDetails(film);
			count++;
		}
		System.out.println("\nYour search returned " + count + " results listed above.");
	}
	


}
