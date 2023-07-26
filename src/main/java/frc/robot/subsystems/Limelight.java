// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.RectanglePoseArea;

public class Limelight extends SubsystemBase {
  Drivetrain drivebase;
  Alliance alliance;
  private Boolean enable = true;
  private Boolean trust = false;
  private int fieldError = 0;
  private int distanceError = 0;
  private Pose2d botpose;
  public static final RectanglePoseArea field =
      new RectanglePoseArea(new Translation2d(0.0, 0.0), new Translation2d(16.54, 8.02));

  /** Creates a new Limelight. */
  public Limelight(Drivetrain drivebase) {
    this.drivebase = drivebase;
    SmartDashboard.putNumber("Field Error", fieldError);
    SmartDashboard.putNumber("Limelight Error", distanceError);
  }

  @Override
  public void periodic() {
    if (enable) {
      LimelightHelpers.Results result =
          LimelightHelpers.getLatestResults("limelight-left").targetingResults;
      if (!(result.botpose[0] == 0 && result.botpose[1] == 0)) {
        if (alliance == Alliance.Blue) {
          botpose = LimelightHelpers.toPose2D(result.botpose_wpiblue);
        } else if (alliance == Alliance.Red) {
          botpose = LimelightHelpers.toPose2D(result.botpose_wpired);
        }
        if (field.isPoseWithinArea(botpose)) {
          if (drivebase.getPose().getTranslation().getDistance(botpose.getTranslation()) < 0.33
              || trust
              || result.targets_Fiducials.length > 1) {
            drivebase.addVisionMeasurement(
                botpose,
                Timer.getFPGATimestamp()
                    - (result.latency_capture / 1000.0)
                    - (result.latency_pipeline / 1000.0),
                true,
                1.0);
          } else {
            distanceError++;
            SmartDashboard.putNumber("Limelight Error", distanceError);
          }
        } else {
          fieldError++;
          SmartDashboard.putNumber("Field Error", fieldError);
        }
      }
    }
  }

  public void setAlliance(Alliance alliance) {
    this.alliance = alliance;
  }

  public void useLimelight(boolean enable) {
    this.enable = enable;
  }

  public void trustLL(boolean trust) {
    this.trust = trust;
  }

  public void fineAlignment(Boolean fine) {
    if (fine) {
      LimelightHelpers.setPipelineIndex("limelight-left", 1);
      LimelightHelpers.setLEDMode_ForceOn("limelight-left");
      enable = false;
    } else {
      LimelightHelpers.setPipelineIndex("limelight-left", 0);
      LimelightHelpers.setLEDMode_ForceOff("limelight-left");
      enable = true;
    }
  }

  public double getFineAlign() {
    fineAlignment(true);
    var tx = LimelightHelpers.getTX("limelight-left");
    if (Math.abs(tx) > 0.1) {
      return Math.copySign(0.2, tx);
    } else {
      return 0.0;
    }
  }
}
