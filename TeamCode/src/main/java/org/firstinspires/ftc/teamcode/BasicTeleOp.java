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
    private double velocityConst;

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
