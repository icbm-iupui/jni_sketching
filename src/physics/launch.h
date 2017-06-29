#ifndef PI
#define PI 3.14159265358
#endif
#ifndef LAUNCH_H
#define LAUNCH_H
#include <cmath>
class Launcher{
	private:
                double speed;
                double vX;
                double vY;
                double angle;
                double gravity;
        public:
                Launcher(double s, double g, double a);
		void setSpeed(double s){
                        speed = s;
                }
                double getSpeed(){
                        return speed;
                }
                void setAngle(double a){
                        angle = a;
                }
                double getAngle(){
                        return d2R(angle);
                }
                void setGravity(double g){
                        gravity = g;
                }
                double getGravity(){
                        return gravity;
                }
                double getVX();
                double getVY();
		double d2R(double d);
};
#endif
