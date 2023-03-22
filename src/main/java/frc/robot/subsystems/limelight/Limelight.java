package frc.robot.subsystems.limelight;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {

  /*
   * Pipelines:
   * 0: AprilTags
   * 1: Retroreflective Top
   * 2: Retro Refelective Bottom
   */

  static NetworkTable limelightNetworkTable =
      NetworkTableInstance.getDefault().getTable("limelight");

  public static double GyroAngle = 0.0;

  public static void TurnLimelightOff() {
    limelightNetworkTable.getEntry("ledMode").setDouble(1.0);
  }

  public static double GetLimelightOnOff() {
    return limelightNetworkTable.getEntry("ledMode").getDouble(-1.0);
  }

  public static void TurnLimelightOn() {
    limelightNetworkTable.getEntry("ledMode").setDouble(3.0);
  }

  public static double GetX() {
    return limelightNetworkTable.getEntry("tx").getDouble(0.0);
  }

  public static double GetY() {
    return limelightNetworkTable.getEntry("ty").getDouble(0.0);
  }

  public static double GetV() {
    return limelightNetworkTable.getEntry("tv").getDouble(0.0);
  }

  public static double GetS() {
    return limelightNetworkTable.getEntry("ts").getDouble(0.0);
  }

  public static double GetDistance() {
    return LimelightConstants.HEIGHT_DIFFERENCE_GRID
        / Math.tan(LimelightConstants.LIMELIGHT_ANGLE_OF_ELEVATION + GetY());
  }

  public static DoubleArraySubscriber arraySub =
      limelightNetworkTable.getDoubleArrayTopic("botpose").subscribe(new double[] {});

  public static Translation2d PositionOnFieldGrid() {
    if (GetV() == 1) {
      double[] array = arraySub.get();
      SmartDashboard.putNumber("length", array.length);
      if (array.length == 6) {
        double x = array[0];
        double y = array[1];
        return new Translation2d(x + 8.25, y + 4);
      } else {
        return new Translation2d(0, 0);
      }
    } else {
      return new Translation2d(0, 0);
    }
  }

  public static void SetPipeline(int pipeline) {
    limelightNetworkTable.getEntry("pipeline").setDouble(pipeline);
  }

  public static void GetConeScoreLocations() {}
}
