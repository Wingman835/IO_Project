package io.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import io.controller.IOController;
import io.model.Game;

public class IOPanel extends JPanel
{
	private IOController baseController;
	private JButton saveButton;
	private JButton loadButton;
	private JTextField titleField;
	private JTextField rankingField;
	private JTextArea rulesArea;
	private JLabel rulesLabel;
	private JLabel rankingLabel;
	private JLabel titleLabel;
	private SpringLayout baseLayout;
	
	public IOPanel(IOController baseController)
	{
		this.baseController = baseController;
		
		saveButton = new JButton("Save the game.");
		loadButton = new JButton("Load the game.");
		
		titleLabel = new JLabel("Game Title: ");
		rankingLabel = new JLabel("Game Ranking: ");
		rulesLabel = new JLabel("Game Rules: ");
		
		titleField = new JTextField(15);
		rankingField = new JTextField(5);
		rulesArea = new JTextArea(5, 20);

		baseLayout = new SpringLayout();
		baseLayout.putConstraint(SpringLayout.NORTH, loadButton, 0, SpringLayout.NORTH, saveButton);
		baseLayout.putConstraint(SpringLayout.EAST, loadButton, 0, SpringLayout.EAST, rulesArea);
		baseLayout.putConstraint(SpringLayout.WEST, saveButton, 0, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.SOUTH, saveButton, -27, SpringLayout.SOUTH, this);
		
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupPanel()
	{
		this.setLayout(baseLayout);
		this.setBackground(new Color(30, 80, 160));
		add(rankingField);
		add(rankingLabel);
		add(rulesArea);
		add(rulesLabel);
		add(saveButton);
		add(loadButton);
		add(titleField);
		add(titleLabel);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, titleLabel, 38, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingLabel, 36, SpringLayout.SOUTH, titleLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rankingField, 30, SpringLayout.SOUTH, titleField);
		baseLayout.putConstraint(SpringLayout.NORTH, titleField, -3, SpringLayout.NORTH, titleLabel);
		baseLayout.putConstraint(SpringLayout.WEST, titleField, 0, SpringLayout.WEST, rankingField);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesArea, -5, SpringLayout.NORTH, rulesLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rulesArea, 0, SpringLayout.WEST, rankingField);
		baseLayout.putConstraint(SpringLayout.WEST, titleLabel, 0, SpringLayout.WEST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, rulesLabel, 30, SpringLayout.SOUTH, rankingLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rankingField, 6, SpringLayout.EAST, rankingLabel);
		baseLayout.putConstraint(SpringLayout.WEST, rankingLabel, 0, SpringLayout.WEST, rulesLabel);
		baseLayout.putConstraint(SpringLayout.EAST, rulesLabel, -298, SpringLayout.EAST, this);
	}
	
	private void setupListeners()
	{
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.makeGameFromInput(titleField.getText(), rankingField.getText(), rulesArea.getText());
				
				if (tempGame != null)
				{
					baseController.saveGameInformation(tempGame);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Try again with a valid number.");
				}
			}
		});
		
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				Game tempGame = baseController.readGameInformation();
				
				if (tempGame != null)
				{
					titleField.setText(tempGame.getGameTitle());
					rankingField.setText(Integer.toString(tempGame.getFunRanking()));
					
					String tempRules = "";
	
				for (String currentRule : tempGame.getGameRules())
					{
						tempRules += currentRule + "\n";
					}
					rulesArea.setText(tempRules);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Check the save file make sure it is in order.");
				}
			}
		});
		
	}

}
