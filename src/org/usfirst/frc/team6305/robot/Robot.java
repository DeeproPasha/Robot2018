/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6305.robot;



import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
/**
 * IterativeRobot skeleton code for Team 6305
 * @author Deepro Pasha
 *
 */
public class Robot extends IterativeRobot {
	//Wheels
	//Didn't actually know what wheel ports were so just randomly assigned them
	Spark frontLeft = new Spark(0);
	Spark frontRight = new Spark(3);
	Spark backLeft = new Spark(1);
	Spark backRight = new Spark(4);
	//intakeClaw
	Spark intakeClaw = new Spark(7);
	//fireman's ladder
	Spark firemanLadder = new Spark(5);
	//Joysticks for tankDrive
	Joystick jStickL = new Joystick(0);
	Joystick jStickR = new Joystick(1);
	//CoDriver controller
	XboxController xbox = new XboxController(2);
	//Timer for autonomous
	Timer time = new Timer();
	//Gyro for autonomous, so it goes straight
	
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	//Encoder enc = new Encoder(0,1,false,Encoder.EncodingType.k4x)
	//Blinkin is set up as a motor controller (Spark)
	Spark led = new Spark(8);
	//For gyro
	//static final double Kp = 0.03;
	//Wasn't sure about order of this
	//RobotDrive myDrive = new RobotDrive(backRight, backLeft, frontRight, frontLeft);
	//Dashboard stuff I think
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String gameData;
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	
	
	public void stop() {
		frontLeft.set(0);
		frontRight.set(0);
		backLeft.set(0);
		backRight.set(0);
		//LEDs turned yellow
		led.set(0.69);
	}
	
	public void forward() {
		frontLeft.set(-0.5);
		frontRight.set(0.5);
		backLeft.set(-0.5);
		backRight.set(0.5);
		//LEDs turned Dark Green
		led.set(0.75);
	}
	
	public void backward() {
		frontLeft.set(0.5);
		frontRight.set(-0.5);
		backLeft.set(0.5);
		backRight.set(-0.5);
		//LEDs turned RED
		led.set(0.61);
	}
	
	public void leftTurn() {
		frontLeft.set(0.5);
		backLeft.set(0.5);
		frontRight.set(0.5);
		backRight.set(0.5);
		//LEDs turned blue heartbeats
		led.set(-0.23);
	}
	
	public void rightTurn() {
		frontRight.set(-0.5);
		backRight.set(-0.5);
		frontLeft.set(-0.5);
		backLeft.set(-0.5);
		//LEDs turned blue heartbeats
		led.set(-0.23);
	}
		
	
	public void tankDrive(){
		double leftSpeed = jStickL.getY();
		double rightSpeed = -jStickR.getY();
		
		frontLeft.set(leftSpeed);
		backLeft.set(leftSpeed);
		
		frontRight.set(rightSpeed);
		backRight.set(rightSpeed);
	}
	
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		//had to assign it here, woulddn't work other way
		//lkjkljlkjkl
	    gyro.calibrate();
	    gyro.reset();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		//timer has to reset, just in case
		time.reset();
		//starts
		time.start();
		
		
		
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	//Autonomous is experimental, feel free to correct if you want to
	//Just tell me what I did wrong for future reference.
	@Override
	public void autonomousPeriodic() {
		
		System.out.println(gyro.getAngle());
		//switch (autoSelected) {
		
		//case customAuto:
		if(gameData.charAt(0) == 'L'){
			forward();
			if(time.get() >= 5) {
				backward();
			}
			if(time.get() >= 10) {
				rightTurn();
			
			}
			if(time.get() >= 15) {
				stop();
			}
				//put left code here.
				
		}
		if(gameData.charAt(1) == 'R'){
			forward();
			if(time.get() >= 5) {
				backward();
			}
			if(time.get() >= 10) {
				leftTurn();
			
			}
			if(time.get() >= 15) {
				stop();
			}
				//put right code here.
		}
			//On the right side of the field for example
			//Note: if there is a better way, please correct this
			//This is really preliminary
							
			
			
			//turns Left
			
			// Put custom auto code here
			
		//case defaultAuto:
			//just get across base line
		//default:
			//robot goes forward for 10 seconds
			
			
			
			
			// Put default auto code here
			
	}
	@Override
	public void teleopInit(){
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		tankDrive();
		
		//A Button would be intake for cubes
		if(xbox.getAButton() == true){
			intakeClaw.set(1);
		}
		//B Button would outtake cubes for emergency/strategy purposes
		if(xbox.getBButton() == true){
			intakeClaw.set(-1);
		}
		//X Button stops Claw
		if(xbox.getXButton() == true){
			intakeClaw.set(0);
		}
		//Start Button activates Ladder
		if(xbox.getStartButton() == true){
			firemanLadder.set(0.5);
		}
		//Back button stops Ladder
		if(xbox.getBackButton() == true){
			firemanLadder.set(0);
		}
		//Stick Button reverses Ladder
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		System.out.println(gyro.getAngle());
		led.set(0.57);
	}
}

