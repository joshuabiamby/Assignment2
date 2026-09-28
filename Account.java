import java.util.ArrayList;

public class Account {
    private String owner;
    private ArrayList<Entry> entries = new ArrayList<>();

    //getter
    public String getOwner(){return owner;}

    //constructor
    public Account(String owner){
        this.owner = owner;
        this.entries = new ArrayList<>();
    }
    
    public void postEntry(Entry entry){
        entries.add(entry);
    }

    public ArrayList<Entry> listByCounterParty(String name){
        ArrayList<Entry> subList = new ArrayList<>();

        //loops through entries arraylist using blank string to match
        if(name.equals("")){
            for (Entry e: entries){
                subList.add(e);
            }
        }
        //loops through entries arraylist using getter. Adds to sublist.
        else{
            for (Entry e: entries){
            if (e.get_CounterParty().equals(name)){
                subList.add(e);
            } 
            }
        }
        return subList;
        }

    public ArrayList<String> listCounterparties(){
        // Call sublist to get all entries.
        ArrayList<Entry> all_Entries = listByCounterParty("");

        //New comparison list
        ArrayList<String> uniqueStrings = new ArrayList<>();

        //Loop through list and get names
        for (Entry e: all_Entries){
            String name = e.get_CounterParty();
            //If name isnt in unique strings then append.
            if(!uniqueStrings.contains(name)){
                uniqueStrings.add(name);
            }
        }
    return uniqueStrings;
    }

    public String getStatement(){
        String statement = "Statement " + this.owner + ":"; 
        ArrayList <String> party_involved = listCounterparties();


        //Get all the entries by the counterparty
        for(String party: party_involved){
            ArrayList<Entry> entriesByCP = listByCounterParty(party);

            //initialize amounts to 0
            int amountGiven = 0;
            int amountReceieved = 0;

            //Loop through counterparty
            for(Entry e : entriesByCP){
                if(!e.is_CounterPartyGiving()){ //If false that means the CP gave
                    amountGiven += e.get_Quantity();
                }
                else{   
                    amountReceieved += e.get_Quantity(); //If true the CP recieved
                }
            }

            //Do math
            int balance = amountGiven - amountReceieved;

            if(balance > 0){// Owed money
                statement = statement + party + " owes " + balance + " to " + this.owner + "\n";
            } else if(balance < 0){ //money owed
                statement = statement + this.owner + " owes $" + Math.abs(balance) + " to" + party + "\n";
            }
        }
        
        return statement;
    }
}
