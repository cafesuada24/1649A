package com.hahsm.common.ioconsole;

import java.util.Scanner;

import com.hahsm.datastructure.adt.List;

public class MenuBuilder {
    //private boolean needClose;
    private String header;
    private List<Object> options;
	private int start = 0;


    public void setIndexStart(final int start) {
        this.start = start;
    }

    public void setHeader(final String header) {
        this.header = header;
    }

    public void removeHeader() {
        this.header = null;
    }

    public void setOptions(List<Object> options) {
		this.options = options;
	}

    public int getUserOption(Scanner scanner) {
        assert options != null;

        printOptions();
        
        return ConsoleHelper.getInteger(scanner, "Please enter your choice: ", start, start + options.size() - 1);
    }

    public void printOptions() {
        assert options != null;

        final String prefix = header == null ? "" : "\t";
        if (header != null) {
            System.out.println(header);
        }

        for (int i = 0, end = start + options.size(); i < end; ++i) {
            System.out.printf("%s%d. %s%n", prefix, i + start, options.get(i));
        }
    }


    //private void ensureScanner() {
    //    if (scanner == null) {
    //        scanner = new Scanner(System.in);
    //        needClose = true;
    //    }
    //}
    //
    //private void ensureScannerClosed() {
    //    if (needClose) {
    //        scanner.close();
    //        scanner = null;
    //        needClose = false;
    //    }
    //}
}
