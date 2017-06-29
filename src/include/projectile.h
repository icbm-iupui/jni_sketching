#ifndefine
#define
class projectile{
        public:
                void setResistance(double air){
                        resist = air;
                }
                double getResistance(){
                        return resist;
                }
                void setMass(double m){
                        mass = m;
                }
                double getMass(){
                        return mass;
                }
        private:
                double mass;
                double resist;

};

#endif
