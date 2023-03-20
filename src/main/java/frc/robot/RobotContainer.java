// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.*;
import static frc.robot.subsystems.drivetrain.DrivetrainConstants.*;

import com.kauailabs.navx.frc.AHRS;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPoint;
import com.pathplanner.lib.commands.FollowPathWithEvents;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.lib.team3061.swerve.SwerveModule;
import frc.lib.team3061.swerve.SwerveModuleIO;
import frc.lib.team3061.swerve.SwerveModuleIOSim;
import frc.lib.team3061.swerve.SwerveModuleIOTalonFX;
import frc.robot.Constants.Mode;
import frc.robot.commands.TeleopSwerve;
import frc.robot.commands.autons.FeedForwardCharacterization;
import frc.robot.commands.autons.FeedForwardCharacterization.FeedForwardCharacterizationData;
import frc.robot.commands.pathplanner.FollowPath;
import frc.robot.commands.robot.LockArmExtend;
import frc.robot.commands.subautotele.pickup.PickupBack;
import frc.robot.commands.subautotele.pickup.PickupFront;
import frc.robot.commands.subautotele.pickup.PickupStation;
import frc.robot.commands.subautotele.score.cones.ScoreCN3;
import frc.robot.commands.subautotele.score.cubes.ScoreCB3;
import frc.robot.commands.subautotele.swerve.AutoBalanceV2;
import frc.robot.commands.subautotele.swerve.AutoBalanceV4;
import frc.robot.commands.subcommandsaux.extension.ArmExtension;
import frc.robot.commands.subcommandsaux.intake.IntakeCube;
import frc.robot.commands.subcommandsaux.intake.IntakeCubeAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeInAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeInOut;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.LockRobotArm;
import frc.robot.commands.subcommandsbase.LockRobotDrivetrain;
import frc.robot.commands.teleop.HomePos;
import frc.robot.commands.teleop.ScoreController;
import frc.robot.commands.teleop.StageController;
import frc.robot.commands.vision.AlignToAprilTagX;
import frc.robot.subsystems.auxiliary.AirCompressor;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSubsystem;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.limelight.Limelight;
import java.util.List;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private OperatorInterface oi = new OperatorInterface() {};

  public AirCompressor airCompressor;

  public Drivetrain drivetrain;

  // use AdvantageKit's LoggedDashboardChooser instead of SendableChooser to ensure accurate logging
  private final LoggedDashboardChooser<Command> autoChooser =
      new LoggedDashboardChooser<>("Auto Routine");

  // RobotContainer singleton
  private static RobotContainer robotContainer = new RobotContainer();
  private PivotSubsystem pivotSystem = new PivotSubsystem();
  private IntakeSystem intakeSystem = new IntakeSystem();

  /** Create the container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // new ArmExtension(intakeSystem, intakeSystem.GetArmExtendedPosition(), true).schedule();
    // create real, simulated, or replay subsystems based on the mode and robot specified
    if (Constants.getMode() != Mode.REPLAY) {
      switch (Constants.getRobot()) {
        case ROBOT_2022_PRESEASON:
          {
            AHRS gyro = new AHRS(SPI.Port.kMXP, (byte) 200);

            SwerveModule flModule =
                new SwerveModule(
                    new SwerveModuleIOTalonFX(
                        0,
                        FRONT_LEFT_MODULE_DRIVE_MOTOR,
                        FRONT_LEFT_MODULE_STEER_MOTOR,
                        FRONT_LEFT_MODULE_STEER_ENCODER,
                        FRONT_LEFT_MODULE_STEER_OFFSET),
                    0,
                    MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule frModule =
                new SwerveModule(
                    new SwerveModuleIOTalonFX(
                        1,
                        FRONT_RIGHT_MODULE_DRIVE_MOTOR,
                        FRONT_RIGHT_MODULE_STEER_MOTOR,
                        FRONT_RIGHT_MODULE_STEER_ENCODER,
                        FRONT_RIGHT_MODULE_STEER_OFFSET),
                    1,
                    MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule blModule =
                new SwerveModule(
                    new SwerveModuleIOTalonFX(
                        2,
                        BACK_LEFT_MODULE_DRIVE_MOTOR,
                        BACK_LEFT_MODULE_STEER_MOTOR,
                        BACK_LEFT_MODULE_STEER_ENCODER,
                        BACK_LEFT_MODULE_STEER_OFFSET),
                    2,
                    MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule brModule =
                new SwerveModule(
                    new SwerveModuleIOTalonFX(
                        3,
                        BACK_RIGHT_MODULE_DRIVE_MOTOR,
                        BACK_RIGHT_MODULE_STEER_MOTOR,
                        BACK_RIGHT_MODULE_STEER_ENCODER,
                        BACK_RIGHT_MODULE_STEER_OFFSET),
                    3,
                    MAX_VELOCITY_METERS_PER_SECOND);

            drivetrain = new Drivetrain(gyro, flModule, frModule, blModule, brModule);
            drivetrain.enableFieldRelative();
            break;
          }
        case ROBOT_SIMBOT:
          {
            SwerveModule flModule =
                new SwerveModule(new SwerveModuleIOSim(), 0, MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule frModule =
                new SwerveModule(new SwerveModuleIOSim(), 1, MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule blModule =
                new SwerveModule(new SwerveModuleIOSim(), 2, MAX_VELOCITY_METERS_PER_SECOND);

            SwerveModule brModule =
                new SwerveModule(new SwerveModuleIOSim(), 3, MAX_VELOCITY_METERS_PER_SECOND);
            drivetrain = new Drivetrain(new AHRS(), flModule, frModule, blModule, brModule);
            airCompressor = new AirCompressor();
            break;
          }
        default:
          break;
      }

    } else {
      SwerveModule flModule =
          new SwerveModule(new SwerveModuleIO() {}, 0, MAX_VELOCITY_METERS_PER_SECOND);

      SwerveModule frModule =
          new SwerveModule(new SwerveModuleIO() {}, 1, MAX_VELOCITY_METERS_PER_SECOND);

      SwerveModule blModule =
          new SwerveModule(new SwerveModuleIO() {}, 2, MAX_VELOCITY_METERS_PER_SECOND);

      SwerveModule brModule =
          new SwerveModule(new SwerveModuleIO() {}, 3, MAX_VELOCITY_METERS_PER_SECOND);
      drivetrain =
          new Drivetrain(
              new AHRS(SPI.Port.kMXP, (byte) 200) {}, flModule, frModule, blModule, brModule);
      airCompressor = new AirCompressor();
    }

    // disable all telemetry in the LiveWindow to reduce the processing during each iteration
    LiveWindow.disableAllTelemetry();

    updateOI();

    configureAutoCommands();
  }

  /**
   * This method scans for any changes to the connected joystick. If anything changed, it creates
   * new OI objects and binds all of the buttons to commands.
   */
  public void updateOI() {

    CommandScheduler.getInstance().getActiveButtonLoop().clear();
    // oi = OISelector.findOperatorInterface();

    /*
     * Set up the default command for the drivetrain. The joysticks' values map to percentage of the
     * maximum velocities. The velocities may be specified from either the robot's frame of
     * reference or the field's frame of reference. In the robot's frame of reference, the positive
     * x direction is forward; the positive y direction, left; position rotation, CCW. In the field
     * frame of reference, the origin of the field to the lower left corner (i.e., the corner of the
     * field to the driver's right). Zero degrees is away from the driver and increases in the CCW
     * direction. This is why the left joystick's y axis specifies the velocity in the x direction
     * and the left joystick's x axis specifies the velocity in the y direction.
     */
    drivetrain.setDefaultCommand(
        new TeleopSwerve(
            drivetrain,
            oi::PrimaryLeftStickYAxis,
            oi::PrimaryLeftStickXAxis,
            oi::PrimaryRightStickXAxis));

    intakeSystem.setDefaultCommand(new IntakeCube(intakeSystem, oi::PrimaryLeftTrigger));

    intakeSystem.setDefaultCommand(new IntakeCube(intakeSystem, oi::PrimaryLeftTrigger));

    configureButtonBindings();
  }

  /**
   * Factory method to create the singleton robot container object.
   *
   * @return the singleton robot container object
   */
  public static RobotContainer getInstance() {
    return robotContainer;
  }

  /** Use this method to define your button->command mappings. */
  private void configureButtonBindings() {

    // Primary Controller
    oi.PrimaryA().onTrue(new StageController(intakeSystem, pivotSystem, 1));
    oi.PrimaryX().onTrue(new StageController(intakeSystem, pivotSystem, 2));
    oi.PrimaryY().onTrue(new StageController(intakeSystem, pivotSystem, 3));
    oi.PrimaryB().onTrue(new StageController(intakeSystem, pivotSystem, 0));

    oi.PrimaryBack().onTrue(Commands.runOnce(drivetrain::zeroGyroscope, drivetrain));
    oi.PrimaryStart(); // Empty

    oi.PrimaryPOV0().onTrue(new PickupFront(intakeSystem, pivotSystem)); // Front Pickup Command
    oi.PrimaryPOV90().onTrue(new AlignToAprilTagX(drivetrain));
    oi.PrimaryPOV180().onTrue(new PickupBack(intakeSystem, pivotSystem)); // Back Pickup Command
    oi.PrimaryPOV270().onTrue(new PickupStation(intakeSystem, pivotSystem));

    oi.PrimaryLeftBumper().whileTrue(new IntakeInOut(intakeSystem, .75, false));
    oi.PrimaryRightBumper().onTrue(new ScoreController(intakeSystem, pivotSystem));
    // End

    // Override Controller
    oi.OverrideBack().onTrue(new LockRobotDrivetrain(drivetrain)); // End Drivetrain
    oi.OverrideStart().onTrue(new LockRobotArm(intakeSystem, pivotSystem)); // End Arm

    oi.OverrideA().onTrue(new LockArmExtend(Robot.lockSystem, true));
    oi.OverrideB().whileTrue(new IntakeInOut(intakeSystem, 0.75, true));

    oi.OverrideY().onTrue(Commands.runOnce(drivetrain::disableXstance, drivetrain));
    oi.OverrideX().onTrue(Commands.runOnce(drivetrain::enableXstance, drivetrain));
    // End
  }

  private PathPlannerTrajectory GenerateTrajectoryFromPath(
      String name, double maxVelocity, double maxAcceleration) {
    PathPlannerTrajectory traj = PathPlanner.loadPath(name, maxVelocity, maxAcceleration);
    return traj;
  }

  private double[] GenerateCorrection(double idealX, double idealY) {
    double x = Limelight.PositionOnFieldGrid().getX();
    double y = Limelight.PositionOnFieldGrid().getY();
    double[] array = {0, 0};
    if (x == 0.0 || y == 0.0) {
      array[0] = idealX;
      array[1] = idealY;
      return array;
    } else {
      array[0] = x;
      array[1] = y;
      return array;
    }
  }

  public void RobotInit() {
    new ArmExtension(intakeSystem, intakeSystem.GetArmExtendedPosition(), true).schedule();
  }

  private PathPlannerTrajectory GenerateCorrectionPath(
      double idealX, double idealY, double lastX, double lastY) {
    double[] correction = GenerateCorrection(idealX, idealY);
    PathPlannerTrajectory path =
        PathPlanner.generatePath(
            new PathConstraints(
                AUTO_MAX_SPEED_METERS_PER_SECOND, AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED),
            new PathPoint(
                new Translation2d(correction[0], correction[1]),
                new Rotation2d(Drivetrain.gyroIO.getYaw())),
            new PathPoint(
                new Translation2d(lastX, lastY), new Rotation2d(Drivetrain.gyroIO.getYaw())));
    return path;
  }

  /** Use this method to define your commands for autonomous mode. */
  private void configureAutoCommands() {
    AUTO_EVENT_MAP.put("event1", Commands.print("passed event marker 1"));

    AUTO_EVENT2_MAP.put("event1", Commands.print("passed marker 1"));

    AUTO_EVENT3_MAP.put("event1", Commands.print("passed marker 1"));

    M1L1P1_MAP.put("BPU", new PickupBack(intakeSystem, pivotSystem));

    FullPath_Map.put("BPUP1", new PickupBack(intakeSystem, pivotSystem).ignoringDisable(true));
    FullPath_Map.put("BPUP2", new PickupBack(intakeSystem, pivotSystem).ignoringDisable(true));

    List<PathPlannerTrajectory> p_FullPathRed =
        PathPlanner.loadPathGroup(
            "Red3",
            MAX_VELOCITY_METERS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    List<PathPlannerTrajectory> p_FullPathBlueCube =
        PathPlanner.loadPathGroup("Blue3CubeStart", 2.0, 2.0);
    List<PathPlannerTrajectory> p_FullPathBlueCone =
        PathPlanner.loadPathGroup("Blue3ConeStart", 1.0, 1.0);

    PathPlannerTrajectory p_1Meter =
        GenerateTrajectoryFromPath(
            "1Meter",
            MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    PathPlannerTrajectory p_3Meter =
        GenerateTrajectoryFromPath(
            "3Meter",
            MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    PathPlannerTrajectory p_5Meter =
        GenerateTrajectoryFromPath(
            "5Meter",
            MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    PathPlannerTrajectory ScoreBackup = GenerateTrajectoryFromPath("ScoreThenBackup", 1, 1);

    Command c_FullPathRed =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathRed.get(0), drivetrain, true),
                p_FullPathRed.get(0).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathRed.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathRed.get(2), drivetrain, false),
                p_FullPathRed.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathRed.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, pivotSystem));

    Command c_FullPathBlueCube =
        Commands.sequence(
            new ScoreCB3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCube.get(0), drivetrain, true),
                p_FullPathBlueCube.get(0).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathBlueCube.get(1), drivetrain, false),
            new ScoreCN3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCube.get(2), drivetrain, false),
                p_FullPathBlueCube.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathBlueCube.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, pivotSystem));

    Command c_FullPathBlueCone =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCone.get(0), drivetrain, true),
                p_FullPathBlueCone.get(0).getMarkers(),
                FullPath_Map),
            new IntakeCubeAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathBlueCone.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCone.get(2), drivetrain, false),
                p_FullPathBlueCone.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(intakeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathBlueCone.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, pivotSystem));

    Command scoreBackup =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem), new FollowPath(ScoreBackup, drivetrain, true));

    PathPlannerTrajectory score = GenerateTrajectoryFromPath("Score", 1.0, 1.0);

    PathPlannerTrajectory go = GenerateTrajectoryFromPath("RobotControlPath", 1.0, 1.0);
    Command gogo =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem),
            new FollowPath(go, drivetrain, true),
            new PickupFront(intakeSystem, pivotSystem),
            new IntakeInAuto(intakeSystem), // Auto Intake Refine to auton mode
            new HomePos(intakeSystem, pivotSystem));

    Command scoreHighCone =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem),
            new FollowPath(score, drivetrain, true),
            new AutoBalanceV2(drivetrain, false, 3.0, -13.0, 33.0),
            new LockArmExtend(Robot.lockSystem, true));

    Command scoreHighBalance =
        Commands.sequence(
            new ScoreCN3(intakeSystem, pivotSystem), new AutoBalanceV4(drivetrain, true, 1.0, -20));

    PathPlannerTrajectory testPath =
        GenerateTrajectoryFromPath(
            "testPath",
            AUTO_MAX_SPEED_METERS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

    Command autoTestPath =
        Commands.sequence(
            new FollowPath(testPath, drivetrain, true),
            new FollowPath(
                GenerateCorrectionPath(
                    0, 0, drivetrain.getPose().getX(), drivetrain.getPose().getY()),
                drivetrain,
                TUNING_MODE));

    // add commands to the auto chooser
    autoChooser.addDefaultOption("Do Nothing", new InstantCommand());

    autoChooser.addOption("Balance", new AutoBalanceV4(drivetrain, true, 1.0, -20));
    autoChooser.addOption("TestPath", autoTestPath);
    autoChooser.addOption("ScoreHighAutoBalance", scoreHighCone);
    autoChooser.addOption("Blue_ScoreHighAndGrab", gogo);
    autoChooser.addOption("Red_MakeLink", c_FullPathRed);
    autoChooser.addOption("Blue_MakeLinkCubeStart", c_FullPathBlueCube);
    autoChooser.addOption("Blue_MakeLinkConeStart", c_FullPathBlueCone);
    autoChooser.addOption("AutoBalance", scoreHighBalance);
    autoChooser.addOption("ScoreAndBackup", scoreBackup);

    // "auto" command for tuning the drive velocity PID
    autoChooser.addOption(
        "Drive Velocity Tuning",
        Commands.sequence(
            Commands.runOnce(drivetrain::disableFieldRelative, drivetrain),
            Commands.deadline(
                Commands.waitSeconds(5.0),
                Commands.run(() -> drivetrain.drive(1.5, 0.0, 0.0), drivetrain))));

    // "auto" command for characterizing the drivetrain
    autoChooser.addOption(
        "Drive Characterization",
        new FeedForwardCharacterization(
            drivetrain,
            true,
            new FeedForwardCharacterizationData("drive"),
            drivetrain::runCharacterizationVolts,
            drivetrain::getCharacterizationVelocity));

    autoChooser.addOption("1Meter", new FollowPath(p_1Meter, drivetrain, true));
    autoChooser.addOption("3Meter", new FollowPath(p_3Meter, drivetrain, true));
    autoChooser.addOption("5Meter", new FollowPath(p_5Meter, drivetrain, true));
    Shuffleboard.getTab("MAIN").add(autoChooser.getSendableChooser());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    SmartDashboard.putData(drivetrain);
    return autoChooser.get();
  }
}
