# This program on finding the number of candies on given inputs as N as number of candies,k as number of buyers and c's as cost.
# This program uses the mentioned algorithm below:
# First it takes all the costs of candies in a list and reverse sorts it.
# Then it divides the cost of candies list in to the number of buyers and starts computing the sum
# Then it checks if the number of buyers is equal to the number of candies then it calculates the total sum of the candies costs.
# As the condition given (x+1)*ci so everytime it computes one set of buyers the sum is calculated again.
# This is done till all the candies N are there.
import sys
#This try and except is used in case user forgets to enter arguement(in this case input.txt file) or enters wrong arguement.
try:
	f=open(sys.argv[1])
	strfirst=f.read()
	str1=strfirst.split('\n')
#If wrong file entered.
except IOError as e:
	print 'No such file exist:'+sys.argv[1]+'Try again'
	sys.exit(0)
#If wrong file entered which cannot be converted into integer.
except ValueError:
	print "Could not convert data to an integer."
#If no file entered.
except:
	print "No Input detected,Unexpected error:",sys.exc_info()[0]
	sys.exit(0)
	raise

#Extracting inputs from file into temporary variables as string,this is for the first line. 
temp2=str1[0].split()

#This converts the first line in the input file into integer and stores into next temp variable.
temp1=[int(i) for i in temp2]

#This takes second line as string and splits each cost into a list of costs but its still string.
c1=str1[1].split()

#This takes the string and converts the cost into integer lists.
costofCandies=[int(i) for i in c1]

#This arranges the list in decending order.
costofCandies.sort(reverse=True)
 
 
# It prepares the output file for writing the output. 
f=open('output.txt','w')

#storing of First line input into variable for ease of calculation.
numberofCandies=int(temp1[1])

#variable for number of buyers
k=int(temp1[0])

#according to the algorithms first step
if numberofCandies == k:
       sum_totalcost_Candies=sum(costofCandies)
       f.write(str(sum_totalcost_Candies))
else:
       number_of_times=1	#variable to check if the buyer is coming for the next time to buy the candies.
       sum_totalcost_Candies=0
	#for loop iteration i,j
       i=0
       j=0
	#to keep track of people already bought
       already_bought=k	

       while(i < numberofCandies):
             
              while(j < already_bought):
                     sum_totalcost_Candies=sum_totalcost_Candies + (number_of_times * costofCandies[j])
                     j=j+1
                                  
              number_of_times=number_of_times+1
              i=i+k

              if (numberofCandies-i) < k:
                     already_bought=already_bought+(numberofCandies-i)
                     j=i
              else:
                     already_bought=already_bought+k
                     j=i
 
       f.write(str(sum_totalcost_Candies))
