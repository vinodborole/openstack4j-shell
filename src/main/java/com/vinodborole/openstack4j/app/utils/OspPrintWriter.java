package com.vinodborole.openstack4j.app.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class OspPrintWriter {
    
    public static void printHelp(final String command,final Options options)
    {
            final String commandLineSyntax = command;
            OutputStream out = System.out;
            final PrintWriter writer = new PrintWriter(out);
            final HelpFormatter helpFormatter = new HelpFormatter();
            int printedRowWidth=500;
            String header="options:";
            int spacesBeforeOption=5;
            int spacesBeforeOptionDescription=3;
            String footer="";
            boolean displayUsage=true;
            helpFormatter.printHelp(
               writer,
               printedRowWidth,
               commandLineSyntax,
               header,
               options,
               spacesBeforeOption,
               spacesBeforeOptionDescription,
               footer,
               displayUsage);
            writer.flush();
    }    
    public static void displayBlankLines(final int numberBlankLines,final OutputStream out)
    {
            try
            {
               for (int i=0; i<numberBlankLines; ++i)
               {
                  out.write("\n".getBytes());
               }
            }
            catch (IOException ioEx)
            {
               for (int i=0; i<numberBlankLines; ++i)
               {
                  System.out.println();
               }
            }
     }
}
