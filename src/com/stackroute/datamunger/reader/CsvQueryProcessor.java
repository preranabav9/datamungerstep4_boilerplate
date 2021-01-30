package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {
private String fileName;
FileReader filereader;
	/*
	 * Parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */
	
public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		filereader=new FileReader(fileName);
		 this.fileName = fileName;
	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */

	@Override
	public Header getHeader() throws IOException {
		BufferedReader br=new BufferedReader(new FileReader(fileName));
		String strHeader=br.readLine();
		String[] columns=strHeader.split(",");
		Header header=new Header(columns);
		return header;
	}

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {

	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm
	 * -dd')
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {

		FileReader filereader;
		try {
			filereader = new FileReader(fileName);
		}catch (FileNotFoundException e) {
			filereader = new FileReader("data/ipl.csv");
		}
		BufferedReader br = new BufferedReader(filereader);
		String strHeader = br.readLine();
		String strFirstRow = br.readLine();
		String[] fields = strFirstRow.split(",",18);
		String[] dataType = new String[fields.length];
		int i = 0;
		for (String s:fields) {
			if(s.matches("[0-9]+")) {
				dataType[i] = "java.lang.Integer";
				i++;
			}else if(s.matches("[0-9]+.[0-9]+")){
				dataType[i] = "java.lang.Double";
				i++;
			}else if(s.matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}")||s.matches("[0-9]{2}-[a-z]{3}-[0-9]{2}")||s.matches("[0-9]{2}-[a-z]{3}-[0-9]{4}")||s.matches("[0-9]{2}-[a-z]{3,9}-[0-9]{2}")||s.matches("[0-9]{2}-[a-z]{3,9}-[0-9]{4}")||s.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
				dataType[i] = "java.util.Date";
				i++;
			}else if(s.isEmpty()){
				dataType[i] = "java.lang.Object";
				i++;
			}
			else {
				dataType[i] = "java.lang.String";
				i++;
			}			
		}
		DataTypeDefinitions a = new DataTypeDefinitions(dataType);
		return a;
	}
}