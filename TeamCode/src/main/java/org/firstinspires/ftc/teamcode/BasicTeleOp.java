package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.lang.*;

@TeleOp (name = "TeleOp Mec Wheels", group = "Basic")
public class BasicTeleOp extends LinearOpMode {
    //I declare all the motors over here.
    private DcMotor motorFL;
    private DcMotor motorFR;
    private DcMotor motorBL;
    private DcMotor motorBR;
    private final int maxMotorPower = 70;
    private final int motorReduction = 100 - maxMotorPower;
    //variables for trig drive
    private double r;
    private double rightX;
    private double v1;
    private double v2;
    private double v3;
    private double v4;

    public BasicTeleOp() {
    }

    public void trigMecRun() {
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
            // start of trig version for mecanum wheel drive
            r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);//r is the
            double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            rightX = gamepad1.right_stick_x;
            v1 = r * Math.cos(robotAngle) + rightX;
            v2 = r * Math.sin(robotAngle) - rightX;
            v3 = r * Math.sin(robotAngle) + rightX;
            v4 = r * Math.cos(robotAngle) - rightX;
            motorFL.setPower(v1 * -1.0);
            motorFR.setPower(v2);
            //sets direction to reverse
            //Important for setting wheel reverse to go backward
            motorBL.setPower(v3 * -1.0);
            motorBR.setPower(v4);
            //end of trig version
        }
        //going to test with robot
//        wait(1);
//        idle();
    }


}
