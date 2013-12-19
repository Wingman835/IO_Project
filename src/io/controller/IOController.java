package io.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import io.model.Game;
import io.view.IOFrame;

/**
 * Controller class for the IO project.
 * @author nduc4538
 * @version 1.0 13/12/13 Added start method.
 */
public class IOController
{
	
	/**
	 * Reference to the GUI Frame for the project.
	 */
	private IOFrame appFrame;
	
	
	/**
	 * 
	 */
	public IOController()
	{
		
	}
	
	public void start()
	{
		appFrame = new IOFrame(this);
	}

	public void saveGameInformation(Game currentGame)
	{
		PrintWriter gameWriter;
		String saveFile = "save file.txt";
		
		try
		{
			gameWriter = new PrintWriter(saveFile); //Creates the save file.
			
			gameWriter.println(currentGame.getGameTitle());
			gameWriter.println(currentGame.getFunRanking());
			for(int count = 0; count < currentGame.getGameRules().size(); count++)
			{
				gameWriter.println(currentGame.getGameRules().get(count));
			}
			
			gameWriter.close(); //Required to prevent corruption of data and maintain security of the file.
		}
		catch(FileNotFoundException noFileExists)
		{
			JOptionPane.showMessageDialog(appFrame, "Could not create the save file. :(");
			JOptionPane.showMessageDialog(appFrame, noFileExists.getMessage());
		}
	}
	
	public Game readGameInformation()
	{
		String fileName = "save file.txt";
		File currentSaveFile = new File(fileName);
		Scanner fileReader;
		Game currentGame = null;
		int gameRanking = 0;
		String gameTitle = "";
		ArrayList<String> gameRules = new ArrayList<String>();
		
		try
		{
			fileReader = new Scanner(currentSaveFile);
			gameTitle = fileReader.nextLine();
			gameRanking = fileReader.nextInt();
			while(fileReader.hasNext())
			{
				gameRules.add(fileReader.nextLine());
			}
			
			currentGame = new Game(gameRules, gameRanking, gameTitle);
			fileReader.close();
		}
		catch(FileNotFoundException currentFileDoesNotExist)
		{
			JOptionPane.showMessageDialog(appFrame, currentFileDoesNotExist.getMessage());
		}
		
		return currentGame;
	}
	
	public Game makeGameFromInput(String gameTitle, String gameRanking, String gameRules)
	{
		Game currentGame = new Game();
		currentGame.setGameTitle(gameTitle);
		
		if (checkNumberFormat(gameRanking))
		{
			currentGame.setFunRanking(Integer.parseInt(gameRanking));
		}
		else
		{
			return null;
		}
		
		String[] temp = gameRules.split("\n");
		ArrayList<String> tempRules = new ArrayList<String>();
		
		for (String tempWord : temp)
		{
			tempRules.add(tempWord);
		}
		currentGame.setGameRules(tempRules);
		
		return currentGame;
	}
	
	private boolean checkNumberFormat(String toBeParsed)
	{
		boolean isNumber = false;
		
		try
		{
			int valid = Integer.parseInt(toBeParsed);
			isNumber = true;
		}
		catch (NumberFormatException error)
		{
			JOptionPane.showMessageDialog(appFrame, "Please try again with an actual number for the ranking.");
		}
		
		return isNumber;
	}
}
