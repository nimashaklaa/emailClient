package Hello;

import java.io.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmailClient {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        String mail;
        int sum=0;
        System.out.println("""
                Enter the Selection: \s
                1 - Option to add a new Email receiver to the list
                2 - Option to send an email to relative recipient
                3 - Option to print all the people who have birthdays on a relevant date
                4 - Option to print all the information of emails of a relevant date
                5 - Option to Print all  the number of Friends in the list""");

        int selections = scanner.nextInt();

        List<EmailReceivers> birthdayPersons;
        birthdayPersons = new ArrayList<>();
        //ArrayList<Email> emailArrayList=new ArrayList<Email>();

        OtherOfficials official ;
        OfficialFriends officialFriend = null;
        PersonalFriends personalFriend = null;
//creating arrays to keep a track on the Friends separately
        List<OtherOfficials> officialList = new ArrayList<OtherOfficials>();  //arrays to store the objects created
        List<OfficialFriends> officeFriendList = new ArrayList<OfficialFriends>();
        List<PersonalFriends> personalFriendsListList = new ArrayList<PersonalFriends>();

        switch (selections) {
            //Adding a new Recipient
            case 1:
                System.out.println("""
                        Input Format:\s
                        Other Officials: name,email Address,position,birthday\s
                        Official Friends: name,email Address,position,birthday\s
                        Personal Friends: name,<nickname>,email Address,birthday
                        """);
                Scanner add = new Scanner(System.in);
                String data = add.nextLine();
                try {
                    //Writing in the Client File
                    FileWriter Entry = new FileWriter("D:\\emailClient\\Hello\\ClientList", true);
                    Entry.write(data + "\n");
                    Entry.close();

                }catch (IOException ioException){
                    ioException.printStackTrace();

                }
                System.out.println("Entry successful \n");
                break;

            case 2:
                //taking input of the email Address,and Email body
                System.out.println("Input Email Address,subject and emailContent \n"); //sending an email
                Scanner emailAndContent = new Scanner(System.in);
                mail = emailAndContent.nextLine();
                String[] dataContent = mail.split(",");

                //sending the email
                JavaMail newEmail = new JavaMail(dataContent[0], dataContent[1]); //emailAddress and emailSubject
                newEmail.SendEmail(dataContent[2]); //emailContent
                //Serialize the objects
                SerializeObjects serializeObject = new SerializeObjects();
                serializeObject.serializingObjects(newEmail);



                break;


            //printing all the recipients who have birthdays
            case 3:
                List<String> arr = new ArrayList<>();//creating an array
                //taking the date that you have to print
                System.out.println("Enter the date you want: \n date format : yyyy/mm/dd");
                Scanner dateInput = new Scanner(System.in);
                String date = dateInput.nextLine();
                File information = new File("D:\\emailClient\\Hello\\ClientList");
                //reading the info from the txt
                Scanner readInfo = new Scanner(information);

                while (readInfo.hasNextLine()) {
                    String dataTake = readInfo.nextLine();
                    arr.add(dataTake);
                }

                for (String client : arr) {
                    String[] subInfo = client.split(",");
                    int subInfoSize = subInfo.length;
                    if (subInfoSize == 4) {
                        if (((subInfo[3]).substring(5, 10)).equals((date).substring(5, 10))) {
                            String birthDayPersonName = subInfo[0].split(":")[1];
                            System.out.println(birthDayPersonName);
                        }
                    }


                }
                break;


            case 4:
                //taking the date you want to take information
                System.out.println("Enter the date you want to take information: \ninput date format yyyy/mm/dd"); //
                Scanner inputDate = new Scanner(System.in);
                String dateInfo = inputDate.nextLine();

                //copying all the data you sent in emails for the relevant date

                String fileName = "D:\\emailClient\\Hello\\SerializedFile.ser";
                File fileCopy = new File(fileName);

                //if such a file exists before
                if (fileCopy.exists()) {

                    JavaMail emails ;

                    //Deserialization
                    try {
                        FileInputStream fileInputStream = new FileInputStream("D:\\emailClient\\Hello\\SerializedFile.ser");
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                        emails = (JavaMail) objectInputStream.readObject();

                        //assert emails != null;
                        if (emails.dateTake.equals(dateInfo)) {
                            System.out.println("EmailReceivers: " + emails.recipients + "\n" + "Subject: " + emails.subject+ "\n"+"EmailContent: "+emails.emailBody+"\n");

                        }

                        fileInputStream.close();
                        objectInputStream.close();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                break;


            case 5:

                // code to print the number of recipient objects in the application
                File seek = new File("D:\\emailClient\\Hello\\ClientList");
                Scanner readFile = new Scanner(seek);
                sum=0;
                while (readFile.hasNextLine()) {
                    //String getData = readFile.nextLine();

                    sum+=1;



                }
                System.out.println("number of recipient objects in the application :" + sum);
                //writing in the Count_on_objects file the sum of clients
                try {

                    FileWriter Writer = new FileWriter("D:\\emailClient\\Hello\\Count_On_Objects", false);
                    Writer.write(sum + "\n");
                    Writer.close();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                break;


        }
        try {


            LocalDate dateObject = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String date = dateObject.format(dateTimeFormatter);

            FileInputStream fileInputStream = new FileInputStream("D:\\emailClient\\Hello\\ClientList");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String dataEntry;

            while ((dataEntry = bufferedReader.readLine()) != null) {
                //splitting the entry by ":"
                String[] entryLine = dataEntry.split(":");
                //taking the type of the person
                String type =entryLine[0].trim();




                //keep a track on Other Officials
                if(type.equals("Other Officials"))
                {
                    String[] recipientInfo = entryLine[1].split(",");
                    official = new OtherOfficials(recipientInfo[0], recipientInfo[1], recipientInfo[2]);
                    officialList.add(official);
                }
                //Keep track of Official Friends
                else if (type.equals("Official Friends"))
                {
                    String[] recipientInfo = entryLine[1].split(",");
                    officialFriend = new OfficialFriends(recipientInfo[0], recipientInfo[1], recipientInfo[2], recipientInfo[3]);
                    officeFriendList.add(officialFriend);
                    //adding the official Friends to the list
                    if (officialFriend.checkBirthday(date.substring(5, 10))) {
                        birthdayPersons.add(officialFriend);
                    }
                }
                //Keep track of Personal Friends
                else if (type.equals("Personal Friends"))
                {
                    String[] recipientInfo = entryLine[1].split(",");
                    personalFriend = new PersonalFriends(recipientInfo[0], recipientInfo[1], recipientInfo[2], recipientInfo[3]);
                    personalFriendsListList.add(personalFriend);
                    if (personalFriend.checkBirthday(date.substring(5, 10))) {
                        birthdayPersons.add(personalFriend);

                    }

                }

            }

        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        //Serializing Objects

        SerializeObjects serializeObjects = new SerializeObjects();

        JavaMail email;
        for (EmailReceivers birthdayPerson : birthdayPersons) {

            if (birthdayPerson instanceof PersonalFriends) {
                assert personalFriend != null;
                email = new JavaMail(personalFriend.getEmailAddress(), personalFriend.getEmailSubject());
                email.SendEmail(personalFriend.getEmailContent());
                serializeObjects.serializingObjects(email);
            }
            if (birthdayPerson instanceof OfficialFriends) {

                assert officialFriend != null;
                email = new JavaMail(officialFriend.getEmailAddress(), officialFriend.getEmailSubject());
                email.SendEmail(officialFriend.getEmailContent());
                serializeObjects.serializingObjects(email);

            }

        }







    }









}