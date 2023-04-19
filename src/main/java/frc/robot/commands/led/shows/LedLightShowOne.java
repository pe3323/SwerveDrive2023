// FRC2106 Junkyard Dogs - Continuity Base Code - www.team2106.org

package frc.robot.commands.led.shows;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.led.DisableLedRainbow;
import frc.robot.commands.led.SetLedRGB;
import frc.robot.commands.led.SetLedRGBEveryOther;
import frc.robot.util.LightStrip;

// Run multiple commands in a routine
public class LedLightShowOne extends SequentialCommandGroup{

    // Routine command constructor
    public LedLightShowOne(LightStrip ledStrip){

      addCommands(
        new DisableLedRainbow(ledStrip),
        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),
        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),

        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),
        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),

        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),
        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),

        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3),
        new SetLedRGB(ledStrip, 162, 0, 0),
        new WaitCommand(0.3)
      );

    }
  }