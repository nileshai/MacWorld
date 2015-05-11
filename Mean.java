package arithmetic;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Mean {
	
	static public int col_key=0; 

	public static class MeanMap extends Mapper<LongWritable, Text, IntWritable, IntWritable> 
		
		{
		private Configuration conf;

		@Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String lines = value.toString();
         String []lineArr = lines.split("\t");
         
         conf = context.getConfiguration();
         col_key=conf.getInt("key",0);

         int StockVolume=Integer.parseInt(lineArr[col_key]);
         context.write(new IntWritable(1),new IntWritable(StockVolume));
        }
     }





public static class MeanReduce extends Reducer<IntWritable, IntWritable, IntWritable, FloatWritable>
{
     public void reduce(IntWritable key, Iterable<IntWritable> values, Context context ) throws IOException, InterruptedException
     {

            int sum=0,i=0;
            
            for(IntWritable value : values)
            {
            	sum += value.get();
            	i++;
                               
                  
            }
            
            float mean=(float)sum/i;
            context.write(key, new FloatWritable(Float.valueOf(mean)));

		}	
	}


	public static void main(String args[]) throws Exception
	{
		@Deprecated
		Job job = new Job();
		job.setJarByClass(Mean.class);
		
		FileInputFormat.addInputPath(job, new Path(args [0]));
		FileOutputFormat.setOutputPath(job, new Path(args [1]));
		
		//Configuration conf = new Configuration();
		int column_key =Integer.parseInt(args[2]);

		job.getConfiguration().setInt("key",column_key);
		job.setMapperClass(MeanMap.class);
		//job.setCombinerClass(WordCountCombiner.class);
		job.setReducerClass(MeanReduce.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(FloatWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
	}

