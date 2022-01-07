package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "The claw! Ooooooooh!")
public class claw extends LinearOpMode {
    //Creating motor
    DcMotor motor1;
    //Creating Button Values
    boolean a, b;
    double x;
    double y;
    double p;
    boolean a1, b1, a2, b2;
    boolean up, down, left, right;
    double speed;
    private ElapsedTime runtime = new ElapsedTime();
    //Creating another motor
    DcMotor motor;
    boolean wheelon = false;
    boolean clawon = false;
    double x2;
    // x is value of wheels, back is -x,  y+x y-x
    //Creating another motor
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor wheel;
    @Override
    public void runOpMode() throws InterruptedException{
        //What happens during program
        motor1 = hardwareMap.dcMotor.get("claw");
        motor = hardwareMap.dcMotor.get("motor");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        wheel = hardwareMap.dcMotor.get("wheel");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (opModeIsActive()){
            b1 = gamepad2.right_bumper;
            a1 = gamepad2.left_bumper;
            a2 = gamepad2.a;
            b2 = gamepad2.b;

            //Opening/closing claw
            if(a1){
                motor1.setPower(0.25);
            } else if(b1){
                motor1.setPower(-0.25);

            } else{
                //This made sure that the power of the motor is not affected and aimed to reduce stuttering
                motor1.setPower(0);
            }
            //Moves lift based on button
            if(a2){
                movingvoid(1);
            }
            if(b2){
                movingvoid(-1);
            }
            a1 = gamepad2.a;
            b1 = gamepad2.b;
            y = gamepad1.left_stick_y;
            x = gamepad1.left_stick_x;
            x2 = gamepad1.right_stick_x;
            p = gamepad2.left_stick_y;
            a = gamepad1.a;
            b = gamepad1.b;
            up = gamepad1.dpad_up;
            down = gamepad1.dpad_down;
            left = gamepad1.dpad_left;
            right = gamepad1.dpad_right;

            //Setting wheel speed based on directional values
            if(up){
                speed = 0.8;
            }
            if(down){
                speed = 0.4;
            }
            if(left){
                speed = 0.6;
            }
            if(right){
                speed = 0.6;
            }
            //Moving wheel with speed set
            if(a && wheelon){
                wheel.setPower(-speed);
                wheelon = false;
            }
            if(a && !wheelon){
                wheel.setPower(0);
                wheelon = true;
            }
            if(b && wheelon){
                wheel.setPower(speed);
                wheelon = false;
            }
            if(b && !wheelon){
                wheel.setPower(0);
                wheelon = true;
            }
            //Run to position ticks
            //frontLeft.setPower(Range.clip(-y - (0.5 * x), -1.0, 1.0));
            //frontRight.setPower(Range.clip(y - (0.5 * x), -1.0, 1.0));
            //backLeft.setPower(Range.clip(-y + (0.5 * x), -1.0, 1.0));
            //backRight.setPower(Range.clip(y + (0.5 * x), -1.0, 1.0));
            //Moving robot based on controller inputs as numbers
            if(abs(x) > abs(y) && abs(x) > abs(x2)){
                frontLeft.setPower(Range.clip(-x, -1.0, 1.0));
                frontRight.setPower(Range.clip(-x, -1.0, 1.0));
                backLeft.setPower(Range.clip(-x, -1.0, 1.0));
                backRight.setPower(Range.clip(-x, -1.0, 1.0));
            } else if(abs(y) > abs(x) && abs(y) > abs(x2)){
                frontLeft.setPower(Range.clip(y, -1.0, 1.0));
                frontRight.setPower(Range.clip(-y, -1.0, 1.0));
                backLeft.setPower(Range.clip(y, -1.0, 1.0));
                backRight.setPower(Range.clip(-y, -1.0, 1.0));
            } else if(abs(x2) > abs(x) && abs(x2) > abs(y)){
                frontLeft.setPower(Range.clip(-x2, -1.0, 1.0));
                frontRight.setPower(Range.clip(-x2, -1.0, 1.0));
                backLeft.setPower(Range.clip(x2, -1.0, 1.0));
                backRight.setPower(Range.clip(x2, -1.0, 1.0));
            }else{
                //This made sure that the power of the motors are not affected and aimed to reduce stuttering
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                wheel.setPower(0);
            }
        }
    }
    public void movingvoid(int upordown){
        double speed2 = 0.25*upordown;
        motor.setPower(speed2);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        motor.setPower(0);
    }
}
