/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.event.*;
/**
 *
 * @author andmcnut
 */
public class Physics {
    JFrame mainW = new JFrame();
    JPanel bottom = new JPanel();
    JPanel graphics = new JPanel();
    JButton enter = new JButton("Enter");
    JButton clear = new JButton("Clear");
    JSlider[] chooseVal = new JSlider[3];
    JLabel[] label = new JLabel[3];
    String[] labelTxt = {"Speed: ", "Degrees: ", "Gravity: "};
    Font font = new Font("Times new Roman", Font.BOLD, 14);
    double degree = 45;
    double speed = 50;
    int height, width;
    double gravity = 1;
    Trajectory display = new Trajectory();
    
    
    Physics(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainW.setSize((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
        mainW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainW.setResizable(false);
        
        enter.setFont(font);
        enter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                computeTrajectory();
            }
        });
        clear.setFont(font);
        clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                display.clear();
                mainW.revalidate();
                mainW.repaint();
            }
        });
        
        for(int i = 0; i < 3; i++){
            label[i] = new JLabel();
            label[i].setFont(font);
        }
        label[1].setText(labelTxt[1] + degree);
        label[0].setText(labelTxt[0] + speed);
        label[2].setText(labelTxt[2] + gravity + " g");
            
        {
            chooseVal[0] = new JSlider(JSlider.HORIZONTAL, 0, 90, 45);
            chooseVal[0].addChangeListener(new ChangeListener(){
               public void stateChanged(ChangeEvent ce){
                    degree = ((JSlider)ce.getSource()).getValue();
                    label[1].setText(labelTxt[1] + degree);
               } 
            });
            chooseVal[1] = new JSlider(JSlider.HORIZONTAL);
            chooseVal[1].addChangeListener(new ChangeListener(){
               public void stateChanged(ChangeEvent ce){
                    speed = (int)((JSlider)ce.getSource()).getValue();
                   //makes the range within the display
                    label[0].setText(labelTxt[0] + speed);
               } 
            });
            chooseVal[2] = new JSlider(JSlider.HORIZONTAL, 0, 10, 1);
            chooseVal[2].addChangeListener(new ChangeListener(){
               public void stateChanged(ChangeEvent ce){
                    gravity = ((JSlider)ce.getSource()).getValue();
                    label[2].setText(labelTxt[2] + gravity + " g");
//                    gun.setGravity(gravity * 9.8);
               } 
            });
        }
        
        display.setPreferredSize(mainW.getSize());
       
        bottom.setLayout(new GridLayout(2,4));
        bottom.add(chooseVal[0]);
        bottom.add(chooseVal[1]);
        bottom.add(chooseVal[2]);
        bottom.add(enter);
        bottom.add(label[1], BorderLayout.CENTER);
        bottom.add(label[0], BorderLayout.CENTER);
        bottom.add(label[2], BorderLayout.CENTER);
        bottom.add(clear);
        mainW.add(bottom, BorderLayout.SOUTH);
        mainW.add(display);
        
        mainW.setVisible(true);
        Dimension size = new Dimension(display.getSize());
        height = (int)size.getHeight();
        width = (int)size.getWidth();
        
//        System.out.println("Windows made...  Waiting for Thread...");
//        
//        
//        new Thread(new Runnable() {
//        
//        @Override
//        public void run() {
//            gun = new Launcher((speed / 100.0 * Math.sqrt(width * (9.8)/2) / (Math.sin(Math.PI / 4))), gravity * 9.8, degree);
//        }
//        }).start();
//        //gun = new Launcher((speed / 100.0 * Math.sqrt(width * (9.8)/2) / (Math.sin(Math.PI / 4))), gravity * 9.8, degree);
        
        display.setHW(height, width);
        
            
    }
    
    public void computeTrajectory(){
        Launcher gun = new Launcher((speed / 100.0 * Math.sqrt(width * (9.8)/2) / (Math.sin(Math.PI / 4))), gravity * 9.8, degree);
        display.calcTraj(gun.getSpeed(), gun.getAngle(), gun.getGravity(), gun.getVX(), gun.getVY());
        display.repaint();
        mainW.revalidate();
        mainW.repaint();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            System.load("C:\\Users\\andmcnut\\Documents\\NetBeansProjects\\Physics\\src\\physics\\launcher.dll");
            Physics start = new Physics();
        }
       catch (NullPointerException e){
                 System.out.println("What woo..");
                        }
    }
    
  
   
    
}

class Trajectory extends JPanel{
    double timeStep;
    double flyTime;
    private Point curr;
    private double curTime;  
    List<Point> flight; //Path of the given init_veloc, accel, and angle
    Image image;
    Graphics2D graphics;
    int height_, width_;
    
    Trajectory(){
        flight = new ArrayList<>();
        curr = new Point(0,0);
    }
    
    static{
        System.load("C:\\Users\\andmcnut\\Documents\\NetBeansProjects\\Physics\\src\\physics.dll");
    }
    //private native double getXVel(int speed, double degrees);
    //private native double getYVel(int speed, double degrees);
    //private native double getRad(int degrees);
    private native Point getNextP(double vX, double vY, double accel, double timeStep);
    private native double getFlyTime(double vel, double accel, double angle);
    
    public List<Point> getTraj(){
        return this.flight;
    }
    
    public void setHW(int height, int width){
        this.height_ = height;
        this.width_ = width;
    }
    
    /* Draws the lines for the different trajectories*/
    public void drawLines(int height, int width){
        int flightS = flight.size();

        for(int i = 1; i < flightS; i++){
            graphics.drawLine((int)(flight.get(i-1).getX()), height - (int)flight.get(i-1).getY(),(int)(flight.get(i).getX()), height - (int)flight.get(i).getY());
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(image == null){
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D)image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setBackground(Color.WHITE);
            g.clearRect(0, 0, width_, height_);
        }
        g.drawImage(image, 0, 0, null);

        
    }
    
    /*Clears the screen of all currently displayed Trajectories*/
    public void clear(){
        this.repaint();
        graphics.clearRect(0,0,width_, height_);
        flight.clear();
    }
    
    /*Calculates the trajectory based on a given speed, radian, acceleration, vX and vY
        Puts the trajectory into flight as points*/
    public void calcTraj(double speed, double rad, double gravity, double vX, double vY){
        flight.clear();
        
        flyTime = getFlyTime(speed, gravity, rad);

        timeStep = flyTime / 100;
        System.out.println(vX + " " + vY + " " + flyTime + " " + timeStep);
        for(curTime = 0; curTime < flyTime; curTime += timeStep){
            //System.out.println(getNextP(vX, vY, gravity, this.curTime));
            this.flight.add(getNextP(vX, vY, gravity, this.curTime));
        }
        this.repaint();
        drawLines(height_, width_);
    }
    
    /*Used if the native program is not used*/
    /*private Point getNextP(double vX, double vY, double accel, double timeStep){
        double x,y;
        x = vX * timeStep;
        y = -0.5 * accel * Math.pow(timeStep, 2) + vY * timeStep;
        return (new Point((int)x, (int) y));
    }*/
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2 = (Graphics2D)g.create();
        graphics2.setBackground(Color.WHITE);
        graphics2.setPaint(Color.BLACK);
        drawLines(height_, width_);
    }
    
    public void setsSize(int dim1, int dim2){
        setPreferredSize(new Dimension(dim1, dim2));
    }
}



/*Controls a Native Object:Launcher
    Variables: speed, angle, gravity, velocity_x, velocity_y*/
/*class Launcher{
    static{
        //System.loadLibrary("include.launcher");
        System.load("C:\\Users\\andmcnut\\Documents\\NetBeansProjects\\Physics\\src\\launcher.dll");
        
    }
    private long ptr;
    
    private native long createLaunch(double sp, double gr, double an);
    private native void destroyLaunch(long ptr);
    private native void setSpeedC(long ptr, double speed);
    private native double getSpeedC(long ptr);
    private native void setAngleC(long ptr, double angle);
    private native double getAngleC(long ptr);
    private native void setGravityC(long ptr, double grav);
    private native double getGravityC(long ptr);
    private native double getVXC(long ptr);
    private native double getVYC(long ptr);
    
    Launcher(double s, double g, double a){
        ptr = createLaunch(s,g,a);
    }
    
    public void destroy(){
        destroyLaunch(ptr);
    }
    
    public void setSpeed(double sp){
        setSpeedC(ptr, sp);
    }
    
    public double getSpeed(){
        return getSpeedC(ptr);
    }
    
    public void setAngle(double a){
        setAngleC(ptr, a);
    }
    
    public double getAngle(){
        return getAngleC(ptr);
    }
    
    public void setGravity(double g){
        setGravityC(ptr, g);
    }
    
    public double getGravity(){
        return getGravityC(ptr);
    }
    
    public double getVX(){
        return getVXC(ptr);
    }
    
    public double getVY(){
        return getVYC(ptr);
    }
            
}*/
