import java.util.*;
import java.io.*;
import javax.swing.*;
public class BookingSystem {

private static File accountInfo = new File ("userInfo.txt");
private static boolean Administrator = false;


public static void main (String [] args) throws IOException {
String userEmail = "", userPassword = "";
String adminEmail = "", adminPassword = "";
String newUser = "";
FileWriter fw = new FileWriter(accountInfo,true);
PrintWriter pw = new PrintWriter(fw);
ArrayList<String> emailsAndPasswords = new ArrayList<String>();
ArrayList<String> allUserDetails = new ArrayList<String>();
ArrayList<String> adminDetails = new ArrayList<String>();
Scanner in;
Scanner in2;
String lineFromFile, adminLineFromFile;
String fileElements[], adminFileElements[];

int Chance = 1;
int adminNumber, position;
String aUser = "", tempUserDetails;
boolean validInput  = false, validAccessDetails = false, validAdminAccessDetails = false;


if(accountInfo.length() == 0) 
{
	adminEmail = JOptionPane.showInputDialog("create ADMIN: enter email");
	adminPassword = JOptionPane.showInputDialog("create ADMIN: enter password");
	newUser =(adminEmail + "," + adminPassword);
	pw.println(newUser);
	System.out.println("Admin Created, relaunch");
	pw.close();
	System.exit(0);
	
}
 else if (accountInfo.length() != 0)
 {
	in2 = new Scanner(accountInfo);
	adminLineFromFile = in2.nextLine();
	adminDetails.add(adminLineFromFile);
	in2.close();
	
	in = new Scanner(accountInfo);
	 while(in.hasNext()) {
	 lineFromFile = in.nextLine();
     fileElements = lineFromFile.split(",");
     allUserDetails.add(lineFromFile);
	 }
	 in.close();
	}
	while((!(validInput)) && (Chance <= 3)) {
		userEmail = JOptionPane.showInputDialog(null,"please enter your email");
		if (userEmail != null)
			{
				userPassword = JOptionPane.showInputDialog(null, "please enter your password");
			
			if (userPassword != null) 
			{
				aUser = userEmail + "," + userPassword;
			
				if (adminDetails.contains(aUser))
				{ 
					validInput		= true;
					validAdminAccessDetails = true;
					
				if (allUserDetails.contains(aUser))
				{
					validInput = true;
					validAccessDetails = true;
				}
				
				else 
				{	
				Chance += 1;
						if (Chance <= 3)
							JOptionPane.showMessageDialog(null,"Invalid input, please re-try");
					
						else {
							JOptionPane.showMessageDialog(null,"Too many invalid inputs");
							System.exit(0);
						}
					}
				}
			} else  validInput = true;	
		} else validInput = true;
	}
	if (validAdminAccessDetails)
	{ 
		position        = emailsAndPasswords.indexOf(aUser);
		tempUserDetails = allUserDetails.get(position);
		Administrator = true;
		System.out.println("Logged in as Admin");
	}
		else if(validAccessDetails)
		{ 
			position        = emailsAndPasswords.indexOf(aUser);
			tempUserDetails = allUserDetails.get(position);
			System.out.println("Logged in user + userEmail");
		}
		else {
			System.out.println("Goodbye");
		}
		}
}
