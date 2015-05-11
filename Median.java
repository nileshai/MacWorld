package arithmetic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Median {
	private static int col_key=7;
	public static ArrayList<Integer> a1=new ArrayList<Integer>();
	public static int arrlen;
	
	public static class MedianMap extends Mapper<LongWritable, Text, IntWritable, Text> 
		
		{

		@Override
		  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		   {
			String lines = value.toString();
		    String []lineArr = lines.split("\t");
		    
		  
		    
		    
		   String StockVolume=lineArr[col_key];
		    
		    
		    
		   context.write(new IntWritable(1),new Text(StockVolume));
		   }
		}





public static class MedianReduce extends Reducer<IntWritable, Text, IntWritable, IntWritable>
	{
		public void reduce(IntWritable key, Iterable<Text> values, Context context ) throws IOException, InterruptedException
		{
			for(Text value : values)
			{
				
				String v=value.toString();
				int t=Integer.parseInt(v);
				a1.add(t);
			}
		    Collections.sort(a1);
		    
			
			arrlen=a1.size();
			int index=((arrlen+1)/2);
			int temp=0;
			
			if(arrlen%2==0)	//arrlen is even
			{
				  temp=(a1.get(index)+a1.get(index-1))/2; 
			}
			else
				temp=a1.get(index-1);
			
			context.write(key, new IntWritable(temp));
		}	
	}


	public static void main(String args[]) throws Exception
	{
		@Deprecated
		Job job = new Job();
		job.setJarByClass(Median.class);
		
		FileInputFormat.addInputPath(job, new Path(args [0]));
		FileOutputFormat.setOutputPath(job, new Path(args [1]));
		
		
		
		job.setMapperClass(MedianMap.class);
		//job.setCombinerClass(WordCountCombiner.class);
		job.setReducerClass(MedianReduce.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
	}


