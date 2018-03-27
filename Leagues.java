import java.util.*;
import java.io.*;
import javax.swing.*;
public class Leagues{
    private static final String[] mainMenu = { "Create League", "View and Edit", "Log Out" };
    private static final String[] subMenu = {"View Leagues", "View Teams", "View Leaderboard", "View Fixtures", "Add Results", "Back to Main Menu" };
    private static Scanner x;
    private static Scanner y;
    private static Scanner z;
    private static Scanner findAdminNum;
    private static int currentAdminNum;
    private static File accountInfo = new File ("userInfo.txt");
    private static File leagueInfo = new File ("leagueInfo.txt");
    public static ArrayList<ArrayList<String>>  teams;
    public static ArrayList<ArrayList<Integer>> fixtures;
	  public static ArrayList<ArrayList<Integer>> results;
	  public static int [][] leaderBoard;

public static void main(String[] args) throws IOException {
	   verifyLogin();
     boolean main = true;
     while (main) {
          boolean sub = true;
          String section = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, mainMenu, mainMenu[0]);
          if(section=="Create League") {
              createLeague();
          }else if(section=="View and Edit") {
            while (sub) {
                String subSection = (String) JOptionPane.showInputDialog(null, "Menu","",JOptionPane.QUESTION_MESSAGE, null, subMenu, subMenu[0]);
                if(subSection=="View Leaderboard"){
                    viewLeaderboard();
                }else if(subSection=="View Fixtures"){
                    viewFixtures();
                }else if(subSection=="Add Results") {
                    addResults();
                }else if(subSection =="View Teams") {
					          viewTeams();
				        }else if(subSection =="View Leagues") {
					          viewLeagues();
				        }
				        else  {
                    sub = false;
                }
            }
          }else
            main = false;
      }
      JOptionPane.showMessageDialog(null, "You have been logged out");
      System.exit(0);
	}

	public static int checkAmmountOfLeagues() throws IOException {
		int leagueCounter = 0;
		String lineFromFile;
		String fileElements[];
		Scanner leagueChecker = new Scanner(leagueInfo);
		while (leagueChecker.hasNext())
		{
		lineFromFile = leagueChecker.nextLine();
		fileElements = lineFromFile.split(",");
		leagueCounter =Integer.parseInt(fileElements[0]);
		}
    leagueChecker.close();
		return leagueCounter;
	}

	public static void createLeague() throws IOException {
		//JOptionPane, asks for League name and number of teams, automatically asigns league number based on how many already exist
		// JOptionPane, asks to input team names corresponding to number of teams
		leagueInfo.createNewFile();
		String leagueName;
		int leagueNumber = checkAmmountOfLeagues();
		int numberOfTeams;
		String adminNumber = "admin";
		String fileTeamNames = "";
		String teamNames = ""; // The string for listing teams, in line 57 the teams are added 1 by 1.
		FileWriter outputStream = new FileWriter(leagueInfo,true);
		FileWriter outputStream2 = new FileWriter((leagueNumber+1) + "_participants.txt",true);
		leagueNumber++;
		PrintWriter pw = new PrintWriter(outputStream);
		PrintWriter pw2 = new PrintWriter(outputStream2);
		leagueName = (String) JOptionPane.showInputDialog(null,"Please enter League Name","");
		numberOfTeams = Integer.parseInt(JOptionPane.showInputDialog(null,leagueName + "\n How many teams are to be in the league?"));
		String[] leagueTeamNames = new String[numberOfTeams];
		for (int i = 0; i < numberOfTeams;i++) {
		leagueTeamNames[i] = (String) (JOptionPane.showInputDialog(null,"("+ (i + 1) + ")" + " Enter team name"));
		  if (leagueTeamNames[i].matches(".*[^a-zA-Z].*")) { //Only Alphabetical letters
			     JOptionPane.showMessageDialog(null,"Team names may only contain letters!");
			     i--; //If input is wrong then then the loop doesn't go forward.
			     continue;
		  }
		teamNames += (i + 1) + ". " + leagueTeamNames[i] + "\n"; // Format for JOptionPane message
		fileTeamNames = (i + 1) + "," + leagueTeamNames[i]; // Actual format to be written to file
		pw2.println(fileTeamNames);
		}
		JOptionPane.showMessageDialog(null,teamNames); // List teams
		String LeagueDetails = leagueNumber + "," + leagueName + "," + currentAdminNum;
		pw.println(LeagueDetails);
		pw.close();
		pw2.close();
		generateFixtures();
  }

	public static void generateFixtures() throws IOException  {
    int numberOfTeams, totalNumberOfRounds, numberOfMatchesPerRound;
    int roundNumber, matchNumber, homeTeamNumber = 0, awayTeamNumber = 0, even, odd;
    boolean additionalTeamIncluded = false;
	  int leagueNumber = checkAmmountOfLeagues();
	  File f = new File(leagueNumber + "_fixtures.txt");
	  FileWriter fixt = new FileWriter(f,true);
  	PrintWriter pwFixt = new PrintWriter(fixt);
    int selection;
	  int matchCounter = 1;
    String [][] fixtures;
    String [][] revisedFixtures;
    String []   elementsOfFixture;
    String fixtureAsText;
    selection = getNumberOfTeams();
    if (selection != 0)
    {
       numberOfTeams = selection;
       if (numberOfTeams % 2 == 1) {
	        numberOfTeams++;
	        additionalTeamIncluded = true;
       }
	        totalNumberOfRounds     = numberOfTeams - 1;
          numberOfMatchesPerRound = numberOfTeams / 2;
          fixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
          for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++) {
            for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++) {
              homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
		          awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
              if (matchNumber == 0)
                awayTeamNumber = numberOfTeams - 1;
		            fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + "," + (awayTeamNumber + 1);

            }
          }
	        revisedFixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
          even = 0;
          odd = numberOfTeams / 2;
          for (int i = 0; i < fixtures.length; i++) {
            if (i % 2 == 0)
              revisedFixtures[i] = fixtures[even++];
            else
              revisedFixtures[i] = fixtures[odd++];
            }
            fixtures = revisedFixtures;
            for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++) {
              if (roundNumber % 2 == 1) {
	               fixtureAsText = fixtures[roundNumber][0];
	               elementsOfFixture = fixtureAsText.split(",");
                 fixtures[roundNumber][0] = elementsOfFixture[1] + "," + elementsOfFixture[0];
	            }
            }
	           for(int x = 0; x<fixtures.length; x++) {
			            for(int y = 0; y < fixtures[x].length; y++) {
                    pwFixt.println(matchCounter + "," + fixtures[x][y]);
			              matchCounter++;
			            }
             }
     }
		 if (selection != 0)
     {
       numberOfTeams = selection;
       if (numberOfTeams % 2 == 1)
       {
	        numberOfTeams++;
	        additionalTeamIncluded = true;
       }
	     totalNumberOfRounds     = numberOfTeams - 1;
       numberOfMatchesPerRound = numberOfTeams / 2;
       fixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
       for (roundNumber = 0; roundNumber < totalNumberOfRounds; roundNumber++)
       {
         for (matchNumber = 0; matchNumber < numberOfMatchesPerRound; matchNumber++)
	       {
           homeTeamNumber = (roundNumber + matchNumber) % (numberOfTeams - 1);
		       awayTeamNumber = (numberOfTeams - 1 - matchNumber + roundNumber) % (numberOfTeams - 1);
           if (matchNumber == 0)
           awayTeamNumber = numberOfTeams - 1;
		       fixtures[roundNumber][matchNumber] = (awayTeamNumber + 1) + "," + (homeTeamNumber + 1);
         }
       }
	     revisedFixtures = new String[totalNumberOfRounds][numberOfMatchesPerRound];
       even = 0;
       odd = numberOfTeams / 2;
       for (int i = 0; i < fixtures.length; i++)
       {
         if (i % 2 == 0)
           revisedFixtures[i] = fixtures[even++];
         else
           revisedFixtures[i] = fixtures[odd++];
       }
       fixtures = revisedFixtures;
       for (roundNumber = 0; roundNumber < fixtures.length; roundNumber++)
       {
         if (roundNumber % 2 == 1)
	       {
	          fixtureAsText = fixtures[roundNumber][0];
	          elementsOfFixture = fixtureAsText.split(",");
            fixtures[roundNumber][0] = elementsOfFixture[1] + "," + elementsOfFixture[0];
	       }
       }
		   for(int x = 0; x<fixtures.length; x++) {
			      for(int y = 0; y < fixtures[x].length; y++) {
			           pwFixt.println(matchCounter + "," + fixtures[x][y]);
			           matchCounter++;
			      }
		   }
	  }
    pwFixt.close();
  }


	public static int getNumberOfTeams() throws IOException {
    int numberOfnumberOfTeams = 0;
    Scanner in;
	  int leagueNumber = checkAmmountOfLeagues();
  	String lineFromFile;
  	String fileElements[];
  	File x = new File (leagueNumber + "_participants.txt");
  	in = new Scanner(x);
    while (in.hasNext())
    {
      lineFromFile = in.nextLine();
	    fileElements = lineFromFile.split(",");
		  numberOfnumberOfTeams = Integer.parseInt(fileElements[0]);
	  }
	  if (numberOfnumberOfTeams < 2) {
	    JOptionPane.showMessageDialog(null,"Error. Team number < 2", "Error. Team number < 2", 2);
		}
		in.close();
		return numberOfnumberOfTeams;
  }

  public static void viewLeaderboard()  throws IOException {
	  boolean readFile;
    readFile = readFilesIntoArrayLists();
    if (!readFile)
      System.out.println("One or more files do not exist.");
    else
    {
      createEmptyLeaderBoard();
      processResults();
      orderLeaderBoard();
      displayLeaderboard();
    }
  }

  public static boolean readFilesIntoArrayLists() throws IOException {
	   int	leagueNumber =Integer.parseInt(JOptionPane.showInputDialog(null, "Which league would you like to view?"));
     String fileElements[];
	   File inputFile1 = new File(leagueNumber + "_participants.txt");
	   File inputFile2 = new File(leagueNumber + "_fixtures.txt");
	   File inputFile3 = new File(leagueNumber + "_outcomes.txt");
	   teams = new ArrayList<ArrayList<String>>();
     teams.add(new ArrayList<String>());
     teams.add(new ArrayList<String>());
     fixtures = new ArrayList<ArrayList<Integer>>();
	   fixtures.add(new ArrayList<Integer>());
     fixtures.add(new ArrayList<Integer>());
     fixtures.add(new ArrayList<Integer>());
     results = new ArrayList<ArrayList<Integer>>();
	   results.add(new ArrayList<Integer>());
     results.add(new ArrayList<Integer>());
     results.add(new ArrayList<Integer>());
  	 if (inputFile1.exists() && inputFile2.exists() && inputFile3.exists()) {
	      Scanner in;
	      in = new Scanner(inputFile1);
	      while(in.hasNext())
	      {
	         fileElements = (in.nextLine()).split(",");
	         teams.get(0).add(fileElements[0]);
	         teams.get(1).add(fileElements[1]);
	      }
	      in.close();
	      in = new Scanner(inputFile2);
	      while(in.hasNext())
	      {
	         fileElements = (in.nextLine()).split(",");
	         fixtures.get(0).add(Integer.parseInt(fileElements[0]));
	         fixtures.get(1).add(Integer.parseInt(fileElements[1]));
	         fixtures.get(2).add(Integer.parseInt(fileElements[2]));
	      }
	      in.close();
	      in = new Scanner(inputFile3);
	      while(in.hasNext()) {
	         fileElements = (in.nextLine()).split(",");
	         results.get(0).add(Integer.parseInt(fileElements[0]));
	         results.get(1).add(Integer.parseInt(fileElements[1]));
	         results.get(2).add(Integer.parseInt(fileElements[2]));
	      }
	      in.close();
	      return true;
    }
    else
    return false;
  }

  public static void createEmptyLeaderBoard()
  {
	  // find out the number of teams/players which will determine
	  // the number of rows
    int rows = teams.get(0).size();
	  int columns = 14;
	  leaderBoard = new int[rows][columns];
	  // place team numbers in column 0 of leader board
  	for (int i = 0; i < leaderBoard.length; i++)
       leaderBoard[i][0] = Integer.parseInt(teams.get(0).get(i));
  }

  public static void processResults()
  {
	int fixtureNumber, homeTeamScore, awayTeamScore, homeTeamNumber, awayTeamNumber;
	int position;
	for (int i = 0; i < results.get(0).size(); i++)
    {
	  fixtureNumber  = results.get(0).get(i);
	  homeTeamScore  = results.get(1).get(i);
	  awayTeamScore  = results.get(2).get(i);
	  position       = fixtures.get(0).indexOf(fixtureNumber);
	  homeTeamNumber = fixtures.get(1).get(position);
	  awayTeamNumber = fixtures.get(2).get(position);
	  if (homeTeamScore == awayTeamScore)
	  {
		recordFixtureResultForHomeTeam(homeTeamNumber,0,1,0,homeTeamScore,awayTeamScore,1);
		recordFixtureResultForAwayTeam(awayTeamNumber,0,1,0,homeTeamScore,awayTeamScore,1);
	  }
	  else if (homeTeamScore > awayTeamScore)
	  {
		recordFixtureResultForHomeTeam(homeTeamNumber,1,0,0,homeTeamScore,awayTeamScore,3);
		recordFixtureResultForAwayTeam(awayTeamNumber,0,0,1,homeTeamScore,awayTeamScore,0);
	  }
	  else
	  {
		recordFixtureResultForHomeTeam(homeTeamNumber,0,0,1,homeTeamScore,awayTeamScore,0);
		recordFixtureResultForAwayTeam(awayTeamNumber,1,0,0,homeTeamScore,awayTeamScore,3);
	  }
    }
  }

  public static void recordFixtureResultForHomeTeam(int hTN, int w, int d, int l, int hTS, int aTS, int p)
  {
	   leaderBoard[hTN-1][1]++;        			// gamesPlayed
	   leaderBoard[hTN-1][2]+= w;      			// homeWin
	   leaderBoard[hTN-1][3]+= d;      			// homeDraw
	   leaderBoard[hTN-1][4]+= l;      			// homeLoss
	   leaderBoard[hTN-1][5]+= hTS;    			// homeTeamScore
	   leaderBoard[hTN-1][6]+= aTS;    			// awayTeamScore
	   leaderBoard[hTN-1][12] += (hTS - aTS);    	// goalDifference
	   leaderBoard[hTN-1][13] += p;    			// points
  }

  public static void recordFixtureResultForAwayTeam(int aTN, int w, int d, int l, int hTS, int aTS, int p)
  {
	   leaderBoard[aTN-1][1]++;        			// gamesPlayed
	   leaderBoard[aTN-1][7]+= w;      			// awayWin
	   leaderBoard[aTN-1][8]+= d;      			// awayDraw
	   leaderBoard[aTN-1][9]+= l;      			// awayLoss
	   leaderBoard[aTN-1][10]+= aTS;    			// awayTeamScore
	   leaderBoard[aTN-1][11]+= hTS;    			// homeTeamScore
	   leaderBoard[aTN-1][12] += (aTS - hTS);    	// goalDifference
	   leaderBoard[aTN-1][13] += p;    			// points
  }

  public static void orderLeaderBoard()
  {
	  int [][] temp = new int[leaderBoard.length][leaderBoard[0].length];
    boolean finished = false;
    while (!finished)
    {
      finished = true;
      for (int i = 0; i < leaderBoard.length - 1; i++)
      {
        if (leaderBoard[i][13] < leaderBoard[i + 1][13])
        {
          for (int j = 0; j < leaderBoard[i].length; j++)
          {
            temp[i][j]            = leaderBoard[i][j];
            leaderBoard[i][j]     = leaderBoard[i + 1][j];
            leaderBoard[i + 1][j] = temp[i][j];
          }
          finished = false;
        }
      }
    }
  }

  public static void displayLeaderboard()
  {
	  int aTeamNumber;
	  String aTeamName, formatStringTeamName;
	  String longestTeamName       = teams.get(1).get(0);
    int    longestTeamNameLength = longestTeamName.length();
    for (int i = 1; i < teams.get(1).size(); i++)
    {
	     longestTeamName = teams.get(1).get(i);
       if (longestTeamNameLength < longestTeamName.length())
          longestTeamNameLength = longestTeamName.length();
    }
    formatStringTeamName = "%-" + (longestTeamNameLength + 2) + "s";
    System.out.printf(formatStringTeamName,"Team Name");
    System.out.println("  GP  HW  HD  HL  GF  GA  AW  AD  AL  GF  GA   GD   TP");
    for (int i = 0; i < leaderBoard.length; i++)
    {
	     aTeamNumber       = leaderBoard[i][0];
	     aTeamName         = teams.get(1).get(aTeamNumber - 1);
       System.out.printf(formatStringTeamName, aTeamName);
       System.out.printf("%4d", leaderBoard[i][1]);
       System.out.printf("%4d", leaderBoard[i][2]);
       System.out.printf("%4d", leaderBoard[i][3]);
       System.out.printf("%4d", leaderBoard[i][4]);
       System.out.printf("%4d", leaderBoard[i][5]);
       System.out.printf("%4d", leaderBoard[i][6]);
       System.out.printf("%4d", leaderBoard[i][7]);
	     System.out.printf("%4d", leaderBoard[i][8]);
       System.out.printf("%4d", leaderBoard[i][9]);
       System.out.printf("%4d", leaderBoard[i][10]);
       System.out.printf("%4d", leaderBoard[i][11]);
       System.out.printf("%5d", leaderBoard[i][12]);
       System.out.printf("%5d", leaderBoard[i][13]);
       System.out.println();
    }
  }

	public static void viewLeagues() throws IOException {
		Scanner in;
		String lineFromFile;
		in = new Scanner(leagueInfo);
		while(in.hasNext())
		{
			lineFromFile = in.nextLine();
			System.out.println(lineFromFile);

		}
		in.close();
	}

	public static void viewTeams() throws IOException {
		String inputLeagueName = JOptionPane.showInputDialog(null,"Enter the name of the league you would like to view");
		int tempLeagueNum, tempAdminNum;
    String tempLeagueName;
    Scanner in;
		Scanner on;
		String lineFromFile;
		String lineFromFile2;
		String FileElements[];
    in = new Scanner(leagueInfo);
    boolean found = false;
    while(in.hasNext() && ! found) {
			lineFromFile =in.nextLine();
			FileElements = lineFromFile.split(",");
			tempLeagueNum =  Integer.parseInt(FileElements[0]);
			tempLeagueName = FileElements[1];
			tempAdminNum =   Integer.parseInt(FileElements[2]);
          if(inputLeagueName.trim().equals(tempLeagueName) && tempAdminNum == currentAdminNum) {
            found = true;
				    File y = new File(tempLeagueNum + "_participants.txt");
				    on = new Scanner(y);
				    while(on.hasNext()) {
              lineFromFile2 = on.nextLine();
					    System.out.println (lineFromFile2);
	          }
				    on.close();
		     }
		 }
     in.close();
   }

    public static void viewFixtures() throws IOException {
  		int inputLeagueNum = Integer.parseInt(JOptionPane.showInputDialog(null,"What number league would like to view the fixtures of?"));
  		int tempLeagueNum, tempAdminNum, homeTeamNum, awayTeamNum, fixtureNum;
      String tempLeagueName;
      Scanner in;
  		Scanner on;
      Scanner an;
  		String lineFromFile, lineFromFile2, homeTeamName, awayTeamName;
  		String fileElements[];
      String fileElements2[];
      ArrayList<String> allParicipantDetails = new ArrayList<String>();
      in = new Scanner(leagueInfo);
      boolean found = false;
      while(in.hasNext() && ! found) {
  			lineFromFile =in.nextLine();
  			fileElements = lineFromFile.split(",");
  			tempLeagueNum =  Integer.parseInt(fileElements[0]);
  			tempLeagueName = fileElements[1];
  			tempAdminNum =   Integer.parseInt(fileElements[2]);
            if(inputLeagueNum==tempLeagueNum && tempAdminNum == currentAdminNum) {
              found = true;
  				    File x = new File(tempLeagueNum + "_participants.txt");
              File y = new File(tempLeagueNum + "_fixtures.txt");
  				    on = new Scanner(y);
              an = new Scanner(x);
              while(an.hasNext()) {
                 lineFromFile2 = an.nextLine();
                 fileElements2 = lineFromFile2.split(",");
                 allParicipantDetails.add(fileElements2[1]);
               }
  				     while(on.hasNext()) {
                lineFromFile = on.nextLine();
                fileElements = lineFromFile.split(",");
          			fixtureNum =  Integer.parseInt(fileElements[0]);
          			homeTeamNum = Integer.parseInt(fileElements[1]);
          			awayTeamNum =   Integer.parseInt(fileElements[2]);
                homeTeamName=allParicipantDetails.get(homeTeamNum-1);
                awayTeamName=allParicipantDetails.get(awayTeamNum-1);
                System.out.println (fileElements[0] + ". " + homeTeamName + " v " + awayTeamName);
  	          }
                on.close();
                an.close();
  		     }
  		  }
        in.close();
     }

    public static void addResults() throws IOException {
      	int inputLeagueNum = Integer.parseInt(JOptionPane.showInputDialog(null,"What number league would like to add results to?"));
    		int tempLeagueNum, tempAdminNum, homeTeamNum, awayTeamNum, fixtureNum, printAwayScore, printHomeScore;
        int  currentFixtureNum = 1;
        Scanner in;
    		Scanner on;
        Scanner an;
        Scanner un;
    		String lineFromFile, lineFromFile2, homeTeamName, awayTeamName, tempLeagueName, fix;
    		String fileElements[];
        String fileElements2[];
        ArrayList<String> allParicipantDetails = new ArrayList<String>();
        in = new Scanner(leagueInfo);
        boolean found = false;
        while(in.hasNext() && ! found) {
    			lineFromFile =in.nextLine();
    			fileElements = lineFromFile.split(",");
    			tempLeagueNum =  Integer.parseInt(fileElements[0]);
    			tempLeagueName = fileElements[1];
    			tempAdminNum =   Integer.parseInt(fileElements[2]);
              if(inputLeagueNum==tempLeagueNum && tempAdminNum == currentAdminNum) {
                found = true;
    				    File x = new File(tempLeagueNum + "_participants.txt");
                File y = new File(tempLeagueNum + "_fixtures.txt");
                File z = new File(tempLeagueNum + "_outcomes.txt");
            	  FileWriter printed = new FileWriter(z,true);
                PrintWriter out = new PrintWriter(printed);
    				    on = new Scanner(y);
                an = new Scanner(x);
                un = new Scanner(z);
                while(an.hasNext()) {
                   lineFromFile2 = an.nextLine();
                   fileElements2 = lineFromFile2.split(",");
                   allParicipantDetails.add(fileElements2[1]);
                }
                while(un.hasNext()) {
                  un.nextLine();
                  currentFixtureNum++;
                }
                un.close();
    				    while(on.hasNext()) {
                  lineFromFile = on.nextLine();
                  fileElements = lineFromFile.split(",");
            			fixtureNum =  Integer.parseInt(fileElements[0]);
            			homeTeamNum = Integer.parseInt(fileElements[1]);
            			awayTeamNum =   Integer.parseInt(fileElements[2]);
                  homeTeamName=allParicipantDetails.get(homeTeamNum-1);
                  awayTeamName=allParicipantDetails.get(awayTeamNum-1);
                  fix = (fixtureNum + ". " + homeTeamName + " v " + awayTeamName);
                  if (fixtureNum==currentFixtureNum) {
                    printHomeScore = Integer.parseInt(JOptionPane.showInputDialog(null, fix + "\n Home Team Score:"));
                    printAwayScore = Integer.parseInt(JOptionPane.showInputDialog(null, fix + "\n Away Team Score:"));
                    out.println(fixtureNum + "," + printHomeScore + "," + printAwayScore);
                  }
    	          }
                  out.close();
                  on.close();
                  an.close();
    		     }
    		  }
          in.close();
    }

    public static void verifyLogin() throws IOException  {
	     String userName   = "",      userPassword   = "";
	     String newUser = "";
	     FileWriter fw = new FileWriter(accountInfo,true);
	     PrintWriter pw3 = new PrintWriter(fw);
	     ArrayList<String> userNamesAndPasswords = new ArrayList<String>();
	     ArrayList<String> allUserDetails = new ArrayList<String>();
	     Scanner in;
       String lineFromFile;
       String fileElements[];
       String message1 = "Please enter your username";
	     String message2 = "Please enter your password";
       String message3 = "Invalid input, please re-try";
       String message4 = "Invalid input, no more attempts";
       int chance = 1;
	     int createdAdminNumber = 0;
	     int selectedOption;
       int adminNumber, position;
       boolean validInput  = false, validAccessDetails = false;
	     String aUser = "", tempUserDetails;
	     if(accountInfo.length() == 0) {
		        userName = JOptionPane.showInputDialog("Please enter desired username");
		        userPassword = JOptionPane.showInputDialog("Please enter desired password");
		        adminNumber = 1;
	          newUser = (userName + "," + userPassword + "," + adminNumber);
	          pw3.println(newUser);
		        System.out.println("Login Created, relaunch");
		        pw3.close();
	          System.exit(0);
	     }
	     selectedOption = JOptionPane.showConfirmDialog(null,"Have you already created an account?","LOGIN",JOptionPane.YES_NO_OPTION);
	     if (selectedOption == JOptionPane.NO_OPTION) {
		       userName = JOptionPane.showInputDialog("Please enter desired username");
		       userPassword = JOptionPane.showInputDialog("Please enter desired password");
		       in = new Scanner(accountInfo);
	         while(in.hasNext())	{
			         lineFromFile = in.nextLine();
			         fileElements = lineFromFile.split(",");
			         createdAdminNumber = ((Integer.parseInt(fileElements[2])) + 1);
		       }
		   in.close();
		   newUser = (userName + "," + userPassword + "," + createdAdminNumber);
		   pw3.println(newUser);
       JOptionPane.showMessageDialog(null,"Login Created, relaunch");
       pw3.close();
       System.exit(0);
		   } else if (selectedOption == JOptionPane.YES_OPTION) {
	        in = new Scanner(accountInfo);
	        while(in.hasNext()) {
	           lineFromFile = in.nextLine();
             fileElements = lineFromFile.split(",");
             userNamesAndPasswords.add(fileElements[0] + "," + fileElements[1]);
             allUserDetails.add(lineFromFile);
	        }
	        in.close();
	     } else
	      System.out.println("User file not found");
        while((!(validInput)) && (chance <= 3)) {
	         userName = JOptionPane.showInputDialog(null, message1);
	         if (userName != null) {
	            userPassword = JOptionPane.showInputDialog(null, message2);
		            if (userPassword != null) {
		                aUser = userName + "," + userPassword;
		                  if (userNamesAndPasswords.contains(aUser))
		                  {
		                  validInput         = true;
			                validAccessDetails = true;
		                  } else {
  		                    chance += 1;
			                    if (chance <= 3)
			                       JOptionPane.showMessageDialog(null, message3);
			                    else {
                            JOptionPane.showMessageDialog(null, message4);
			                         System.exit(0);
                          }
		                  }
                } else
		               validInput = true;
	         } else
	          validInput = true;
        }
	      if (validAccessDetails) {
	         position        = userNamesAndPasswords.indexOf(aUser);
           tempUserDetails = allUserDetails.get(position);
	         fileElements    = tempUserDetails.split(",");
           adminNumber     = Integer.parseInt(fileElements[2]);
	         currentAdminNum = adminNumber;
	         System.out.println("Hello " + userName + currentAdminNum);
        } else
	         System.out.print("Goodbye");
	  }
}
