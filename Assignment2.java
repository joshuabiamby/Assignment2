import java.util.ArrayList;
import java.util.Scanner;

public class Assignment2 {
    public static void main(String[] args){
        ArrayList<Account> ledger = new ArrayList<>();
                Scanner input = new Scanner(System.in);
                boolean running = true;

                while (running) {
                    System.out.println("\n--- Bank-A-Nator ---");
                    System.out.println("1. Load file");
                    System.out.println("2. Show all accounts");
                    System.out.println("3. Show account statement");
                    System.out.println("4. List all entries in account");
                    System.out.println("5. Quit");
                    System.out.print("Select an option: ");

                    String choice = input.nextLine();

                    switch (choice) {
                        case "1":
                            System.out.print("Enter the filename: ");
                            String fileName = input.nextLine();
                            if (populateAccounts(ledger, fileName)) {
                                System.out.println("Success: File loaded and accounts populated.");
                            } else {
                                System.out.println("Error: Could not load file.");
                            }
                            break;

                        case "2":
                            for (Account a : ledger) {
                                System.out.println(a.getOwner());
                            }
                            break;

                        case "3":
                            System.out.print("Enter account owner name: ");
                            String ownerName = input.nextLine();
                            Account foundAcc = null;
                            for (Account a : ledger) {
                                if (a.getOwner().equals(ownerName)) {
                                    foundAcc = a;
                                    break;
                                }
                            }
                            if (foundAcc == null) {
                                System.out.println("Error: No account found with that name.");
                            } else {
                                System.out.println(foundAcc.getStatement());
                            }
                            break;

                        case "4":
                            System.out.print("Enter account owner name: ");
                            String listOwner = input.nextLine();
                            Account listAcc = null;
                            for (Account a : ledger) {
                                if (a.getOwner().equals(listOwner)) {
                                    listAcc = a;
                                    break;
                                }
                            }

                            if (listAcc == null) {
                                System.out.println("Error: No account found with that name.");
                            } else {
                                System.out.print("Enter counterparty name: ");
                                String cpName = input.nextLine();
                                ArrayList<Entry> results = listAcc.listByCounterParty(cpName);
                                for (Entry e : results) {
                                    System.out.println(e);
                                }
                            }
                            break;

                        case "5":
                            running = false;
                            System.out.println("Sayonara.");
                            break;

                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }
                input.close();
            }

 public static boolean populateAccounts(ArrayList<Account> accounts, String filename){
            ArrayList<String> transactions = TransactionLoader.loadTransactions(filename);
            if(transactions == null){
                return false;
            }
            else{
                accounts.clear();
                for(String t:transactions){
                    String[] splits = t.split(" ");

                    //Text file has giver at [0], receiver at [2], amount at [3]
                    String giver = splits[0];
                    String receiver = splits[2];
                    int amount = Integer.parseInt(splits[3].replace("$", ""));

                    Entry[] entries = Entry.createEntries(giver, receiver, amount);

                    //Retrieve or create giver account.
                    Account giverAccount = null;
                    for(Account a: accounts){
                        if(a.getOwner().equals(giver)){
                            giverAccount = a;
                            break;
                        }
                    }
                    if (giverAccount == null){
                        giverAccount = new Account(giver);
                        accounts.add(giverAccount);
                    }

                    //Retrieve or create receiver account.
                    Account recieverAccount = null;
                    for(Account a: accounts){
                        if(a.getOwner().equals(receiver)){
                            recieverAccount = a;
                            break;
                        }
                    }
                    if (recieverAccount == null){
                        recieverAccount = new Account(receiver);
                        accounts.add(recieverAccount);
                    }

                    //now post to entries array
                    giverAccount.postEntry(entries[0]);
                    recieverAccount.postEntry(entries[1]);
                }
                return true;
            }
        }
}
