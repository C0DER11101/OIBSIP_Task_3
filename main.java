// ATM interface

import java.util.*;      // for Scanner, Random, etc....
import java.util.concurrent.TimeUnit;    // for sleep()
import java.io.IOException;       // for handling exception thrown by File class
import java.lang.System;      // for exit()
import java.io.File; // for file handling
import java.io.FileWriter;
import java.io.FileNotFoundException;

// classes for date time formatting!
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

interface ATM {
	String userId="usr9090";
	Integer userPin=9087;
}

class credentials implements ATM{ 
	;
}

class History implements ATM {

	static void fetch(){
		// fetch the history of money transfers, withdrawals, etc.... from some files
		File withdrawHist=new File("Withdraw.txt");


		File transferHist=new File("Transfer.txt");

		File depositHist=new File("Deposit.txt");

		if(withdrawHist.exists()) {
			if(withdrawHist.length()==0) {
				System.out.println("No withdrawals made.....");
			}

			else {
				// display the withdraw history
				String data=new String();
				System.out.printf("%80s", "MONEY WITHDRAWAL HISTORY\n");

				try {
					Scanner fileData=new Scanner(withdrawHist);


					while(fileData.hasNextLine()) {
						data=fileData.nextLine();
						System.out.println(data);
					}

					fileData.close();
				}

				catch(FileNotFoundException e) {
					;
				}
			}
		}

		else {
			System.out.println("No withdrawals made.....");
		}

		if(transferHist.exists()) {
			if(transferHist.length()==0) {
				System.out.println("No money transfer made so far.....");
			}

			else {
				// display the money transfer history
				String data=new String();
				System.out.printf("%80s", "MONEY TRANSFER HISTORY\n");

				try {
					Scanner fileData=new Scanner(transferHist);

					while(fileData.hasNextLine()) {
						data=fileData.nextLine();
						System.out.println(data);
					}

					fileData.close();
				}

				catch(FileNotFoundException e) {
					;
				}

			}
		}

		else {
			System.out.println("No money transfer made so far.....");
		}

		if(depositHist.exists()) {
			if(depositHist.length()==0) {
				System.out.println("No deposits.....");
			}

			else {
				// display the deposit history
				String data=new String();
				System.out.printf("%80s", "MONEY DEPOSIT HISTORY\n");
				try {
					Scanner fileData=new Scanner(depositHist);

					while(fileData.hasNextLine()) {
						data=fileData.nextLine();
						System.out.println(data);
					}

					fileData.close();
				}

				catch(FileNotFoundException e) {
					;
				}
			}
		}

		else {

			System.out.println("No deposits.....");
		}
	}
}

class Withdraw implements ATM {

	static float update(float bankBalance, float amount) {

		try {
			FileWriter withdrawData=new FileWriter("Withdraw.txt", true);
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now=LocalDateTime.now();
			withdrawData.write(String.format("%s %50s %30s %30s", dtf.format(now), bankBalance, amount, bankBalance-amount+"\n"));
			bankBalance-=amount;
			withdrawData.close();
		}

		catch(IOException e) {
			;
		}

		return bankBalance;
	}
}

class Transfer implements ATM {

	static float update(float bankBalance, float amount, String accNo) {
		try {
			FileWriter transferData=new FileWriter("Transfer.txt", true);
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now=LocalDateTime.now();
			transferData.write(String.format("%s %50s %30s %30s %30s", dtf.format(now), bankBalance, amount, bankBalance-amount, accNo+"\n"));
			bankBalance-=amount;
			transferData.close();
		}

		catch(IOException e) {
			;
		}

		return bankBalance;
	}
}

class Deposit implements ATM {

	static float update(float bankBalance, float amount) {
		try {
			FileWriter depositData=new FileWriter("Deposit.txt", true);
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now=LocalDateTime.now();
			depositData.write(String.format("%s %50s %30s %30s", dtf.format(now), bankBalance, amount, bankBalance+amount+"\n"));
			bankBalance+=amount;
			depositData.close();
		}

		catch(IOException e) {
			;
		}

		return bankBalance;
	}
}

class initATM {

	static int login() {
		System.out.println("Please enter login details:");
		System.out.print("User ID: ");
		Scanner userid=new Scanner(System.in);
		String id=userid.nextLine();

		System.out.print("PIN: ");
		Scanner userpin=new Scanner(System.in);
		int pin=userpin.nextInt();

		ATM atm=new credentials();
		if(id.compareTo(atm.userId)==0) {
			if(pin==atm.userPin)
				return 1;

			else 
				return -1;
		}

		else
			return -1;
	}

	static void cls() {
			System.out.println("\033[H\033[2J");
	}

	static void init() {
		if(login()==1) {
			Random random=new Random();
			String load="[";
			cls();
			int spaces=30;

			for(int i=0; i<spaces; i++) {
				System.out.println("ATM is starting.....");
				System.out.print(load);
				for(int j=0; j<spaces-load.length(); j++) {
					System.out.print(" ");
				}
				System.out.println("]");
				try{
					TimeUnit.SECONDS.sleep(random.nextInt(3));
					cls();
				}

				catch(InterruptedException e) {
					;
				}

				load+="#";
			}
		}

		else {
			System.out.println("SORRY!! INCORRECT DETAILS!!");
			System.exit(0);
		}
	}
}

class main {

	private static int menu() {
		int option;
		System.out.printf("%70s", "Please select your operation\n");
		System.out.printf("\n%s", "[1] History.");
		System.out.printf("%120s", "[2] Withdraw.\n");
		System.out.printf("\n%s", "[3] Transfer.");
		System.out.printf("%118s", "[4] Deposit.\n");
		System.out.printf("\n%s", "[5] Exit.");

		System.out.print("\n> ");
		Scanner input=new Scanner(System.in);
		try {
			option=input.nextInt();
			return option;
		}

		catch(InputMismatchException e) {
			return -1;
		}
	}

	public static void main(String args[]) {
		initATM.init();  // start the ATM
		File moneyWithdraw=new File("Withdraw.txt");

		if(moneyWithdraw.exists()) {
			;
		}

		else {
			try {
				moneyWithdraw.createNewFile();
			}

			catch(IOException e) {
				;
			}

			try {
				FileWriter createColumn=new FileWriter(moneyWithdraw, true);
				createColumn.write(String.format("%s %50s %30s %30s", "Date and time", "Balance before", "Amount withdrawn", "Balance now\n"));
				createColumn.close();
			}

			catch(IOException e) {
				;
			}
		}

		File moneyTransfer=new File("Transfer.txt");

		if(moneyTransfer.exists()) {
			;
		}

		else {
			try {
				moneyTransfer.createNewFile();
			}

			catch(IOException e) {
				;
			}

			try {
				FileWriter createColumn=new FileWriter(moneyTransfer, true);
				createColumn.write(String.format("%s %50s %30s %30s %30s", "Date and time", "Balance before", "Amount deposited", "Balance now", "Transferred to\n"));
				createColumn.close();
			}

			catch(IOException e) {
				;
			}
		}

		File moneyDeposit=new File("Deposit.txt");

		if(moneyDeposit.exists()) {
			;
		}

		else {
			try {
				moneyDeposit.createNewFile();

				try {
					FileWriter createColumn=new FileWriter(moneyDeposit, true);
					createColumn.write(String.format("%s %50s %30s %30s", "Date and time", "Balance before", "Amount deposited", "Balance now\n"));
					createColumn.close();
				}

				catch(IOException e) {
					;
				}
			}

			catch(IOException e) {
				;
			}
		}
		/* from the file, balance.txt, read the amount available in the user's account and display it
		 */
		String fileBalance=new String();
		float bankBalance=0.0f;

		try {
			File balance=new File("balance.txt");
			if(balance.exists()) {
				Scanner balanceReader=new Scanner(balance);
				// read the balance from the file
				while(balanceReader.hasNextLine()) {
					fileBalance=balanceReader.nextLine();
				}

				bankBalance=Float.valueOf(fileBalance);

			}

			else {  // if the file doesn't exist
				balance.createNewFile();  // create the file

				try {
					FileWriter writeBalance=new FileWriter("balance.txt");
					writeBalance.write("9000000");
					bankBalance=9000000f;
				}

				catch(IOException e) {
					System.out.println("Failed to write!!");
				}
			}
		}

		catch(IOException e) {
			;
		}

		Scanner inputAmount=new Scanner(System.in);   // this is for taking amount inputs
		float amount;

		String accNo=new String();
		Scanner accNum=new Scanner(System.in);
		
		// For choosing option
		int userInp;
		Scanner option=new Scanner(System.in);

		FileWriter writeBalance;


		int opt;
		while(true) {
			opt=menu();
			if(opt<0 || opt>5) {
				System.out.println("ERROR!! Invalid option! Enter again");
				continue;
			}

			else {
				switch(opt) {
					case 1:
						History.fetch();  // fetch the past payments and transactions
						break;
						
					case 2:
						System.out.print("Enter amount to withdraw >");
						amount=inputAmount.nextFloat();

						bankBalance=Withdraw.update(bankBalance, amount);

						try{
							writeBalance=new FileWriter("balance.txt");
							writeBalance.write(String.format("%s", bankBalance));
							writeBalance.close();
						}
						catch(IOException e) {
							;
						}

						break;

					case 3:
						System.out.print("Enter destination account number: ");
						accNo=accNum.nextLine();

						System.out.printf("%10s", "[0] Correct\n");
						System.out.printf("%10s", "[1] In-Correct\n>");
						userInp=option.nextInt();

						if(userInp==0) {
							// proceed to transfer money
							if(accNo.length()>11 || accNo.length()<11) {
								System.out.println("Account number entered by you is invalid!! Stopping transaction!!!");
								continue;
							}
						}

						else {
							System.out.println("Cancelling transaction!");
							continue;
						}

						System.out.print("Enter amount to transfer: ");
						amount=inputAmount.nextFloat();

						bankBalance=Transfer.update(bankBalance, amount, accNo);

						try{
							writeBalance=new FileWriter("balance.txt");
							writeBalance.write(String.format("%s", bankBalance));
							writeBalance.close();
						}

						catch(IOException e) {
							;
						}


						break;

					case 4:
						System.out.print("Enter amount to be deposited: ");
						amount=inputAmount.nextFloat();

						bankBalance=Deposit.update(bankBalance, amount);

						try {
							writeBalance=new FileWriter("balance.txt");
							writeBalance.write(String.format("%s", bankBalance));
							writeBalance.close();
						}

						catch(IOException e) {
							;
						}
						break;

					case 5:
						System.exit(0);
						break;
				}
			}
		}
	}
}
