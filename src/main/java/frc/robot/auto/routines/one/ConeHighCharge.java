// FRC2106 Junkyard Dogs - Continuity Base Code - www.team2106.org

package frc.robot.auto.routines.one;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.auto.commands.TrajectoryWeaver;
import frc.robot.auto.routines.util.AutoBalance;
import frc.robot.commands.elevator.ElevatorSolenoid;
import frc.robot.commands.elevator.ElevatorZero;
import frc.robot.commands.grabber.intake.GrabberHold;
import frc.robot.commands.grabber.intake.GrabberSolenoid;
import frc.robot.commands.routines.scoring.ScoreTop;
import frc.robot.commands.util.ResetOdometryInverse;
import frc.robot.commands.util.ResetYaw;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.GrabberSubsystem;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.util.Leds;
import frc.robot.util.LightStrip;



// Run multiple commands in a routine
public class ConeHighCharge extends SequentialCommandGroup{


    // DEPRECATED

    // PPTrajectory and event map
    private final PathPlannerTrajectory back = PathPlanner.loadPath("backwards0.5M", new PathConstraints(3.5, 4.5)); 
    // private final HashMap<String, Command> eventMap = new HashMap<>();

    // Routine command constructor
    public ConeHighCharge(SwerveSubsystem swerveSubsystem, ElevatorSubsystem elevatorSubsystem, GrabberSubsystem grabberSubsystem, PIDController xController,
    PIDController yController,  PIDController ppthetaController, Leds leds){

        // Add commands to event map markers
        // eventMap.put("marker1", new PrintCommand("TRAJ1: Passed Marker 1"));

        // Add commands to run
        addCommands(
        new GrabberHold(grabberSubsystem), // reverse grabber for hold
        new ScoreTop(elevatorSubsystem, grabberSubsystem), // raise elevator
        new WaitCommand(0.8), // wait
        new ElevatorSolenoid(elevatorSubsystem), // bring down elevator
        new WaitCommand(1), // wait
        //new GrabberReverse(grabberSubsystem), // reverse grabber motor
        new GrabberSolenoid(grabberSubsystem), // open grabber up
        new WaitCommand(0.5), // wait
        new ElevatorSolenoid(elevatorSubsystem), // bring up elevator
        new WaitCommand(1), // wait
        new ElevatorZero(elevatorSubsystem, grabberSubsystem), // zero elevator
        new WaitCommand(1),
        new TrajectoryWeaver(swerveSubsystem, xController, yController, ppthetaController, back, true, false), // bring robot back
        new GrabberSolenoid(grabberSubsystem), // close grabber
        new AutoBalance(swerveSubsystem, leds)
        //new ResetYaw(swerveSubsystem), // reset gyro yaw
        //new ResetOdometryInverse(swerveSubsystem) // reset odometry
        
        );

    }






    





}