package Hello;

public class OtherOfficials extends EmailReceivers {

    String position;

    public OtherOfficials(String name, String emailAddress,String position) {
        super(name, emailAddress);
        this.position=position;
    }

}