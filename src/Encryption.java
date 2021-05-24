import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Encryption {

    private static final char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public Encryption(){}

    public int getLetterNumber(char c)
    {
        for(int i = 0; i<letters.length; i++)
        {
            if(c==letters[i])
            {
                return i;
            }
        }
        return 26;
    }

    public char getEncryptedLetter(char c, int key)
    {
        int l = (getLetterNumber(c)+key)%26;
        return letters[l];
    }

    public char getDecryptedLetter(char c, int key)
    {
        int l = (getLetterNumber(c)-key)%26;
        if(l<0)
        {
            return letters[l+26];
        }
        return letters[l];
    }

    public int readKey()
    {
        int key = 0;
        try {
            Scanner s = new Scanner(new File("key.txt"));
            if(s.hasNext())
            {
                key = s.nextInt();
            }
            s.close();
        }
        catch (Exception e)
        {
            System.out.println("Error: key.txt not found.");
        }
        System.out.println(key);
        if(key<0)
        {
            return 0;
        }
        return key;
    }

    public void encrypt()
    {
        int key = readKey();
        try
        {
            Scanner s = new Scanner(new File("text.txt"));
            String text = "";
            while(s.hasNext())
            {
                text = text + s.nextLine().toLowerCase();
            }
            s.close();
            char[] ca = text.toCharArray();
            for(int i = 0; i<ca.length; i++)
            {
                if(getLetterNumber(ca[i])!=26)
                {
                    ca[i]= getEncryptedLetter(ca[i], key);
                }
            }
            String encText = "";
            for(int i = 0; i<ca.length; i++)
            {
                encText = encText + ca[i];
            }
            try{
                BufferedWriter writer = new BufferedWriter((new FileWriter("encText.txt")));
                writer.write(encText);
                writer.close();
            }
            catch (Exception e)
            {
                System.out.println("Error: encText.txt not found.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: text.txt not found!");
        }
    }

    public void decrypt()
    {
        int key = readKey();
        try
        {
            Scanner s = new Scanner(new File("encText.txt"));
            String encText = "";
            while(s.hasNext())
            {
                encText = encText + s.nextLine().toLowerCase();
            }
            s.close();
            char[] ca = encText.toCharArray();
            for(int i = 0; i<ca.length; i++)
            {
                if(getLetterNumber(ca[i])!=26)
                {
                    ca[i] = getDecryptedLetter(ca[i], key);
                }
            }
            String text = "";
            for(int i = 0; i<ca.length; i++)
            {
                text = text + ca[i];
            }
            try{
                BufferedWriter writer = new BufferedWriter((new FileWriter("text.txt")));
                writer.write(text);
                writer.close();
            }
            catch (Exception e)
            {
                System.out.println("Error: text.txt not found.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: encText.txt not found!");
        }
    }
}

