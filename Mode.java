package arithmetic;

import java.io.IOException;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Mode {
	private static int col_key=7;
	
	public static class ModeMap extends Mapper<LongWritable, Text, IntWritable, IntWritable> 
		
		{
		/*public void setup(Context context) throws IOException,
		InterruptedException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			col_key=Integer.parseInt(reader.readLine());
			
		}*/
		@Override
		  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		   {
			String lines = value.toString();
		    String []lineArr = lines.split("\t");
		    int StockVolume=Integer.parseInt(lineArr[col_key]);
		    context.write(new IntWritable(StockVolume),new IntWritable(1));
		   }
		}





	public static class ModeReduce extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>
	{
		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context ) throws IOException, InterruptedException
		{
			int sum=0;
			for(IntWritable value : values)
			{
				sum += value.get();
				
			}
			context.write(key, new IntWritable(sum));
		}	
	}


	public static void main(String args[]) throws Exception
	{
		@Deprecated
		Job job = new Job();
		job.setJarByClass(Mode.class);
		
		FileInputFormat.addInputPath(job, new Path(args [0]));
		FileOutputFormat.setOutputPath(job, new Path(args [1]));
		
		
		
		job.setMapperClass(ModeMap.class);
		//job.setCombinerClass(WordCountCombiner.class);
		job.setReducerClass(ModeReduce.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
	}

