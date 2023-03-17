// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Initially from https://github.com/Mechanical-Advantage/RobotCode2022
 */

package frc.robot;

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

  public default Trigger OverrideA() {
    return new Trigger(() -> overrideController.getAButton());
  }

  public default Trigger OverrideB() {
    return new Trigger(() -> overrideController.getBButton());
  }

  public default Trigger OverrideX() {
    return new Trigger(() -> overrideController.getXButton());
  }

  public default Trigger OverrideY() {
    return new Trigger(() -> overrideController.getYButton());
  }

  public default Trigger OverrideStart() {
    return new Trigger(() -> overrideController.getStartButton());
  }

  public default Trigger OverrideBack() {
    return new Trigger(() -> overrideController.getBackButton());
  }

  public default POVButton OverridePOV0() {
    return new POVButton(overrideController, 0);
  }

  public default POVButton OverridePOV90() {
    return new POVButton(overrideController, 90);
  }

  public default POVButton OverridePOV180() {
    return new POVButton(overrideController, 180);
  }

  public default POVButton OverridePOV270() {
    return new POVButton(overrideController, 270);
  }

  public default double OverrideLeftStickXAxis() {
    return overrideController.getLeftX();
  }

  public default double OverrideLeftStickYAxis() {
    return overrideController.getLeftY();
  }

  public default double OverrideRightStickXAxis() {
    return overrideController.getRightX();
  }

  public default double OverrideRightStickYAxis() {
    return overrideController.getRightY();
  }

  public default Trigger OverrideLeftBumper() {
    return new Trigger(() -> overrideController.getLeftBumper());
  }

  public default Trigger OverrideRightBumper() {
    return new Trigger(() -> overrideController.getRightBumper());
  }

  public default double OverrideLeftTrigger() {
    return overrideController.getLeftTriggerAxis();
  }

  public default double OverrideRightTrigger() {
    return overrideController.getRightTriggerAxis();
  }
}
