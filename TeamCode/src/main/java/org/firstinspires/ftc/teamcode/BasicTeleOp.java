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
    private final int maxMotorPower = 70;
    private final int motorReduction = 100 - maxMotorPower;
    //variables for trig drive
    private double r;
    private double rightX;
    private double vFL;
    private double vFR;
    private double vBL;
    private double vBR;

    public BasicTeleOp() {
    }

    public void trigMecRun() {
        // start of trig version for mecanum wheel drive
        //sets direction to reverse
        //Important for setting wheel reverse to go backward
        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);//r is the
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        rightX = gamepad1.right_stick_x * -1;

        vFL = r * Math.sin(robotAngle) + rightX;
        vFR = r * Math.cos(robotAngle) - rightX;
        vBL = r * Math.cos(robotAngle) + rightX;
        vBR = r * Math.sin(robotAngle) - rightX;

        motorFL.setPower(vFL); //* -1
        motorFR.setPower(vFR);
        motorBL.setPower(vBL); // *-1
        motorBR.setPower(vBR);
        //end of trig version

        //telemetry adding the status of run time to the screen of the android phones
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
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
        runtime.reset();
        while (opModeIsActive()) {
            //method that will run when opmode is active
            trigMecRun();
        }
    }


}
