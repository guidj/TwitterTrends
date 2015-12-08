package com.gpjpe.twittertrends;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println("These is what you fed me:");
        for(String argument: args){
            System.out.println("\t" + argument);
        }

    }
}
