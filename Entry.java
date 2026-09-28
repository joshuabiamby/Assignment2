class Entry{

    private String counterParty;
    private boolean counterPartyGiving = false;
    private int quantity;
    private int id;
    public static int nextId = 1; 

    //getters
    public String get_CounterParty(){return counterParty;}
    public boolean is_CounterPartyGiving(){return counterPartyGiving;}
    public int get_Quantity(){return quantity;}
    public int get_id(){return id;}
    
    //Constructor
    private Entry(String counterParty, int quantity, boolean counterPartyGiving){
        this.counterParty = counterParty;
        this.quantity = quantity;
        this.counterPartyGiving = counterPartyGiving;
        id = nextId++;  
    } 

    @Override 
    public String toString(){
        if (counterPartyGiving){
            return "# "+id+ "recieved ${" +quantity+ "} from " +counterParty;
        }
        else{
            return "# "+id+ "gave ${" +quantity+ "} to " +counterParty;
        }
    }

    public static Entry[] createEntries(String givers_name, String receivers_name, int amount){
        Entry senders_entry =  new Entry(givers_name, amount, true);
        Entry receiver_entry = new Entry(receivers_name, amount, false);
        return new Entry[]{receiver_entry, senders_entry};
    }

}
