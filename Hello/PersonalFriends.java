package Hello;

public class PersonalFriends extends EmailReceivers{
    String emailSubject="wishing you a happy birthday!";
    String emailContent="Sending lots of love, hugs, and happy birthday wishes for a wonderful day!!\n From:your loving,\n Amandi Nimasha";
    private final String nickName;
    private final String birthday;




    public PersonalFriends(String name,String nickName, String emailAddress,String birthDay) {
        super(name, emailAddress);
        this.nickName=nickName;
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
