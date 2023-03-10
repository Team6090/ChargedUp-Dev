// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Initially from https://github.com/Mechanical-Advantage/RobotCode2022
 */

package frc.robot.operator_interface;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface OperatorInterface {

  // Primary Controller
  XboxController primaryController = new XboxController(0);

  public default Trigger PrimaryA() {
    return new Trigger(() -> primaryController.getAButton());
  }

  public default Trigger PrimaryB() {
    return new Trigger(() -> primaryController.getBButton());
  }

  public default Trigger PrimaryX() {
    return new Trigger(() -> primaryController.getXButton());
  }

  public default Trigger PrimaryY() {
    return new Trigger(() -> primaryController.getYButton());
  }

  public default Trigger PrimaryStart() {
    return new Trigger(() -> primaryController.getStartButton());
  }

  public default Trigger PrimaryBack() {
    return new Trigger(() -> primaryController.getBackButton());
  }

  public default POVButton PrimaryPOV0() {
    return new POVButton(primaryController, 0);
  }

  public default POVButton PrimaryPOV90() {
    return new POVButton(primaryController, 90);
  }

  public default POVButton PrimaryPOV180() {
    return new POVButton(primaryController, 180);
  }

  public default POVButton PrimaryPOV270() {
    return new POVButton(primaryController, 270);
  }

  public default double PrimaryLeftStickXAxis() {
    return primaryController.getLeftX();
  }

  public default double PrimaryLeftStickYAxis() {
    return primaryController.getLeftY();
  }

  public default double PrimaryRightStickXAxis() {
    return primaryController.getRightX();
  }

  public default double PrimaryRightStickYAxis() {
    return primaryController.getRightY();
  }

  public default Trigger PrimaryLeftBumper() {
    return new Trigger(() -> primaryController.getLeftBumper());
  }

  public default Trigger PrimaryRightBumper() {
    return new Trigger(() -> primaryController.getRightBumper());
  }

  public default double PrimaryLeftTrigger() {
    return primaryController.getLeftTriggerAxis();
  }

  public default double PrimaryRightTrigger() {
    return primaryController.getRightTriggerAxis();
  }
  // End

  // Override Controller
  XboxController overrideController = new XboxController(1);

  public default Trigger OverrideExtendArm() {
    return new Trigger(() -> overrideController.getAButton());
  }

  public default Trigger OverrideRetractArm() {
    return new Trigger(() -> overrideController.getBButton());
  }
}
