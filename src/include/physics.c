#define _USE_MATH_DEFINES // for C

#include <jni.h>
#include "physics_Physics.h"
//#include "physics_Trajectory.h"
#include <math.h>
//using namespace std;

double d2r(int);
double computeYPos(double velY, double grav, double tStep);
double velocity(int speed, double rad, int xORy);
double getTime(double yVel, double grav);

JNIEXPORT jdouble JNICALL Java_physics_Trajectory_getFlyTime (JNIEnv *env, jobject thisObj, jdouble sp, jdouble g, jdouble angle){
	return pow(sp,2) * sin(2 * angle) / g / (sp * cos(angle));
}


JNIEXPORT jdouble JNICALL Java_physics_Physics_computeTime (JNIEnv *env, jobject thisObj, jint inJDeg, jint inJSp){
	jdouble answer = inJSp * 2 * sin(d2r((int)inJDeg)) / 9.8;
	return answer;
  }

double d2r(int deg){
	return (deg/180.0) * ( M_PI);
}

JNIEXPORT jdouble JNICALL Java_physics_Trajectory_getXVel (JNIEnv *env, jobject thisObj, jint speed, jdouble rad){
	return speed * cos(rad);
}

JNIEXPORT jdouble JNICALL Java_physics_Trajectory_getYVel (JNIEnv *env, jobject thisObj, jint speed, jdouble rad){
	return speed * sin(rad);
}

JNIEXPORT jdouble JNICALL Java_physics_Trajectory_getRad (JNIEnv *env, jobject thisObj, jint deg){
	return d2r(deg);
}

JNIEXPORT jobject JNICALL Java_physics_Trajectory_getNextP (JNIEnv *env, jobject thisObj, jdouble vX, jdouble vY, jdouble g, jdouble t){
	jclass point = (*env)->FindClass(env, "java/awt/Point");

	jdouble x, y;
	x = vX * t;
	y = computeYPos(vY, g, t);
	
	//printf("%f, %f\n", x, y);
	jmethodID initP = (*env)->GetMethodID(env, point, "<init>", "(II)V");
	jobject newP = (*env)->NewObject(env,point,initP, (jint)x, (jint)y);

	return newP;
}

double computeYPos(double velY, double grav, double tStep){
	return -0.5 * grav * tStep * tStep + velY * tStep;
}

JNIEXPORT jobject JNICALL Java_physics_Trajectory_calcTrajC(JNIEnv *env, jobject thisObj, jint speed, jint degrees, jdouble gravity){
	jclass point = (*env)->FindClass(env, "java/awt/Point");
	jclass list = (*env)->FindClass(env, "java/util/ArrayList");
	jmethodID listInit = (*env)->GetMethodID(env, list, "<init>", "(I)V");
	jmethodID pointInit = (*env)->GetMethodID(env, point, "<init>", "(II)V");
	jmethodID pointInitP = (*env)->GetMethodID(env, point, "<init>", "(Ljava/awt/Point)V");
	
	jmethodID pointSet = (*env)->GetMethodID(env, point, "setLocation", "(DD)V");
	jmethodID listAdd = (*env)->GetMethodID(env, list, "add", "(Ljava/lang/Object;)V");

	jobject newList = (*env)->NewObject(env, list, listInit, 100);
	jobject curPoint = (*env)->NewObject(env, point, pointInit, 0 ,0);
	(*env)->CallVoidMethod(env, newList, listAdd, curPoint);

	double rad = d2r(degrees);
	double xVel = velocity(speed, rad, 1);
	double yVel = velocity(speed, rad, 0);
	double totalTime = getTime(yVel, gravity);		
	double timeStep = totalTime / 100;
	double curTime;

	for(curTime = 0; curTime <= totalTime; curTime += timeStep){
		double xPos = xVel * curTime;
		double yPos = computeYPos(yVel, gravity, curTime);
		(*env)->CallVoidMethod(env, curPoint, pointSet, xPos, yPos);
		jobject temp = (*env)->NewObject(env, point, pointInitP, curPoint);
		(*env)->CallVoidMethod(env, newList, listAdd, temp);
	}
		

	return newList;	
}

double velocity(int speed, double rad, int xORy){
	if(xORy == 1)
		return cos(rad) * speed;
	else if(xORy == 0)
		return sin(rad) * speed;
}

double getTime(double yVel, double grav){
	return 2 * yVel / grav;
}
