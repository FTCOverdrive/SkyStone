package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.*;

@TeleOp (name = "TeleOp Mec Wheels", group = "Basic")
public class BasicTeleOp extends LinearOpMode {
    //I declare all the motors over here.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;

    //variables for trig drive
    private double r;
    private double rightX;
    private double vFL;
    private double vFR;
    private double vBL;
    private double vBR;
    private double velocityConst;
    private double strafeCounter = 0;

    public BasicTeleOp() {

    }

    //uses if statements to make sure that the motors don't burn out and sets it equal to 1 if it is over 1 or equal to -1 if it is below -1
    public void restrictPower(double powerNum) {
        if(vBR > powerNum) {
            vBR = powerNum;
        }
        else if(vBR < -powerNum) {
            vBR = -powerNum;
        }

        if(vBL > powerNum) {
            vBL = powerNum;
        }
        else if(vBL < -powerNum) {
            vBL = -powerNum;
        }

        if(vFR > powerNum) {
            vFR = powerNum;
        }
        else if(vFR < -powerNum) {
            vFR = -powerNum;
        }

        if(vFL > powerNum) {
            vFL = powerNum;
        }
        else if(vFL < -powerNum) {
            vFL = -powerNum;
        }

    }

    //commented out on the opmode while loop in the runopmode function
    public void trigMecRun() {
        // start of trig version for mecanum wheel drive

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;

        //test code
        if(gamepad1.left_stick_y > 0.8) {
            rightX = gamepad1.right_stick_x * -0.5;
        } else {
            rightX = gamepad1.right_stick_x * -0.25;
        }

        //used to counteract the rotation from strafing
        if(Math.abs(gamepad1.left_stick_y) < 0.1 && gamepad1.left_stick_x > 0){
            rightX -= strafeCounter * Math.abs(gamepad1.left_stick_x);
        }
        else if(Math.abs(gamepad1.left_stick_y) < 0.1 && gamepad1.left_stick_x < 0){
            rightX += strafeCounter * Math.abs(gamepad1.left_stick_x);

        }

        //sets the power for each of the motor variables
        velocityConst = 1.414213565;
        vFL = (r * Math.sin(robotAngle) + rightX)* velocityConst;
        vFR = (r * Math.cos(robotAngle) - rightX)* velocityConst;
        vBL = (r * Math.cos(robotAngle) + rightX)* velocityConst;
        vBR = (r * Math.sin(robotAngle) - rightX)* velocityConst;

        restrictPower(1);

        motorFL.setPower(vFL); //* -1
        motorFR.setPower(vFR);
        motorBL.setPower(vBL); // *-1
        motorBR.setPower(vBR);
        //end of trig version

        //telemetry adding the status of run time to the screen of the android phones
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("vFL", "vFL: " + vFL);
        telemetry.addData("VFR", "vFR: " + vFR);
        telemetry.addData("vBL", "vBl: " + vBL);
        telemetry.addData("vBR", "vBR: " + vBR);
        telemetry.addData("Strafe Counter", "SC: " + strafeCounter);
        telemetry.update();
    }

    public void vectorMecRun() {

        //sets the direction of the front left mecanum wheel and the back left mecanum wheel in reverse

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        //The first if statement uses Math.abs is for the forward and backward movement with the direction of left_stick_y. If the joystick moves up, it will move in the forward direction and vise versa
        //left_stick_y shows the movement across the vertical axis, up or down
        //left_stick_x shows the movement across the horizontal axis, left or right

        //On game controller the joystick all the way up is -1.0 and all the way down is 1.0
        if(Math.abs(gamepad1.left_stick_x) < 0.10) { //this if statement is saying "if the stick_x is closer to zero(middle) then use the left stick y value to go forward"
            //purpose of the 0.10 is to make sure for the slightest touch is accommodated in order for the robot to go forward and not left and right
            vFL = gamepad1.left_stick_y; //will go forward/backwards
            vFR = gamepad1.left_stick_y; //will go forward/backwards
            vBL = gamepad1.left_stick_y; //will go forward/backwards
            vBR = gamepad1.left_stick_y; //will go forward/backwards
        } else if(Math.abs(gamepad1.left_stick_y) < 0.10) { //This else if statement is used in order to go sideways. The front right and the backLeft(opposite of each other) must go in different directions(move away) in order to go sideways
            //We used the left_stick_x in the game-pad in order for the robot to go in the left or right movement, through the horizontal axis
            //if hte joystick is going to the right or the left it will set the motors to that value
            vFL = gamepad1.left_stick_x;
            vFR = gamepad1.left_stick_x * -1;
            vBL = gamepad1.left_stick_x * -1;
            vBR = gamepad1.left_stick_x;
        } else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y > 0){ //The diagonal movement can be shown here with two wheels moving. The front left and the back right.
            //We use the sum of the left stick x and y and get the average to find the speed diagonally
            //finding the average determines speed diagonally
            //EX: the direction of joystick is facing northeast
            vFL = Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
            vBR = Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
        } else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y > 0){
            //the direction of the joystick is facing northwest
            //will go diagonal in the forward left direction
            vFR = Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
            vBL = Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
        } else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y < 0){
            //the position of the joystick is southeast
            //the robot will go bottom right diagonal
            vFL = -Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
            vBR = -Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
        } else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y < 0){
            //the position of the joystick is southwest
            //the robot will go bottom left diagonal
            vFR = -Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
            vBL = -Math.hypot(gamepad1.left_stick_y, gamepad1.left_stick_x);
        }
        //perfect strafe
        if(gamepad1.right_bumper){
            vFL = 0.5;
            vFR = -0.5;
            vBL = -0.5;
            vBR = 0.5;
        }
        if(gamepad1.left_bumper){
            vFL = -0.5;
            vFR = 0.5;
            vBL = 0.5;
            vBR = -0.5;
        }


        // For rotation, we subtract gamepad1.right_stick_x from the right side and added the value to the left
        vFL += gamepad1.right_stick_x;
        vFR -= gamepad1.right_stick_x;
        vBL += gamepad1.right_stick_x;
        vBR -= gamepad1.right_stick_x;

        //uses if statements to make sure that the motors don't burn out and sets it equal to 1 if it is over 1 or equal to -1 if it is below -1
        restrictPower(1);

        motorFL.setPower(vFL);
        motorFR.setPower(vFR);
        motorBL.setPower(vBL);
        motorBR.setPower(vBR);
        //telemetry adding the status of run time to the screen of the android phones
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("vFL", "vFL: " + vFL);
        telemetry.addData("VFR", "vFR: " + vFR);
        telemetry.addData("vBL", "vBl: " + vBL);
        telemetry.addData("vBR", "vBR: " + vBR);
        telemetry.update();
    }


    @Override
    public void runOpMode() throws InterruptedException {
        //I initialize all the motors over here before the teleOp code that is initiated after the start button is pressed.
        //Important for setting wheel reverse to go backward


        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        telemetry.addLine("Robot is ready");
        telemetry.update();

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            //method that will run when opmode is active
            trigMecRun();
        }
    }

}
