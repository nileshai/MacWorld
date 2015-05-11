"""
This program is suppose to find the common predecessor before the the given values in two different String.
Algorithm for the above solution is below
1)First is to check if the given characters are the first character of the given list if yes then that is the answer.
2)Then from one of the String it starts from the element before the given element in both arrays and finds if the same element exist in the next String.
3)If there is no match found then it displays no common predecessor.
"""
import sys
try:
#This try and except is used in case user forgets to enter arguement(in this case input.txt file) or enters wrong arguement.
	f=open(sys.argv[1])
	strt1=f.read()
#If wrong file entered.
except IOError as e:
	print 'No such file exist:'+sys.argv[1]+'Try again'
	sys.exit(0)
#If no file entered.
except:
	print "No input found,Unexpected error:",sys.exc_info()[0]
	sys.exit(0)
	raise

#this will split the input into different line by line.
s1=strt1.split('\n')
#From the first line it will split entire string into characters
str1=s1[0].split(',')
#From the second line it will split entire string into characters
str2=s1[1].split(',')
#From the third line it will split entire string into characters
str3=s1[2].split(',')
 
f=open('output.txt','w')
#taking characters for which predecessor is to be found.
ch1=str3[0]
ch2=str3[1]
#finding index of characters in their respective Strings
n1=str1.index(ch1)
n2=str2.index(ch2)
 
if (ch1 == ch2) and (ch1 == str2[0]) and (ch2 == str1[0]):
       f.write('Common Predecessor:'+ch1)
       sys.exit(0)
 
num=0
j=1
while(n2-j >= 0):
       tch=str2[n2-j]
       i=1
       while(n1-i>=0):
              if tch in str1[n1-i]:
                     num=1
                     break
              i=i+1
 
       if num != 0:
              break
       j=j+1
 
if num != 0:
       f.write('Common Predecessor:'+tch)
else:
       f.write('No Common Predecessor')