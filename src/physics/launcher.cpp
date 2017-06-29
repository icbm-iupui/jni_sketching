#include "launch.h"

Launcher::Launcher(double s, double g, double a){
	speed = s;
	gravity = g;
	angle = a;
	vX = getVX();
	vY = getVY();
}
double Launcher::getVX(){
	vX = speed * std::cos(d2R(angle));
	return vX;
}
double Launcher::getVY(){
	vY = speed * std::sin(d2R(angle));
	return vY;
}

double Launcher::d2R(double deg){
	return deg / 180 * PI;
}

/*JNIEXPORT jlong JNICALL Java_physics_Launcher_createLaunch(JNIEnv *env, jobject thisObj, jdouble s, jdouble g, jdouble a){	
	return reinterpret_cast<jlong>(new Launcher(s, g, a));
}

JNIEXPORT void JNICALL Java_physics_Launcher_destroyLaunch(JNIEnv *env, jobject thisObj, jlong ptr){
	Launcher *a = reinterpret_cast<Launcher*>(ptr);
	if(a){
		delete a;
	}
}

JNIEXPORT void JNICALL Java_physics_Launcher_setSpeedC(JNIEnv *env, jobject thisObj, jlong ptr, jdouble speed){
	reinterpret_cast<Launcher*>(ptr)->setSpeed((double)speed);
}

JNIEXPORT jdouble JNICALL Java_physics_Launcher_getSpeedC(JNIEnv *env, jobject thisObj, jlong ptr){
	return (reinterpret_cast<Launcher*>(ptr)->getSpeed());
}

JNIEXPORT void JNICALL Java_physics_Launcher_setAngleC(JNIEnv *env, jobject thisObj, jlong ptr, jdouble angle){
	reinterpret_cast<Launcher*>(ptr)->setAngle((double)angle);
}

JNIEXPORT jdouble JNICALL Java_physics_Launcher_getAngleC(JNIEnv *env, jobject thisObj, jlong ptr){
	return (reinterpret_cast<Launcher*>(ptr)->getAngle());
}

JNIEXPORT void JNICALL Java_physics_Launcher_setGravityC(JNIEnv *env, jobject thisObj, jlong ptr, jdouble grav){
	reinterpret_cast<Launcher*>(ptr)->setGravity((double)grav);
}

JNIEXPORT jdouble JNICALL Java_physics_Launcher_getGravityC(JNIEnv *env, jobject thisObj, jlong ptr){
	return (reinterpret_cast<Launcher*>(ptr)->getGravity());
}

JNIEXPORT jdouble JNICALL Java_physics_Launcher_getVXC(JNIEnv *env, jobject thisObj, jlong ptr){
	return (reinterpret_cast<Launcher*>(ptr)->getVX());
}

JNIEXPORT jdouble JNICALL Java_physics_Launcher_getVYC(JNIEnv *env, jobject thisObj, jlong ptr){
	return (reinterpret_cast<Launcher*>(ptr)->getVY());
}*/
