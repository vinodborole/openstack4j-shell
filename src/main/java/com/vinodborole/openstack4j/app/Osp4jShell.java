package com.vinodborole.openstack4j.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.common.base.Strings;
import com.vinodborole.openstack4j.app.commands.Commands;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.commands.factory.Osp4jShellCommandsFactory;
/**
 * Main shell class, generates the osp> prompt, accepts all the commands and delegates it to respective shell command for processing by using the factory design.
 * 
 * @author vinod borole
 */
public class Osp4jShell {
    public static void main(String[] args) throws Exception {
        if(args.length>0 && !Strings.isNullOrEmpty(args[0]) && args[0].equalsIgnoreCase("testsuite")){
            fileSource(args);
        }else{
            printSignature();
            cliSource();
        }
    }
    private static void fileSource(String[] args) throws FileNotFoundException, IOException, Exception {
        if(!Strings.isNullOrEmpty(args[1])){
            File file = new File(args[1]);
           try( 
                FileReader fileReader = new FileReader(file);
                BufferedReader console = new BufferedReader(fileReader)){
                while (true) {
                    String commandLine = console.readLine();
                    commandLine=filterCommandLine(commandLine);
                    evaluateCommand(commandLine);
                }
            }
        }else{
            System.out.println("Missing arguments, please use following command.");
            System.out.println("java -jar <jarname> testsuite <testsuitefilepath>");
            System.exit(0);
        }
    }
    private static void cliSource() throws Exception {
        try(BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in))){
            while (true) {
                try{
                    System.out.print("osp>");
                    String commandLine = bufferRead.readLine();
                    commandLine=filterCommandLine(commandLine);
                    evaluateCommand(commandLine);
                }catch(Exception e){System.out.println("Exception: "+e.getMessage()); e.printStackTrace();System.out.println("Type 'help' for command information");}
            }
        }
    }
    private static void printSignature() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("signature.txt");
        try(InputStreamReader r = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(r)){
            String line = null;
            while ((line = br.readLine()) != null) {
              System.out.println(line);
            }
        }
    }
    private static String filterCommandLine(String commandLine) {
        //remove extra spaces
        return commandLine.replaceAll("\\s+", " ");
    }
    private static void evaluateCommand(String commandLine) throws Exception{
        if(commandLine==null){
            System.exit(0);
        }else if (commandLine.equals("") || commandLine.startsWith("#")){
          return;
        }else if(commandLine.equalsIgnoreCase("exit"))  {
            System.exit(0);
        }
        // Split the string into a String Array
        ArrayList<String> params = new ArrayList<String>();
        String[] lineSplit = commandLine.split(" ");
         
        int size = lineSplit.length;
        for (int i=0; i<size; i++)  {
            params.add(lineSplit[i]); 
         } 
        Commands command=Commands.fromString(params.get(0));
        
        Osp4jShellCommandsFactory osp4jShellCommandsFactory=Osp4jShellCommandsFactory.getInstance();
        IOsp4jShellCommands osp4jShellCommandExecutor=osp4jShellCommandsFactory.getShellCommandExecutor(command);
        osp4jShellCommandExecutor.executeCommand(params.toArray(new String[params.size()]));
    }
}
