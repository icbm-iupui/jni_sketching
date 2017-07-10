all:launcher.dll

launcher.dll: launcher.o launch_wrap.o
	g++ -fpic -shared launcher.o launch_wrap.o -o launcher.dll

launcher.o: launcher.cpp launch.h
	g++ -c -fpic launcher.cpp

launch_wrap.o: launch_wrap.cxx launch.h
	g++ -I"C:\Program Files\Java\jdk1.8.0_65\include" -I"C:\Program Files\Java\jdk1.8.0_65\include\win32" -c -fpic launch_wrap.cxx

launch_wrap.cxx: launch.i launch.h
	swig -java -c++ -package physics launch.i

clean:
	rm launcher.dll launch*.java Launch*.java


