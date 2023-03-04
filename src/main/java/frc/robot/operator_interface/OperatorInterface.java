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

  XboxController primaryController = new XboxController(0);

  XboxController auxController = new XboxController(1);

  public default double getTranslateX() {
    return primaryController.getLeftY();
  }

  public default double getTranslateY() {
    return primaryController.getLeftX();
  }

  public default double getRotate() {
    return primaryController.getRightX();
  }

  public default Trigger getFieldRelativeButton() {
    return new Trigger(() -> primaryController.getBButton());
  }

  public default Trigger getResetGyroButton() {
    return new Trigger(() -> primaryController.getBackButton());
  }

  public default Trigger getXStanceButton() {
    return new Trigger(() -> primaryController.getAButton());
  }

  // public default Trigger alignToAprilTagLimelightX() {
  //   return new Trigger(() -> primaryController.getXButton());
  // }

  // public default Trigger alignToAprilTagLimelightY() {
  //   return new Trigger(() -> primaryController.getYButton());
  // }

  // public default Trigger AlignToAprilTagLimelightXY() {
  //   return new Trigger(() -> primaryController.getRightBumper());
  // }

  public default Trigger Set0() {
    return new Trigger(() -> primaryController.getLeftBumper());
  }

  public default Trigger Set1() {
    return new Trigger(() -> primaryController.getRightBumper());
  }

  public default Trigger PivotPos() {
    return new Trigger(() -> auxController.getYButton());
  }

  public default Trigger PivotNeg() {
    return new Trigger(() -> auxController.getAButton());
  }

  public default Trigger RunCommandGroup() {
    return new Trigger(() -> auxController.getAButton());
  }

  public default Trigger ArmOut() {
    return new Trigger(() -> auxController.getBButton());
  }

  public default Trigger ArmIn() {
    return new Trigger(() -> auxController.getXButton());
  }

  public default Trigger HoldArm() {
    return new Trigger(() -> auxController.getStartButton());
  }

  public default Trigger ReleaseArm() {
    return new Trigger(() -> auxController.getBackButton());
  }

  public default Trigger OpenIntake() {
    return new Trigger(() -> auxController.getRightBumper());
  }

  public default Trigger CloseIntake() {
    return new Trigger(() -> auxController.getLeftBumper());
  }

  public default Trigger IntakeIn() {
    return new Trigger(() -> primaryController.getXButton());
  }

  public default Trigger IntakeOut() {
    return new Trigger(() -> primaryController.getYButton());
  }
}
