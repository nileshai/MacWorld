package arithmetic;

import java.io.IOException;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Variance {
	private static int col_key=7;
	private static double sum=0, len=0;
	
	public static class VarianceMap extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> 
		
		{
		
		@Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String lines = value.toString();
         String []lineArr = lines.split("\t");
         double StockVolume=Double.valueOf(lineArr[col_key]);
         context.write(new IntWritable(1),new DoubleWritable(StockVolume));
        }
     }





public static class VarianceReduce extends Reducer<IntWritable, DoubleWritable, NullWritable, DoubleWritable>
{
     public void reduce(IntWritable key, Iterable<DoubleWritable> values, Context context ) throws IOException, InterruptedException
     {

    	 String a;
    	 for(DoubleWritable value : values)
         {
               len++;
               sum += value.get();
                            
               
         }
    	 double avg=sum/len;
    	// double total=1000.0;
    	 double temp=0.0;
           
            for(DoubleWritable value : values)
            {
                  a=value.toString();
                 double b=Double.parseDouble(a);
                 temp = Double.valueOf(avg)-Double.valueOf(b);
                  //total+=((Double.valueOf(a)-Double.valueOf(avg)*(Double.valueOf(a)-Double.valueOf(avg))));
                  //total=total+(Double.valueOf(b)-Double.valueOf(avg));
                 //total = total + Math.pow(temp,2);
                 
                // context.write(NullWritable.get(), new DoubleWritable(Double.valueOf(temp)));             
                  
            }
            context.write(NullWritable.get(), new DoubleWritable(Double.valueOf(temp)));
            
            

		}	
	}


	public static void main(String args[]) throws Exception
	{
		@Deprecated
		Job job = new Job();
		job.setJarByClass(Variance.class);
		
		FileInputFormat.addInputPath(job, new Path(args [0]));
		FileOutputFormat.setOutputPath(job, new Path(args [1]));
		
		
		
		job.setMapperClass(VarianceMap.class);
		//job.setCombinerClass(WordCountCombiner.class);
		job.setReducerClass(VarianceReduce.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}
	}

