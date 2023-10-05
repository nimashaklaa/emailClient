package Hello;

abstract class EmailReceivers {

    private final String name;
    private final String emailAddress;

    public EmailReceivers(String name,String emailAddress){
        this.name=name;
        this.emailAddress=emailAddress;


    }

    public String getEmailAddress(){
        return emailAddress;
    }





}
