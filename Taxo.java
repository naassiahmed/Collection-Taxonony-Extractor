import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.management.OperationsException;


public class Taxo {


	public static void main(String[] args) throws IOException {
		
		//Opertaion.GetDocsWithTermsMax();
	//	Opertaion.GetTopicsWithDocs();
		//Opertaion.GetAllTopics();
	//Opertaion.WriteTopicFileToDisk();
		Opertaion.WriteTopicFileToDisk(true,false);
		//Opertaion.GetTopicDescription();
//		System.out.println(Opertaion.ReplaceIdfByTerms());
		//Opertaion.GetTOPICwithTERMS();
		//Opertaion.GetTermsWithTopics();
	//Opertaion.GetTermTopicsFromFileAndWrite();
		//Opertaion.OptimizeTaxonomie();
		
	}
}