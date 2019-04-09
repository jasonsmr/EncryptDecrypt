/**********************************************************************
 UnMix.java holds algorithm that powers the decrypt proportion
 of the program.
 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;

import com.example.encryptdecrypt.DoubleLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UnMix {
	/******************************************************************
	 * UnMix empty default constructor method for the UnMix Class
	 *
	 ******************************************************************/
	public UnMix() {}
	/******************************************************************
	 * processCommand Method does most of the work with a switch case
	 * sorting the imputs from the read file passed as an argument.
	 *
	 * @param userMessage String passed that is current user message
	 * @param command String passed from scan file read in
	 * @return String value of userMessage
	 ******************************************************************/
	public String processCommand(String userMessage, String command) {
		try {

			Mix mix = new Mix(true);
			mix.userMessage = userMessage;
			mix.userUndo = "";
			mix.populateMessage();
			String first = null;
			String[] data = command.split("/");
			//only required a few commands that the randomize command runs.
			if("DRbr".indexOf(command.charAt(0)) != - 1)
				first = command.charAt(0)+"";
			else
				throw new RuntimeException("Cannot have invalid command must be QDRbrpcxzh");
			String line = data[1];
			String two = null;
			switch (first) {
				//deletree case
				case "D":
					System.out.println(line);
					mix.delete(line);
					break;
				//Remove case
				case "R":
					try {
						two = data[2];
						if (line != null && two != null) {
							mix.remove(Integer.parseInt(line), Integer.parseInt(two));
						}
					} catch (NumberFormatException e) {
						System.out.println("Input cannot be non Integer!!");
					}
					break;
				//add before case
				case "b":
					try {
						two = data[2];
						mix.insertbefore(line, Integer.parseInt(two));
					} catch (RuntimeException e) {
						System.out.println("First or second line incorrect!!");
					}
					break;
				//replace with case
				case "r":
					two = data[2];
					if (line.length() != 1 || two.length() != 1) {
						System.out.println("Input cannot be longer than one character!!");
					} else {
						mix.replace(line, two);
					}
					break;
			}
			userMessage = mix.message.toString();

		} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			e.printStackTrace();
			System.exit(0);
		}
		return userMessage;
	}
	/******************************************************************
	 * unMixture Method just prints userMessage final to the
	 * command terminal.
	 *
	 * @param filename String passed as name of file
	 * @param userMessage String passed that is current user message
	 * @return void returns nothing
	 ******************************************************************/
	public void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		System.out.println ("The Original message was: " + original);
	}

	/******************************************************************
	 * UnMixUsingFile Method reroutes the userMessage final unMixture
	 *
	 * @param filename String passed as name of file
	 * @param userMessage String passed that is current user message
	 * @return String returns when new user message is processed
	 ******************************************************************/
	public String UnMixUsingFile(String filename, String userMessage) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scanner.hasNext()) {
			String command = scanner.nextLine();
			//System.out.println("Command: "+command);
			//commands.push(command);
			userMessage = processCommand(userMessage, command);
		}
		//userMessage = processCommand(userMessage, command);
		return userMessage;
	}
}
