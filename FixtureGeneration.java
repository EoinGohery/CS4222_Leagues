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
		   fixtures[roundNumber][matchNumber] = (homeTeamNumber + 1) + "," + (awayTeamNumber + 1);
		
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
			for(int y = 0; y < fixtures[x].length; y++) 
			{
			
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
			for(int y = 0; y < fixtures[x].length; y++) 
			{
			
			pwFixt.println(matchCounter + "," + fixtures[x][y]);
			matchCounter++;
			}
		}
	  }	
		
	
		pwFixt.close();
 }
