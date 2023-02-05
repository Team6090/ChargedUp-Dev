// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Initially from https://github.com/Mechanical-Advantage/RobotCode2022
 */

package frc.robot.operator_interface;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/** Interface for all driver and operator controls. */
public interface OperatorInterface {

  XboxController swerveController = new XboxController(0);
  // Joystick joystick = new Joystick(1);

  public default double getTranslateX() {
    return swerveController.getLeftY();
    // return joystick.getY();
  }

  public default double getTranslateY() {
    return swerveController.getLeftX();
    // return joystick.getX();
  }

  public default double getRotate() {
    return swerveController.getRightX();
    // return joystick.getZ();
  }

  public default Trigger getFieldRelativeButton() {
    return new Trigger(() -> swerveController.getBButton());
  }

  public default Trigger getResetGyroButton() {
    return new Trigger(() -> swerveController.getBackButton());
  }

  public default Trigger getXStanceButton() {
    return new Trigger(() -> swerveController.getAButton());
  }
  
  public default Trigger alignToAprilTagLimelightX() {
    return new Trigger(() -> swerveController.getXButton());
  }

  public default Trigger alignToAprilTagLimelightY() {
    return new Trigger(() -> swerveController.getYButton());
  }

  public default Trigger AlignToAprilTagLimelightXY() {
    return new Trigger(() -> swerveController.getRightBumper());
  }
}
