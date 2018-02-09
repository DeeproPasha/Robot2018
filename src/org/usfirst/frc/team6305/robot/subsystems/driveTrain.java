package org.usfirst.frc.team6305.robot.subsystems;

import org.usfirst.frc.team6305.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class driveTrain extends Subsystem {
	
	public static tankDrive instance = new tankDrive();
	
	Spark frontLeft = new Spark(RobotMap.frontLeft);
	Spark frontRight = new Spark(RobotMap.frontRight);
	Spark backLeft = new Spark(RobotMap.backLeft);
	Spark backRight = new Spark(RobotMap.backRight);

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void drive(double leftSpeed, double rightSpeed) {
    	frontRight.set(-leftSpeed);
		backRight.set(-leftSpeed);
		
		frontLeft.set(-rightSpeed);
		backLeft.set(-rightSpeed);    	
    }
    
    public static tankDrive getInstance() {
    	return instance;
    }
}

