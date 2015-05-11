"""
This program is suppose to find longest subsequent sub array following the condition that,
number of zeroes should be equal to number of ones.

I did this by using the following algorithm in steps:
1)First convert all zeroes to -1.
2)Start by adding each element from the input array in another array and keep a check when the sum of the values becomes zero.
3)keep a track with a variable of the longest subsequent array whose sum is zero.
4)Once you get the longest subsequent array convert back all the -1's to 0's."""


import sys
try:
#This try and except is used in case user forgets to enter arguement(in this case input.txt file) or enters wrong arguement.
	f=open(sys.argv[1])
	strfirst=f.readline()
	str=strfirst.split('\n')

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



#Converting the input string into int
Input_array=[int(i) for i in str[0]]
i=0 #loop iterating variable
length_of_input=len(Input_array)

while (i < length_of_input):
	if ( Input_array[i] == 0):
		Input_array[i]=-1
	i=i+1


j=0  #loop iterating variable
longest_sub_array=[]
count_of_subarray=0
maximum_count=0

while(j< length_of_input):
	i=j
	while(i <length_of_input):
		longest_sub_array.append(Input_array[i])
		if(sum(longest_sub_array) == 0):
			count_of_subarray=count_of_subarray+1
			if (len(longest_sub_array) > maximum_count):
				maximum_count=len(longest_sub_array)	
		i=i+1
	j=j+1
	longest_sub_array=[]

j=0
count_of_subarray=0
temp=[]
f=open('output.txt','w')
while(j< length_of_input):
        i=j
        while(i <length_of_input):
                longest_sub_array.append(Input_array[i])
                if(sum(longest_sub_array) == 0):
                        count_of_subarray=count_of_subarray+1
                        if (len(longest_sub_array) ==  maximum_count):
                        	temp= longest_sub_array
				z=0
				while(z < len(temp)):
					if (temp[z] == -1):
						temp[z]=0
					z=z+1
				f.writelines(["%s" % item  for item in temp])
				sys.exit()
                i=i+1
        j=j+1
        longest_sub_array=[]
