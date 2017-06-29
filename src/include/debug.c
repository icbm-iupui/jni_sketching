#include <math.h>
#include <stdio.h>
double d2r(int);

int main(){
	printf("%f\n", pow(50*sin(d2r(45)),2)/9.8);
	printf("%f\n", pow(50*sin(d2r(45)),2));
	printf("%f\n", 50*sin(d2r(45)));
	printf("%f\n", d2r(45));
}

double d2r(int d){
	return (d / 180.0) * M_PI;
}
