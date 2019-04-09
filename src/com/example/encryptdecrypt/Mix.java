/**********************************************************************
 Mix.java holds algorithm that powers the encrypt proportion
 of the program.
 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;
public class Mix {

	/** store value of finalStep */
	Boolean finalStep = null;

	/** store value of message */
	public DoubleLinkedList<Character> message;

	/** store value of undoCommands */
	private ArrayList<String> undoCommands;

	/** store value of commands */
	private ArrayList<String> commands;

	/** store value of clipBoards */
	private clipBdLinkedList clipBoards;

	/** store value of a valid set of random characters */
	static final String AB =
		"0123456789" +
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
		"abcdefghijklmnopqrstuvwxyz";

	/** store value of a new SecureRandom generator */
	static SecureRandom rnd = new SecureRandom();

	/** store value of userMessage */
	public String userMessage;

	/** store value of userUndo */
	public String userUndo;

	/** store value of the system scan from user input */
	private Scanner scan;

	/** store value of stepNumber */
	private int stepNum = 0;

	/** store value of saveNumber */
	private int saveNum = 0;

	/** store value of ERROR initialized to false */
	private boolean ERROR = false;

	/******************************************************************
	 * Mix simple default constructor sets all values to default.
	 *
	 * @return nothing
	 ******************************************************************/
	public Mix(boolean finalStep) {
		this.finalStep = finalStep;
		scan = new Scanner(System.in).useDelimiter("(/)|\\n");
		message = new DoubleLinkedList<Character>();
		clipBoards = new clipBdLinkedList();
		undoCommands = new ArrayList<String>();
		commands = new ArrayList<String>();
		copy(0, message.getLen()-1, 9999);
	}

	/******************************************************************
	 * populateMessage helper method cycles simple loop to populate
	 * the userMessage
	 *
	 * @return nothing
	 ******************************************************************/
	public void populateMessage(){
		for(int i = 0; i < userMessage.length(); i++){
			message.append(userMessage.charAt(i));
		}
	}

	/******************************************************************
	 * mixture method, does most of the work of the Mix class, it is
	 * responsible for sorting through all input for switch cases and
	 * running commands.
	 *
	 * @return nothing
	 ******************************************************************/
	public void mixture() {
		do {
			DisplayMessage();
			System.out.print("Command: ");
			//initialized the first backup copy of the message
			copy(0, message.getLen()-1, saveNum);
			try {
				//patterns usable commands
				String command = scan.next("[QDRbrpcxzh]");
				System.out.println(command);
				String line = scan.useDelimiter("/|\\n").next();
				String two = null;
				String three = null;

				switch (command) {
					//pattern command for Quit option
					case "Q":
						System.out.println(line);
						save(line);
						System.out.println (
						"Final mixed up message: \"" + message+"\"");
						System.exit(0);
					//pattern command for Delete option
					case "D":
						System.out.println(line);
						delete(line);
						break;
					//pattern command for Remove option
					case "R":
						try {
							System.out.println(line);
							two = scan.useDelimiter("/|\\n").next();
							System.out.println(two);
							if(line != null && two != null)
								remove(Integer.parseInt(line),
										Integer.parseInt(two));
						}catch(NumberFormatException e)
						{System.out.println(
								"Input cannot be non Integer!!");
							//helpPage();
						}
						break;
					//pattern command for insertBefore option
					case "b":
						try{
							two = scan.useDelimiter("/|\\n").next();
							insertbefore(line, Integer.parseInt(two));
						}catch(RuntimeException e){System.out.println(
								"First or second line incorrect!!");
							//helpPage();
						}
						break;
					//pattern command for replace option
					case "r":
						System.out.println(line);
						two = scan.useDelimiter("/|\\n").next();
						System.out.println(two);
						if(line.length() != 1 || two.length() != 1){
							System.out.println(
							"Input longer than one character!!");
							//helpPage();
						}else {replace(line, two);}
						break;
					//pattern command for copy option
					case "c":
						two = null;
						three = null;
						try {
							System.out.println(line);
							two = scan.useDelimiter("/|\\n").next();
							System.out.println(two);
							three = scan.useDelimiter("/|\\n").next();
							System.out.println(three);
							copy(Integer.parseInt(line),
									Integer.parseInt(two),
									Integer.parseInt(three));
						}catch(NumberFormatException e)
						{System.out.println
								("Input Invalid!!");helpPage();}
						break;
					//pattern command for cut option
					case "x":
						try {
							System.out.println(line);
							two = scan.useDelimiter("/|\\n").next();
							System.out.println(two);
							three = scan.useDelimiter("/|\\n").next();
							System.out.println(three);
							cut(Integer.parseInt(line),
									Integer.parseInt(two),
									Integer.parseInt(three));
						}catch(NumberFormatException e)
						{System.out.println
								("Input Invalid!!");helpPage();}
						break;
					//pattern command for paste option
					case "p":
						try{
							System.out.println(line);
							two = scan.useDelimiter("/|\\n").next();
							System.out.println(two);
							paste(Integer.parseInt(line),
									Integer.parseInt(two));
						}catch(NumberFormatException e)
						{System.out.println
						("Input Invalid!!");helpPage();}
						break;
					//pattern command for Random option
					case "z":
						try{
							System.out.println(line);
							Integer test = null;
							test = Integer.parseInt(line);
							if(test == null)
								test = 50;
							randomize(test);
						}catch(NumberFormatException e)
						{System.out.println
						("Input Invalid!!");helpPage();}
						break;
					//pattern command for Help option
					case "h":
						helpPage();
						break;
				}
				scan.nextLine();   // should flush the buffer
			}
			catch (Throwable e ) {
				ERROR = true;
				System.out.println (
						"Error on input, previous state restored.");
				if(e.getClass().equals(InputMismatchException.class)) {
					//e.printStackTrace();
					System.out.println("Invalid Input!!\n\n");
					message.clear();
					System.out.println("SaveNumber "+saveNum);
					pasteDeCrypt(0, saveNum);
					scan = new Scanner
					(System.in).useDelimiter("(/)|\\n");
					helpPage();
				}
				else {
					e.printStackTrace();
					// should completely flush the buffer
					scan = new Scanner
					(System.in).useDelimiter("(/)|\\n");
					pasteDeCrypt(0, saveNum);
				}
			}finally {
				if(!ERROR) {
					copy(0, message.getLen() - 1, saveNum);
				}
				DisplayMessage();
				DisplayUndo();
			}

		} while (true);
	}

	/******************************************************************
	 * randomize another switch case command filter to create using
	 * the Secure Random Generator
	 *
	 * @param numbTimes Integer possible to pass void defaults to 10
	 * @return nothing
	 ******************************************************************/
	private void randomize(Integer numbTimes){
		String curMessage = null;
		String manipulate = null;
		String command = null;
		String tickets = null;
		String easy = null;
		int num;
		// filter my commands
		String pattern = "DRbr";
		if(numbTimes == null)
			numbTimes = 10;
		for (int i = 0; i < numbTimes; i++){
			try {
				command = pattern.charAt(
				new Random().nextInt(pattern.length())) + "";
				curMessage = message.toString();
				switch(command) {
					//pattern command for Delete option
					case "D":
						if(curMessage.length() > 10) {
							System.out.println(
							"Now using option: '"+command+"'.");
							manipulate = curMessage.charAt(
							new Random().nextInt(
							curMessage.length())) + "";
							System.out.println(
							"Deleting char: "+manipulate);
							delete(manipulate);
						}
						break;
					//pattern command for Remove option
					case "R":
						if(curMessage.length() > 10) {
							int num1 = new Random().
							nextInt(curMessage.length());
							int num2 = new Random().
							nextInt(curMessage.length());
							if (num1 > num2 && num1-num2 < 5) {
								System.out.println(
								"Now using option: '" +command+"'.");
								System.out.println(
								"Removing from:" + num2 +
										" To:" + num1);
								remove(num2, num1);
							} else if(num2 > num1 &&
								num2-num1 < 5){
								System.out.println(
								"Removing from:" + num1 +
								" To:" + num2);
								remove(num1, num2);
							}
						}
						break;
					//pattern command for insertBefore option
					case "b":
						tickets = randomString(8);
						//System.out.println(
						//"Current temp message: "+curMessage);
						if(curMessage.length() >= 0 &&
							curMessage.length() < 15) {
							if(curMessage.length() == 0) {
							System.out.println(
							"Now using option: '"+command+
							"'."+"With val: '"+tickets+"'.");
							System.out.println(
							"Inserting to empty message at index 0.");
								insertbefore(tickets, 0);
							}
							else {
								num = new Random().
								nextInt(curMessage.length());
								insertbefore(tickets, num);
							}
						}
						break;
					//pattern command for Random option
					case "r":
						if(curMessage.length() > 0) {
							manipulate = curMessage.charAt(
							new Random().
							nextInt(
							curMessage.length())) + "";
							tickets = randomString(1);
							if(!manipulate.equals(tickets)) {
							System.out.println(
							"Now using option: '"+
							command+"'."+"With val: '"+
							curMessage.length()+"'.");
								System.out.println("Replacing: "+
										manipulate+" with "+tickets);
								replace(manipulate, tickets);
							}
						}
						break;
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(
				"Error thrown in random generator option '"+
				command+"'.");
				throw e;
			}
		}
	}

	/******************************************************************
	 * randomString simple method returns String value for a randomly
	 * generated string of available characters.
	 *
	 * @param len int type passed for length of return string
	 * @return String of randomly generated characters
	 ******************************************************************/
	private String randomString( int len ){
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ )
			sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}

	/******************************************************************
	 * remove simple method that runs a loop looking for characters
	 * to remove from message
	 *
	 * @param start int type for where to start removing
	 * @param stop int type for where to stop removing
	 * @return nothing
	 ******************************************************************/
	public void remove(int start, int stop) {
		if(start <= stop &&
				stop <= message.getLen()-1 && start >= 0 ) {
			copy(start, stop, 1000);
			DoubleLinkedList<Character> temp = clipBoards.get(1000);
			int i = start;
			while (i <= stop) {
				message.removeAt(start);
				i++;
			}
			String com = "R/" + start + "/" + stop + "/";
			commands.add(stepNum, com);
			userUndo = "b/" + temp.printReverse() + "/" + start + "/";
			System.out.println(
					"StepNum:" + stepNum + "\nUserUndo:" + userUndo);
			undoCommands.add(stepNum, userUndo);
			stepNum++;
		}
	}

	/******************************************************************
	 * replace simple method loops through and replaces characters
	 * in the message
	 *
	 * @param data String type for what char to replace
	 * @param replace String type for what char to replace with
	 * @return nothing
	 ******************************************************************/
	public void replace(String data, String replace){
		//shift this to end of undo
		String[] tempShiftData = null;
		Integer[] tempShiftIndex = null;
		boolean check0 = true;
		boolean check = true;
		if(data.length() < 1 || replace.length() < 1 ) {
			if (stepNum == 0) {
				int i = 0;
				while (check0) {
					if (message.getIndex(data.charAt(0)) != null) {
						tempShiftIndex[i] = message.getIndex(data.charAt(0));
						tempShiftData[i] = message.get(tempShiftIndex[i]) + "";
						check0 = true;
						i++;
					} else check = false;

				}
			}
			char d = data.charAt(0);
			char r = replace.charAt(0);
			while (check) {
				Integer charAt = message.getIndex(d);
				if (charAt != null && message.replace(d, r))
					check = true;
				else
					check = false;
			}
			String com = "r/" + data + "/" + replace + "/";
			commands.add(stepNum, com);
			userUndo = "r/" + replace + "/" + data + "/";
			undoCommands.add(stepNum, userUndo);
			stepNum++;
			//finally check if I replaced anything that shouldnt have been
			if (this.finalStep) {

			}
		}else System.out.println(
				"replace character at cannot be multiple Chars");
	}

	/******************************************************************
	 * cut simple method used to target a specific part to cut
	 * from message to clipboard
	 *
	 * @param start int type start cut from index
	 * @param stop stop cutting from index
	 * @param clipNum int where to put the cut into clipboard
	 * @return nothing
	 ******************************************************************/
	public void cut(int start, int stop, int clipNum) {
		DoubleLinkedList<Character> temp = new DoubleLinkedList<Character>();
		for(int i = 0; i <= stop-start; i++) {
			temp.push(message.get(i));
		}
		clipBoards.push(clipNum, temp);
		int i = start;
		while(i <= stop){
			message.removeAt(start);
			i++;
		}
		String com = "c/"+start+"/"+stop+"/"+clipNum+"/";
		commands.add(stepNum, com);
		userUndo = "b/"+temp.printReverse()+"/"+start+"/";
		undoCommands.add(stepNum, userUndo);
		stepNum++;
	}

	/******************************************************************
	 * copy simple method copys part of message to clipboard
	 *
	 * @param start int where to start copy index
	 * @param stop int where to stop copy index
	 * @param clipNum int where to put copy data in clipBoard
	 *
	 * @return nothing
	 ******************************************************************/
	public void copy(int start, int stop, int clipNum) {
		DoubleLinkedList<Character> temp =
				new DoubleLinkedList<Character>();
		for(int i = 0; i <= stop-start; i++) {
			temp.push(message.get(i));
		}
		clipBoards.push(clipNum, temp);
	}

	/******************************************************************
	 * paste simple method loops through clipBoard for clip to paste
	 * to message
	 *
	 * @param index int where to paste in message
	 * @param clipNum where to look for clip
	 * @return nothing
	 ******************************************************************/
	public void paste( int index, int clipNum) {
		DoubleLinkedList<Character> temp = new DoubleLinkedList<Character>();
		int i = 0;
		while(clipBoards.get(clipNum) != null &&
				i < clipBoards.get(clipNum).getLen()) {
			//System.out.print(clipBoards.get(clipNum).get(i));
			message.insertBefore
					(clipBoards.get(clipNum).get(i), index);
			i++;
		}
		if(clipBoards.get(clipNum) == null)
			throw new NumberFormatException();
		String com = "p/"+index+"/"+clipNum+"/";
		commands.add(stepNum, com);
		userUndo = "R/"+index+"/"+
				(index+clipBoards.get(clipNum).getLen()-1)+"/";
		undoCommands.add(stepNum, userUndo);
		stepNum++;
	}

	/******************************************************************
	 * pasteDeCrypt simple method loops through clipBoard
	 * for clip to paste to message but only used for restoring
	 * default states
	 *
	 * @param index int where to paste in message
	 * @param clipNum where to look for clip
	 * @return nothing
	 ******************************************************************/
	public void pasteDeCrypt( int index, int clipNum) {
		DoubleLinkedList<Character> temp = new DoubleLinkedList<Character>();
		for(int i = 0; i < clipBoards.get(clipNum).getLen(); i++) {
			message.insertBefore(clipBoards.get
					(clipNum).get(i), index);
		}
	}

	/******************************************************************
	 * delete simple method that loops through message and deletes char
	 *
	 * @param s String type passed for delete to search for it
	 * @return nothing
	 ******************************************************************/
	public void delete(String s) {
		if(s.length() != 1) {
			System.out.println("delete must be single character");
			return;
		}
		Integer charAt = null;
		char c = s.charAt(0);
		boolean check = true;
		while(check) {
			charAt = message.getIndex(c);
			if(charAt != null) {
				check = true;
				System.out.println("Charater was: "+
						c+"\nFound At: "+charAt);
				System.out.println(message.toString());
				System.out.println("Deleted char: "+
						c+" Status: "+message.delete(c));
			}
			else
				check = false;
			// final check if everything is good to commit
			if(check) {
				String com = "D/"+s+"/";
				commands.add(stepNum, com);
				userUndo = "b/" + s + "/" + charAt+"/";
				undoCommands.add(stepNum, userUndo);
				stepNum++;
			}

		}
	}

	/******************************************************************
	 * insertbefore simple method that loops through message for insert
	 * a new string before an index.
	 *
	 * @param token String type for the new insertion token string
	 * @param index int type for the index to insert at
	 * @return nothing
	 ******************************************************************/
	public void insertbefore(String token, int index) {
		for(int i = 0; i < token.length(); i++) {
			message.insertBefore(token.charAt(i), index+i);
		}
		String com = "b/"+token+"/"+index+"/";
		commands.add(stepNum, com);
		userUndo = "R/" + (index) + "/" + (index+token.length()-1)+"/";
		undoCommands.add(stepNum, userUndo);
		stepNum++;
	}

	/******************************************************************
	 * DisplayMessage simple method loops through message printing
	 * every char human readable to terminal
	 *
	 * @return nothing
	 ******************************************************************/
	private void DisplayMessage() {
		System.out.print ("Message:\n");
		userMessage = message.toString();
		for (int i = 0; i < userMessage.length(); i++)
			System.out.format ("%3d", i);
		System.out.format ("\n");
		for (char c : userMessage.toCharArray())
			System.out.format("%3c",c);
		System.out.format ("\n");
	}

	/******************************************************************
	 * DisplayUndo simple method loops through undoMessages and prints
	 * to terminal
	 *
	 * @return nothing
	 ******************************************************************/
	private void DisplayUndo(){
		for(int i = 0; i < undoCommands.size(); i++){
			System.out.println(undoCommands.get(i));
		}
	}

	/******************************************************************
	 * save simple method that loops through the undoMessage
	 * and the command arrays and writes them to a file line by line.
	 *
	 * @param filename String type for filename
	 * @return nothing
	 ******************************************************************/
	public void save(String filename) {
		if(filename.equals("") || filename == null)
			filename = "filename.txt";
		PrintWriter outUndo = null;
		PrintWriter outCommand = null;
		try {
			outUndo = new PrintWriter(new BufferedWriter
					(new FileWriter(filename)));
			outCommand = new PrintWriter(new BufferedWriter
					(new FileWriter("commands.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < commands.size(); i++) {
			outCommand.println(commands.get(i));
		}
		for(int i = undoCommands.size()-1; i >= 0; i--) {
			outUndo.println(undoCommands.get(i));
		}
		outUndo.close();
		outCommand.close();
	}

	/******************************************************************
	 * helpPage simple method that displays the usage for each command.
	 *
	 * @return nothing
	 ******************************************************************/
	private void helpPage() {
		System.out.println("\nProgram Usage Note: Remember to " +
				"place a / (forward slash)\n" +
				"Before, after, and between all command " +
				"switches." );
		System.out.println("\nCommands:");
		System.out.println("Quit (Q)\n\tUsage: [Q /filename/]" +
				"\n\tMeans, quit! & save to filename" );
		System.out.println("Delete (D)\n\tUsage: [D /c/]\n\t" +
				"Deletes all instances of c; where c is a single" +
				" character to delete." );
		System.out.println("Remove (R)\n\tUsage: [R /#1/#2/]\n\t" +
				"Removes all characters in message between " +
				"index #1 & index #2." );
		System.out.println("Insert Before (b)\n\tUsage: [b " +
				"/MESSAGE/#index/]\n\tWhere MESSAGE is an " +
				"additional message to append before " +
				"some #index." );
		System.out.println("Replace (r)\n\tUsage: [r /C1/C2/]\n\t" +
				"Replaces all instances of character C1 with " +
				"another character C2." );
		System.out.println("Copy (c)\n\tUsage: [c /#1/#2/]\n\t" +
				"Copies all characters starting at index #1 " +
				"and ending at index #2, to clipboard." );
		System.out.println("Cut (c)\n\tUsage: [x /#1/#2/]\n\t" +
				"Cuts all characters starting at index #1 " +
				"and ending at index #2, to clipboard." );
		System.out.println("Paste (p)\n\tUsage: [p /#index/#clip/]" +
				"\n\tPastes string to current message at index #1 " +
				"from clipboard at clip-number #2." );

		System.out.println("Randomize it! (z)\n\tUsage: " +
				"[z /multiple/]\n\tRuns a series of D, R, b, " +
				"and r commands; where multiple (50) default." );
		System.out.println("Help (h)\n\tUsage: [h /]\n\t" +
				"Displays this help page.");
	}
}
