package Hello;

public class  OfficialFriends extends EmailReceivers{
    String emailContent="Wishing you the best on your birthday and everything good in the year ahead!!\n From:your loving,\n Amandi Nimasha";
    String emailSubject="wishing you a happy birthday!";
    String position;
    String birthday;



    public OfficialFriends(String name, String emailAddress,String position,String birthDay) {
        super(name, emailAddress);
        this.position=position;
        this.birthday=birthDay;


    }

    public String getEmailSubject(){

        return(this.emailSubject);
    }
    public String getEmailContent(){

        return(this.emailContent);
    }
    public boolean checkBirthday(String birthDay){

        return (birthday).substring(5,10).equals(birthDay);
    }


}