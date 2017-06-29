/* File: launch.i*/
%module launch
%{
#include "launch.h"
%}
class Launcher{
        public:
                Launcher(double, double, double);
                double getSpeed();
                double getGravity();
                double getVX();
                double getVY();
                double getAngle();
};
