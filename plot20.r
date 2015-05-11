#Dynamic graph stating any input csv file it will convert the output with some #mathematical functions.It will give output every 20 sec and plot the points dynamically.



arrayof20<-function(array_name, new_row){
  i<-1
  j<-nrow(array_name)
  if(j<20){
    array_name<-rbind(array_name,new_row)
  }
  else{
    array_name<-rbind(array_name,new_row)
    array_name<-array_name[-i,]
  }
  return(array_name)
}

accessdata <- function(datafile){
tempdata <- as.data.frame(NULL)
i <- 1


x <- read.csv(datafile,sep=",",head=TRUE)

temp <- tail(x,n=20L)

while (i < 21)
{

#Taking inputs from the file
Time.Stamp<-temp[i,1]
DMC.Level.PV_M6<-temp[i,22]
Mass_DMC_M6<-temp[i,37]
DMC.Density.PV_M6<-temp[i,18]
Stock.Level.PV<-temp[i,27]

#Calculations
Vol_DMC_M6<-pi*2.6*(DMC.Level.PV_M6/100)*(0.5^2+0.5*(0.5+2.6*(DMC.Level.PV_M6/100)+(2.6*(DMC.Level.PV_M6/100))^2))/3
phi_DMC_M6<-5.15*(DMC.Density.PV_M6-1)/(DMC.Density.PV_M6*(4.15))
Mag_mass_DMC_M6<-phi_DMC_M6*Mass_DMC_M6
if(Stock.Level.PV <= 100)
  {
    vol_stock <- 6.948*((Stock.Level.PV/100)^3)+11.578*((Stock.Level.PV/100)^2)+6.432*(Stock.Level.PV/100)
  } 
else {
    vol_stock <- 24.9548+ 4.7712*((Stock.Level.PV-100)/100)
  }


#section for plotting graph

tim <- strftime(strptime(Time.Stamp, format="%m/%d/%Y %H:%M", tz = ""),format="%H:%M",tz="GMT")
plotdata <- data.frame(tim,round(vol_stock,2))


#last 20 values at any moment
tempdata <- arrayof20(tempdata,plotdata)
#print(tempdata)
i <- i +1
}

plot.ts(as.ts(tempdata[2]),main="Live Plot",xlab="Time Series",ylab="Vol_Stock",xaxt='n')
axis(1,at=1:20,label=tempdata[,1])
#axis(2,at=1:20,label=tempdata[,2])
#again reading file stream
repeat{
t1 <- Sys.time()
x <- read.csv(datafile,sep=",",head=TRUE)

temp <- tail(x,n=1L)

#Taking inputs from the file
Time.Stamp<-temp[1,1]
DMC.Level.PV_M6<-temp[1,22]
Mass_DMC_M6<-temp[1,37]
DMC.Density.PV_M6<-temp[1,18]
Stock.Level.PV<-temp[1,27]

#Calculations
Vol_DMC_M6<-pi*2.6*(DMC.Level.PV_M6/100)*(0.5^2+0.5*(0.5+2.6*(DMC.Level.PV_M6/100)+(2.6*(DMC.Level.PV_M6/100))^2))/3
phi_DMC_M6<-5.15*(DMC.Density.PV_M6-1)/(DMC.Density.PV_M6*(4.15))
Mag_mass_DMC_M6<-phi_DMC_M6*Mass_DMC_M6
if(Stock.Level.PV <= 100)
  {
    vol_stock <- 6.948*((Stock.Level.PV/100)^3)+11.578*((Stock.Level.PV/100)^2)+6.432*(Stock.Level.PV/100)
  } 
else {
    vol_stock <- 24.9548+ 4.7712*((Stock.Level.PV-100)/100)
  }


#section for plotting graph

tim <- strftime(strptime(Time.Stamp, format="%m/%d/%Y %H:%M", tz = ""),format="%H:%M",tz="GMT")
plotdata <- data.frame(tim,round(vol_stock,2))


#last 20 values at any moment
tempdata <- arrayof20(tempdata,plotdata)
#print(tempdata)
plot.ts(as.ts(tempdata[2]),main="Live Plot",xlab="Time Series",ylab="Vol_Stock",xaxt='n')
axis(1,at=1:20,label=tempdata[,1])
#axis(2,at=1:20,label=tempdata[,2])

t2<-Sys.time()
dt <- t2-t1
Sys.sleep(20-dt)
t3 <-Sys.time()
dt2 <- t3-t1
print(dt2)
}





}