package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "TeleOp Mec Wheels", group = "Basic")
public class BasicTeleOp extends LinearOpMode {
    //I declare all the motors over here.
    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;

    private final int maxMotorPower = 70;
    private final int motorReduction = 100 - maxMotorPower;

    public BasicTeleOp() {
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //I initialize all the motors over here before the teleOp code that is initiated after the start button is pressed.

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        telemetry.addLine("Robot is ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if () {//absolute value of left joystick displacement - threshold goes here
                motorFL = motorFL.setPower(maxMotorPower);
                motorFR = motorFR.setPower(maxMotorPower);
                motorBL = motorBL.setPower(maxMotorPower);
                motorBR = motorBR.setPower(maxMotorPower)
            } else if () { //if joystick is going left indicating a turn {
                motorFL.setPower(-maxMotorPower); //turns wheel
                motorFR.setPower(-maxMotorPower); //turns the wheel
                motorFR.setPower(maxMotorPower);
                motorBR.setPower(maxMotorPower);
            }
            wait(1);
            idle();
        }
    }

}
