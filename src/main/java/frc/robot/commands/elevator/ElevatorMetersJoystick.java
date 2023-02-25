// FRC2106 Junkyard Dogs - Swerve Drive Base Code

package frc.robot.commands.elevator;
import frc.robot.subsystems.ElevatorSubsystem;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ElevatorMetersJoystick extends CommandBase {

  private ElevatorSubsystem subsystem;
  private Supplier<Double> meters;

  public ElevatorMetersJoystick(ElevatorSubsystem subsystem, Supplier<Double> meters) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = subsystem;
    this.meters = meters;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set the elevator to the desired meters
   
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //subsystem.setElevatorSetpoint(meters.get());
    subsystem.setElevatorMeters(meters.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //subsystem.stopElevator();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
