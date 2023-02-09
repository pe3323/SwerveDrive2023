// FRC2106 Junkyard Dogs - Swerve Drive Base Code

package frc.robot.commands;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.util.Constants.DriveConstants;
import java.util.function.Supplier;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveAlignBasic extends CommandBase {

  private boolean finished;
  SwerveSubsystem swerveSubsystem;
  VisionSubsystem visionSubsystem;
  Supplier<Boolean> switchOverride;
  Supplier<Double> headingFunction, setpointFunction;

  private PIDController vXController, vYController;
  private PIDController thetaController;

  private double vX;
  private double vY;
  private double vT;

  private double initalHeading;

  // Command constructor
  public SwerveAlignBasic(SwerveSubsystem swerveSubsystem, VisionSubsystem visionSubsystem,
   Supplier<Double> headingFunction, Supplier<Boolean> switchOverride, Supplier<Double> setpointFunction){

    addRequirements(swerveSubsystem, visionSubsystem);

    this.swerveSubsystem = swerveSubsystem;
    this.visionSubsystem = visionSubsystem;
    this.switchOverride = switchOverride;
    this.headingFunction = headingFunction;
    this.setpointFunction = setpointFunction;

    initalHeading = headingFunction.get();

    vXController = new PIDController(0.8, 0, 0);
    vYController = new PIDController(0.8, 0, 0);
    thetaController = new PIDController(DriveConstants.kPThetaController, DriveConstants.kIThetaController, DriveConstants.kDThetaController);

    vX = 0;
    vY = 0;
    vT = 0;

    finished = false;
  }

  @Override
  public void initialize() {
    finished = false;
    initalHeading = headingFunction.get();
  }

  @Override
  public void execute(){

    if(!visionSubsystem.hasTargets()){
      finished = true;
    }else{
      // Facing left
      if(swerveSubsystem.getGyroDegrees() >= 0 && swerveSubsystem.getGyroDegrees() < 180){

      vX = vXController.calculate(visionSubsystem.getTargetTransform().getX(), setpointFunction.get()); // X-Axis PID
      vY = -vYController.calculate(visionSubsystem.getTargetTransform().getY(), 0); // Y-Axis PID
      vT = thetaController.calculate(headingFunction.get(), initalHeading) * 100; // Rotation PID
      vT = -Math.abs(vT) > 0.05 ? vT : 0.0; // Deadband

      // Limit max rotation velocity
      if (vT > DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond){
        vT = DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;
      }else if (vT < -DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond){
        vT = -DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;
      }
      // Facing right
    }else{
      vX = -vXController.calculate(visionSubsystem.getTargetTransform().getX(), setpointFunction.get()); // X-Axis PID
      vY = vYController.calculate(visionSubsystem.getTargetTransform().getY(), 0); // Y-Axis PID
      vT = thetaController.calculate(headingFunction.get(), initalHeading) * 100; // Rotation PID
      vT = -Math.abs(vT) > 0.05 ? vT : 0.0; // Deadband

      // Limit max rotation velocity
      if (vT > DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond){
        vT = DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;
      }else if (vT < -DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond){
        vT = -DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond;
      }
    }
    }

    if(switchOverride.get() == false){
      finished = true;
    }

    // Create chassis speeds
    ChassisSpeeds chassisSpeeds;

    // Apply chassis speeds with desired velocities
    chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(vX, vY, vT, swerveSubsystem.getRotation2d());
    //chassisSpeeds = new ChassisSpeeds(vX, vY, vT);
    //chassisSpeeds = new ChassisSpeeds(0.5,0,0);

    // Create states array
    SwerveModuleState[] moduleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);
 
    // Move swerve modules
    swerveSubsystem.setModuleStates(moduleStates);
  }

  // Stop all module motor movement when command ends
  @Override
  public void end(boolean interrupted){swerveSubsystem.stopModules();}

  @Override
  public boolean isFinished(){return finished;}

}
