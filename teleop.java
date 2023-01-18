package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Default TeleOp")
public class teleop extends LinearOpMode {
    double x;
    double y;
    double x2;
    double chassis_speed = 1;
    double lift_speed = 1;
    double claw_speed = 1;
    float lefttrig;
    float righttrig;
    boolean a;
    boolean b;
    boolean leftBump;
    boolean rightBump;
    int aInt;
    int bInt;
    int leftBumpInt;
    int rightBumpInt;

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor lift;
    CRServo claw;

    @Override
    public void runOpMode() throws InterruptedException{
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        lift = hardwareMap.dcMotor.get("lift");
        claw = hardwareMap.crservo.get("claw");

        waitForStart();
        while(opModeIsActive()){
            //Reading GamePad Values
            lefttrig = gamepad1.left_trigger;
            righttrig = gamepad1.right_trigger;
            a = gamepad2.a;
            b = gamepad2.b;
            leftBump = gamepad2.left_bumper;
            rightBump = gamepad2.right_bumper;
            aInt = (a) ? 1 : 0;
            bInt = (b) ? 1 : 0;
            leftBumpInt = (leftBump) ? 1 : 0;
            rightBumpInt = (rightBump) ? 1 : 0;
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            x2 = gamepad1.right_stick_x;

            //Code for speed settings
            if(lefttrig >= 0.5){
                chassis_speed = 0.25;
            }else if(righttrig >= 0.5){
                chassis_speed = 1;
            }

            //Code for lift
            if(aInt > bInt){
                lift.setPower(Range.clip(lift_speed, -1.0, 1.0));
            } else if(bInt > aInt){
                lift.setPower(Range.clip(-lift_speed, -1.0, 1.0));
            } else {
                lift.setPower(0);
            }

            //Code for claw
            if(leftBumpInt > rightBumpInt){
                claw.setPower(Range.clip(claw_speed, -1.0, 1.0));
            } else if(rightBumpInt > leftBumpInt){
                claw.setPower(Range.clip(-claw_speed, -1.0, 1.0));
            } else {
                claw.setPower(0);
            }

            //Complex code for chassis
            if(abs(x) > abs(y) && abs(x) > abs(x2)){
                frontLeft.setPower(Range.clip(-x*chassis_speed, -1.0, 1.0));
                frontRight.setPower(Range.clip(-x*chassis_speed, -1.0, 1.0));
                backLeft.setPower(Range.clip(-x*chassis_speed, -1.0, 1.0));
                backRight.setPower(Range.clip(-x*chassis_speed, -1.0, 1.0));
            } else if(abs(y) > abs(x) && abs(y) > abs(x2)){
                frontLeft.setPower(Range.clip(y*chassis_speed, -1.0, 1.0));
                frontRight.setPower(Range.clip(-y*chassis_speed, -1.0, 1.0));
                backLeft.setPower(Range.clip(y*chassis_speed, -1.0, 1.0));
                backRight.setPower(Range.clip(-y*chassis_speed, -1.0, 1.0));
            } else if(abs(x2) > abs(x) && abs(x2) > abs(y)){
                frontLeft.setPower(Range.clip(-x2*chassis_speed, -1.0, 1.0));
                frontRight.setPower(Range.clip(-x2*chassis_speed, -1.0, 1.0));
                backLeft.setPower(Range.clip(x2*chassis_speed, -1.0, 1.0));
                backRight.setPower(Range.clip(x2*chassis_speed, -1.0, 1.0));
            }else{
                //This made sure that the power of the motors are not affected and aimed to reduce stuttering
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
            }
        }
    }
}
