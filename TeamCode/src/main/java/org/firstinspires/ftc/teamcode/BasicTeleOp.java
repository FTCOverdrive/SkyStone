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

    public BasicTeleOp() {

    }
    public void vectorMecRun() {

        //sets the direction of the front left mecanum wheel and the back left mecanum wheel in reverse

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        //The first if statement uses Math.abs is for the forward and backward movement with the direction of left_stick_y. If the joystick moves up, it will move in the forward direction
        //left_stick_y shows the movement across the vertical axis, up or down
        //left_stick_x shows the movement across the horizontal axis, left or right

        //On game controller the jystick all the way up is -1.0 and all the way down is 1.0
        if(Math.abs(gamepad1.left_stick_x) < 0.10) {
            //The use of the left_stick_y is used in order for the movement up or down, vertical axis
            //the values of the left_stick_y are set to 0 as the robot is moving forward and not left and right
            vFL = gamepad1.left_stick_y; //0
            vFR = gamepad1.left_stick_y; //0
            vBL = gamepad1.left_stick_y; //0
            vBR = gamepad1.left_stick_y; //0
        } else if(Math.abs(gamepad1.left_stick_y) < 0.10) { //This else if statement is used in order to go sideways. The front right and the backLeft(opposite of each other) must go in different directions(move away) in order to go sideways
            //We used the left_stick_x in the game-pad in order for the robot to go in the left or right movement, through the horizontal axis
            vFL = gamepad1.left_stick_x;
            vFR = gamepad1.left_stick_x * -1;
            vBL = gamepad1.left_stick_x * -1;
            vBR = gamepad1.left_stick_x;
        } else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y > 0){ //The diagonal movement can be shown here with two wheels moving. The front left and the back right.
            //We use the sum of the left stick x and y and get the average to find the speed diagonally
            //finding the average determines speed diagonally
            vFL = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
            vBR = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        } else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y > 0){
            vFR = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
            vBL = (gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        } else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y < 0){
            vFL = -(gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
            vBR = -(gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        } else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y < 0){
            vFR = -(gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
            vBL = -(gamepad1.left_stick_y + gamepad1.left_stick_x) / 2;
        }
        // For rotation, we subtract gampad1.right_stick_x from the right side and added the value to the left
        vFL += gamepad1.right_stick_x;
        vFR -= gamepad1.right_stick_x;
        vBL += gamepad1.right_stick_x;
        vBR -= gamepad1.right_stick_x;

        //uses if statements to make sure that the motors don't burn out and sets it equal to 1 if it is over 1 or equal to -1 if it is below -1
        if(vBR > 1) {
            vBR = 1;
        }
        else if(vBR < -1) {
            vBR = -1;
        }

        if(vBL > 1) {
            vBL = 1;
        }
        else if(vBL < -1) {
            vBL = -1;
        }

        if(vFR > 1) {
            vFR = 1;
        }
        else if(vFR < -1) {
            vFR = -1;
        }

        if(vFL > 1) {
            vFL = 1;
        }
        else if(vFL < -1) {
            vFL = -1;
        }
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
//            trigMecRun();
            vectorMecRun();
        }
    }

    //commented out on the opmode while loop in the runopmode function
    public void trigMecRun() {
        // start of trig version for mecanum wheel drive

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;

        if(gamepad1.left_stick_y > 0.8) {
            rightX = gamepad1.right_stick_x * -0.5;
        } else {
            rightX = gamepad1.right_stick_x * -0.25;
        }

        velocityConst = 1.414213565;
        vFL = (r * Math.sin(robotAngle) + rightX)* velocityConst;
        vFR = (r * Math.cos(robotAngle) - rightX)* velocityConst;
        vBL = (r * Math.cos(robotAngle) + rightX)* velocityConst;
        vBR = (r * Math.sin(robotAngle) - rightX)* velocityConst;

        if(vBR > 1) {
            vBR = 1;
        }
        else if(vBR < -1) {
            vBR = -1;
        }

        if(vBL > 1) {
            vBL = 1;
        }
        else if(vBL < -1) {
            vBL = -1;
        }

        if(vFR > 1) {
            vFR = 1;
        }
        else if(vFR < -1) {
            vFR = -1;
        }

        if(vFL > 1) {
            vFL = 1;
        }
        else if(vFL < -1) {
            vFL = -1;
        }

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
        telemetry.update();
    }
}
