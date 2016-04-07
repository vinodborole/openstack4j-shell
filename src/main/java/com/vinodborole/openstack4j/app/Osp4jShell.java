/**
 * @author viborole
 */
package com.vinodborole.openstack4j.app;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.common.base.Strings;
import com.vinodborole.openstack4j.app.commands.Commands;
import com.vinodborole.openstack4j.app.commands.factory.IOsp4jShellCommands;
import com.vinodborole.openstack4j.app.commands.factory.Osp4jShellCommandsFactory;

public class Osp4jShell {
    public static void main(String[] args) throws Exception {
     if(args.length>0 && !Strings.isNullOrEmpty(args[0]) && args[0].equalsIgnoreCase("testsuite")){
         if(!Strings.isNullOrEmpty(args[1])){
             File file = new File(args[1]);
             FileReader fileReader = new FileReader(file);
             BufferedReader console = new BufferedReader(fileReader);
             while (true) {
                 String commandLine = console.readLine();
                 evaluateCommand(commandLine);
             }
         }else{
             System.out.println("Missing arguments, please use following command.");
             System.out.println("java -jar <jarname> testsuite <testsuitefilepath>");
             System.exit(0);
         }
     }else{ 
             Console console=System.console();
             if(console!=null){
                 while (true) {
                	 try{
                		 executeShell(console);
                	 }catch(Exception e){System.out.println("Exception: "+e.getMessage());}
                 }
             }else{
                 System.out.println("No Console associated, Cannot execute the program!");
             }
     }
  }

   private static void executeShell(Console console) throws Exception{
        System.out.print("osp>");
        String commandLine = console.readLine();
        commandLine=filterCommandLine(commandLine);
        evaluateCommand(commandLine);
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
        osp4jShellCommandExecutor.executeCommand(command, params);
    }
    
    
}
