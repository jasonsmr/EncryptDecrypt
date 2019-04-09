/**********************************************************************
 Main.java is main EncryptDecrypt project Main class, it holds the
 main function and is called first by the compiled jar.

 @author Jason Rupright
 @version 4/10/2019
 **********************************************************************/
package com.example.encryptdecrypt;

public class Main {
    public static void main(String[] args) {

        String arg = args[0].trim();
        System.out.println("Command: "+arg);
        if(!arg.equals("mix") && !arg.equals("unmix"))
            System.out.println("First switch options:\n" +
                    "mix/unmix\nNo other switches allowed");
        if(arg.equals("mix")) {
            Mix mix = new Mix(false);
            mix.userMessage = args[1];
            mix.userUndo = "";
            System.out.println(mix.userMessage);
            mix.populateMessage();
            mix.mixture();
        }
        else if(arg.equals("unmix")){
            UnMix v = new UnMix();
            v.unMixture(args[1], args[2]);
        }
    }
}
