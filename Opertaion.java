import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.management.OperationsException;


public class Opertaion {

	
	public static LinkedHashMap<Integer,LinkedHashMap<Integer,Double>> GetDocsWithTerms() throws FileNotFoundException{
				
			File file = new File("D:/Taxonomie/lyrl2004_vectors_train.dat");
				BufferedReader in = new BufferedReader(new FileReader(file));
				String txt;
				int a=0;
				LinkedHashMap<Integer,LinkedHashMap<Integer,Double>> DocTerms=new LinkedHashMap<Integer,LinkedHashMap<Integer,Double>>();
				try {
					while ((txt = in.readLine()) != null ){
					
						String[] line=txt.split("  ");
						String[] Info=line[1].toString().split(" ");
						LinkedHashMap<Integer,Double> Terms=new LinkedHashMap<Integer,Double>();
						for(int i=0;i<Info.length;i++){
							
							String[] val=Info[i].toString().split(":");
							Terms.put(Integer.valueOf(val[0].toString()),Double.valueOf(val[1]));
						//	System.out.println(line[0] +" " + val[0] + " " + val[1]);
							
						}
						DocTerms.put(Integer.valueOf(line[0]),Terms);
			
						a++;
						}
						} catch (IOException e) {
					
							e.printStackTrace();
						}
						a++;
						return DocTerms;
					}
				
	
	public static LinkedHashMap<Integer,LinkedHashMap<Integer,Double>> GetDocsWithTermsMax() throws FileNotFoundException{
		
		File file = new File("D:/Taxonomie/lyrl2004_vectors_train.dat");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String txt;
			int a=0;
			LinkedHashMap<Integer,LinkedHashMap<Integer,Double>> DocTerms=new LinkedHashMap<Integer,LinkedHashMap<Integer,Double>>();
			try {
				while ((txt = in.readLine()) != null ){
				
					String[] line=txt.split("  ");
					String[] Info=line[1].toString().split(" ");
					LinkedHashMap<Integer,Double> Terms=new LinkedHashMap<Integer,Double>();
					double max =0.044664135988103;
					Integer valeur=0;
					for(int i=0;i<Info.length;i++){
						
						String[] val=Info[i].toString().split(":");
						//System.out.println("Le MAX : " + max);
						
						//System.out.println("New : " + val[1]);
						
						if(Double.valueOf(val[1])>max) {
							max =Double.valueOf(val[1]);
							valeur=Integer.valueOf(val[0]);
							
							
						}
						
						
						
					//	System.out.println(line[0] +" " + val[0] + " " + val[1]);
						Terms.put(Integer.valueOf(val[0]),Double.valueOf(val[1]));
					}
					//Terms.put(valeur,max);
					DocTerms.put(Integer.valueOf(line[0]),Terms);
		
					a++;
					}
					} catch (IOException e) {
				
						e.printStackTrace();
					}
					a++;
					
					return DocTerms;
				}
	
	
	public static LinkedHashMap<String,ArrayList<Integer>> GetTopicsWithDocs() throws IOException{
		
	
	
			File file = new File("D:/Taxonomie/rcv1-v2.topics.qrels");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String txt;
			int a=0;
			LinkedHashMap<String,ArrayList<Integer>> Topics=new LinkedHashMap<String,ArrayList<Integer>>();
			
				while ((txt = in.readLine()) != null  ){
					
					
					String[] line=txt.split(" ");
					if(Topics.containsKey(line[0])){
						Topics.get(line[0]).add(Integer.valueOf(line[1]));
					}else{
						ArrayList<Integer> Docs=new ArrayList<Integer>();
						Docs.add(Integer.valueOf(line[1]));
						Topics.put(line[0],Docs);
					}
					
					a++;
				}
		//	System.out.println(Topics);
				return Topics;
	}
	
	public static ArrayList<String> GetAllTopics() throws IOException{
		File file = new File("D:/Taxonomie/rcv1-v2.topics.qrels");
		BufferedReader in = new BufferedReader(new FileReader(file));
		String txt;
		
		ArrayList<String> Topics=new ArrayList<String>();
		
			while ((txt = in.readLine()) != null  ){
				
				
				String[] line=txt.split(" ");
				if(!Topics.contains(line[0])){
					Topics.add(line[0]);
				}
				}
				
				return Topics;
			
	}
	
	public static LinkedHashMap<String,ArrayList<Integer>> GetTopicsWithTerms() throws IOException {
		
		
		LinkedHashMap<String,ArrayList<Integer>> TermsTopics=new LinkedHashMap<String,ArrayList<Integer>>();
		LinkedHashMap<Integer,LinkedHashMap<Integer,Double>> AllDocs;
		LinkedHashMap<String,ArrayList<Integer>> AllTopicsWithDocs;
		
		AllDocs=GetDocsWithTermsMax();
		AllTopicsWithDocs=GetTopicsWithDocs();
		ArrayList<String> Topics=new ArrayList<String>();
		Topics=GetAllTopics();
		
		
		//System.out.println(Topics.size());
		
		for(int i=0;i<Topics.size();i++){
			//System.out.println(Topics.get(i));
			ArrayList<Integer> docs=new ArrayList<Integer>();
			docs=AllTopicsWithDocs.get(Topics.get(i));
			//System.out.println(docs);
			
			ArrayList<Integer> terms=new ArrayList<Integer>();
			for(int j=0;j<docs.size();j++){
			//	System.out.println(docs.get(j));
				
				
				LinkedHashMap<Integer,Double> termsIndoc=new LinkedHashMap<Integer,Double>();
				termsIndoc=AllDocs.get(docs.get(j));
			//	System.out.println(Topics.get(i)+ " @@ " +docs.get(j)+ "@@ " +termsIndoc);
				//if (termsIndoc==null) continue;
				for (Integer val:termsIndoc.keySet()){
				//	System.out.println(">>> " + val);
					terms.add(val);
					
					//System.out.println(val);
				}
			}
		//	TermsTopics.put(Topics.get(i), terms);
			//System.out.println("///////////");
			//System.out.println(TermsTopics);
			TermsTopics.put(Topics.get(i), terms);
		//	System.out.println(terms.size()) ;
			}
		
	//	System.out.println(TermsTopics.size() );
		return TermsTopics;
	}
	
	
	public static LinkedHashMap<String,String> GetTopicDescription() throws IOException{
		
		File file = new File("D:/Taxonomie/TopicNames.txt");
		BufferedReader in = new BufferedReader(new FileReader(file));
		String txt;
		
		LinkedHashMap<String,String> Topics=new LinkedHashMap<String,String>();
		
			while ((txt = in.readLine()) != null  ){
				String[] info=txt.split("    ");
			//	System.out.println(info[1]);
				String[] topicsName=info[1].split(": ");
				String[] topicDesc=info[2].split(": ");
				Topics.put(topicsName[1], topicDesc[1]);
				
			}
				
		//System.out.println(Topics);
		
		return Topics;
		
	}
	
	public static LinkedHashMap<String, ArrayList<String>> WriteTopicFileToDisk(boolean Topic,boolean Write) throws IOException{
		LinkedHashMap<String,ArrayList<String>> ConceptTerms=new LinkedHashMap<String,ArrayList<String>> ();
		LinkedHashMap<String,ArrayList<Integer>> TermsTopics=Opertaion.GetTopicsWithTerms();
		LinkedHashMap<Integer,String> Terms;
		Terms=Opertaion.ReplaceIdfByTerms();
		String Taxonomie="";
		LinkedHashMap<String,String> Topics=Opertaion.GetTopicDescription();
		String term="";
		FileWriter writer = null;
		if(Write){
		
		writer = new FileWriter("f:/Tax.txt", true);
		}
		
		for(String val :TermsTopics.keySet()){
			if(Write){
			if(Topic){
				
				writer.write(Topics.get(val) + " :  ",0,Topics.get(val).length()+4);
			}
				
				
				
				//Taxonomie =Taxonomie +Topics.get(val) + " : ";
			else{
				writer.write(val + " : ",0,val.length()+3);
			}}
				//Taxonomie =Taxonomie +val + " : ";
				
			
			ArrayList<Integer> docs=TermsTopics.get(val);
			ArrayList<String> terms=new ArrayList<String>();
			for(int i=0;i<docs.size();i++){
				term=Terms.get(docs.get(i));
				terms.add(term);
		//	System.out.println(">>>> " + term);
				//Taxonomie=Taxonomie + " " +term;
				if (Write)
				writer.write(term+ " ",0,term.length()+1);
			}
		//	Taxonomie=Taxonomie+"\n";
			if(Write)
			writer.write("\n",0,1);
			ConceptTerms.put(Topics.get(val), terms);
		}
		
		//writer.write(Taxonomie,0,Taxonomie.length());
		if(Write)
		writer.close();
		System.out.println(ConceptTerms);
		return ConceptTerms;

	}
	
	
	
public static LinkedHashMap<Integer,String> ReplaceIdfByTerms() throws IOException{
		
		
	LinkedHashMap<Integer,String> Terms=new LinkedHashMap<Integer,String>();
	
	
	File file = new File("D:/Taxonomie/stem.termid.idf.map.txt~");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	
	while ((txt = in.readLine()) != null  ){
		String[] val=txt.split(" ");
		
		Terms.put(Integer.valueOf(val[1]),val[0]);
	}

	return Terms;
}

public static LinkedHashMap<String,ArrayList<String>> GetTOPICwithTERMS() throws IOException{
	LinkedHashMap<String,ArrayList<String>> ListTopicTermsDesc=new LinkedHashMap<String,ArrayList<String>>();
	LinkedHashMap<String,ArrayList<Integer>> TermsTopics=Opertaion.GetTopicsWithTerms();
	LinkedHashMap<Integer,String> Terms;
	Terms=Opertaion.ReplaceIdfByTerms();
	String Taxonomie="";
	LinkedHashMap<String,String> Topics=Opertaion.GetTopicDescription();
	String term="";
	for(String val :TermsTopics.keySet()){
		
		ArrayList<String> t=new ArrayList<String>();
		
		
		ArrayList<Integer> docs=TermsTopics.get(val);
		for(int i=0;i<docs.size();i++){
			term=Terms.get(docs.get(i));
		
		t.add(term);
		//Taxonomie=Taxonomie + " " +term;
		}
		ListTopicTermsDesc.put(val, t);
	}
	//System.out.println(ListTopicTermsDesc);
	return ListTopicTermsDesc;
	
	
	
	
}
public static LinkedHashMap<String,ArrayList<String>> GetTermsWithTopics() throws IOException{
	LinkedHashMap<String,ArrayList<String>> TermsTopics=new LinkedHashMap<String,ArrayList<String>>();
	LinkedHashMap<String,ArrayList<String>> TopicTerms=Opertaion.GetTOPICwithTERMS();
	ArrayList<String> terms=new ArrayList<String>();
	ArrayList<String> topics=new ArrayList<String>();
	for( Entry<String, ArrayList<String>> val :TopicTerms.entrySet()){
		
		for(int i=0;i<val.getValue().size();i++){
			
		/*	if(TopicTerms.containsKey(val.getValue().get(i))){
				TopicTerms.get(val.getValue().get(i)).add(val.getKey());
			}else{
				ArrayList<String> topics=new ArrayList<String>();
				topics.add(val.getKey());
				TopicTerms.put(val.getValue().get(i),topics );
			}*/
			if(!terms.contains(val.getValue().get(i)))
			terms.add(val.getValue().get(i));
		}
			
	}
//	System.out.println(terms + " " + terms.size());
	
		for(int j=0;j<terms.size();j++){
		//	System.out.println(terms.get(j));
		for( String valeur :TopicTerms.keySet()){
		//	System.out.println(valeur);
			if(TopicTerms.entrySet().contains(terms.get(j))){
				if(!TermsTopics.containsKey(terms.get(j))){
					//System.out.println(arg0)
					ArrayList<String> l=new ArrayList<String>();
					l.add(valeur);
					TermsTopics.put(terms.get(j), l);
				}else{
					TermsTopics.get(terms.get(j)).add(valeur);
				}
			}
		}}
//	System.out.println(TermsTopics);
	return TermsTopics;
}

public static LinkedHashMap<String,ArrayList<String>> GetTermTopicsFromFileAndWrite(boolean Write) throws IOException{
	LinkedHashMap<String,ArrayList<String>> List=new LinkedHashMap<String,ArrayList<String>>();
	FileWriter writer = null;
	if(Write)
	writer = new FileWriter("f:/NewTax.txt", true);
	File file = new File("f:/Tax.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	
	while ((txt = in.readLine()) != null  ){
		String[] val1=txt.split(":");
		String[] val2=val1[1].split("  ");
		String[] val=val2[1].split(" ");
		
		for(int i=0;i<val.length;i++){
			if(List.containsKey(val[i])){
				if(!List.get(val[i]).contains(val1[0])){
					
					List.get(val[i]).add(val1[0]);
					
				}
			}else{
				ArrayList<String> l=new ArrayList<String>();
				l.add(val1[0]);
				List.put(val[i], l);
			}
		}
		
		
	}
	ArrayList<Integer> a=new ArrayList<Integer>();
	//System.out.println(List.size()+ " " + List);
	int max=0;
	String t="";
	for(String val:List.keySet()){
	//	t=t+val +" : ";
		if(Write)
		writer.write(val + " :",0,val.length()+2);
		ArrayList<String> l=new ArrayList<String>();
		l=List.get(val);
		if(l.size()>max)max=l.size();
		a.add(l.size());
		for(int j=0;j<l.size();j++){
			//t=t+l.get(j)+ ",";
			if(Write)
			writer.write(l.get(j)+",",0,l.get(j).length()+1);
		}
		if(Write)
		writer.write("\n",0,1);
		//t=t+"\n";
	}
	
	if(Write)
	writer.close();
	//System.out.println(a);
	return List;
}

public static LinkedHashMap<String,ArrayList<String>> OptimizeTaxonomie() throws IOException{
	LinkedHashMap<String,HashMap<String,String>> ListConcepts=new LinkedHashMap<String,HashMap<String,String>>();
	LinkedHashMap<String,ArrayList<String>> ListOptimized=new LinkedHashMap<String,ArrayList<String>>();
	LinkedHashMap<String,String> ChildParent=new LinkedHashMap<String,String>();
	LinkedHashMap<String,String> ChildDesc=new LinkedHashMap<String,String>();
	
	File file = new File("D:/Taxonomie/TopicNames.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	while ((txt = in.readLine()) != null  ){
		HashMap<String,String> l=new HashMap<String,String>();
		String[] val=txt.split("    ");
		String[] valParent=val[0].split(": ");
		String[] valChild=val[1].split(": ");
		String[] DescChild=val[2].split(": ");
		l.put(valParent[1], DescChild[1]);
		ListConcepts.put(valChild[1], l);
		ChildParent.put(DescChild[1],valParent[1] );
		ChildDesc.put(valChild[1], DescChild[1]);
	}
	//System.out.println(ListConcepts);
	
	LinkedHashMap<String,ArrayList<String>> List=Opertaion.GetTermTopicsFromFileAndWrite(false);
	System.out.println("888");
	FileWriter writer = null;
	writer = new FileWriter("f:/NewTax2.txt", true);
	
	for(String val:List.keySet()){
		writer.write(val+" :", 0, val.length()+2);
		ArrayList<String>l=List.get(val);
	//	System.out.println(l.contains(ChildDesc.get(ChildParent.get("ECONOMIC_PERFORMANCE"))));
		//System.out.println(ChildDesc.get(ChildParent.get("ECONOMIC_PERFORMANCE")));
	//	System.out.println(l.get(0) +" / "+ ChildDesc.get(ChildParent.get(l.get(0).trim())) +" " +l.contains(ChildDesc.get(ChildParent.get(l.get(0).trim()))+" "));
	
		ArrayList<String> NewList=new ArrayList<String>();
		for(int i=0;i<l.size();i++){
			if(l.size()>15){
				if(!l.contains(ChildDesc.get(ChildParent.get(l.get(i).trim()))+" ")){
					NewList.add(l.get(i));
					//System.out.println(l.get(i));
					writer.write(l.get(i)+",", 0,l.get(i).length()+1);
				}
			}else{
				NewList.add(l.get(i));
				writer.write(l.get(i)+",", 0,l.get(i).length()+1);
			}
			
			
		}
		//System.out.println(l.size()+ " " + NewList.size());
		ListOptimized.put(val, NewList);
		writer.write("\n", 0, 1);
	}
	
	//System.out.println(ChildDesc.get(ChildParent.get("ECONOMIC_PERFORMANCE")));
	return ListOptimized;
	
	
}
}

