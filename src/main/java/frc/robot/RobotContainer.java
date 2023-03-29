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
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
import frc.robot.commands.subcommandsaux.extension.ArmExtensionToNextIn;
import frc.robot.commands.subcommandsaux.intake.IntakeCube;
import frc.robot.commands.subcommandsaux.intake.IntakeCubeAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeCubeInfinite;
import frc.robot.commands.subcommandsaux.intake.IntakeInAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeInOut;
import frc.robot.commands.subcommandsaux.intake.IntakeInOutAuto;
import frc.robot.commands.subcommandsaux.intake.IntakeOpenClose;
import frc.robot.commands.subcommandsaux.intake.IntakeStopAuto;
import frc.robot.commands.subcommandsaux.pivot.PivotArmToNextDown;
import frc.robot.commands.subcommandsaux.pivot.PivotArmToNextUp;
import frc.robot.commands.subcommandsaux.pivot.PivotMove;
import frc.robot.commands.subcommandsaux.util.PickupStationFeed;
import frc.robot.commands.teleop.HomePos;
import frc.robot.commands.teleop.ScoreController;
import frc.robot.commands.teleop.StageController;
import frc.robot.commands.teleop.stage.cone.ConeStage3Auto;
import frc.robot.commands.vision.AlignToAprilTagX;
import frc.robot.operator_interface.OISelector;
import frc.robot.operator_interface.OperatorInterface;
import frc.robot.subsystems.auxiliary.AirCompressor;
import frc.robot.subsystems.auxiliary.IntakeSystem;
import frc.robot.subsystems.auxiliary.PivotSystem;
import frc.robot.subsystems.auxiliary.TelescopeSystem;
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
  private PivotSystem pivotSystem = new PivotSystem();
  private IntakeSystem intakeSystem = new IntakeSystem();
  private TelescopeSystem telescopeSystem = new TelescopeSystem();

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

  public void teleopInit() {
    drivetrain.disableXstance();
  }

  /**
   * This method scans for any changes to the connected joystick. If anything changed, it creates
   * new OI objects and binds all of the buttons to commands.
   */
  public void updateOI() {
    if (!OISelector.didJoysticksChange()) {
      return;
    }

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
    oi.PrimaryA().onTrue(new StageController(intakeSystem, telescopeSystem, pivotSystem, 1));
    oi.PrimaryX().onTrue(new StageController(intakeSystem, telescopeSystem, pivotSystem, 2));
    oi.PrimaryY().onTrue(new StageController(intakeSystem, telescopeSystem, pivotSystem, 3));
    oi.PrimaryB().onTrue(new StageController(intakeSystem, telescopeSystem, pivotSystem, 0));

    oi.PrimaryBack().onTrue(Commands.runOnce(drivetrain::zeroGyroscope, drivetrain));
    // oi.PrimaryStart().onTrue(new AlignToAprilTagX(drivetrain));

    oi.PrimaryPOV0()
        .onTrue(
            new PickupFront(intakeSystem, telescopeSystem, pivotSystem)); // Front Pickup Command
    oi.PrimaryPOV90().onTrue(new AlignToAprilTagX(drivetrain));
    oi.PrimaryPOV180()
        .onTrue(new PickupBack(intakeSystem, telescopeSystem, pivotSystem)); // Back Pickup Command
    oi.PrimaryPOV270().onTrue(new PickupStation(intakeSystem, telescopeSystem, pivotSystem));

    oi.PrimaryLeftBumper().whileTrue(new IntakeInOut(intakeSystem, .75, true));
    oi.PrimaryRightBumper().onTrue(new ScoreController(intakeSystem, telescopeSystem, pivotSystem));
    // End

    // Override Controller
    // oi.OverrideBack().onTrue(new LockRobotArm(intakeSystem, pivotSystem)); // End Drivetrain
    // oi.OverrideStart().onTrue(new LockRobotArm(intakeSystem, pivotSystem)); // End Arm

    // oi.OverrideA().onTrue(new CubeScore(intakeSystem, pivotSystem));
    oi.OverrideB().whileTrue(new IntakeInOut(intakeSystem, 0.75, false));

    oi.OverrideY().onTrue(Commands.runOnce(drivetrain::disableXstance, drivetrain));
    oi.OverrideX().onTrue(Commands.runOnce(drivetrain::enableXstance, drivetrain));

    oi.OverrideStart().onTrue(new LockArmExtend(Robot.lockSystem, true));
    oi.OverrideBack().onTrue(new LockArmExtend(Robot.lockSystem, false));

    // oi.OverrideRightBumper().onTrue(new ArmExtension(telescopeSystem, 15000, true));
    // oi.OverrideLeftBumper().onTrue(new ArmExtensionTest(telescopeSystem, 15000, true));
    // oi.OverrideA().onTrue(new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true));

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
    new ArmExtension(telescopeSystem, telescopeSystem.GetArmExtendedPosition(), true).schedule();
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

    M1L1P1_MAP.put("BPU", new PickupBack(intakeSystem, telescopeSystem, pivotSystem));

    FullPath_Map.put(
        "BPUP1", new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true));
    FullPath_Map.put(
        "BPUP2", new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true));
    FullPath_Map.put(
        "ININ1",
        Commands.sequence(
            new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true),
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem)));

    CB_2_LeftBlue_Map.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    CB_2_LeftBlue_Map.put(
        "CB3_STAGE",
        Commands.sequence(
            new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
            new PivotArmToNextDown(pivotSystem, 106.5, 170, true).ignoringDisable(true),
            new ArmExtension(telescopeSystem, 24515, true).ignoringDisable(true)));

    CB_2_RightRed_Map.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    CB_2_RightRed_Map.put(
        "CB3_STAGE",
        Commands.sequence(
            new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
            new PivotArmToNextDown(pivotSystem, 106.5, 170, true).ignoringDisable(true),
            new ArmExtension(telescopeSystem, 24515, true).ignoringDisable(true)));
    CB_2_5_LeftBlue_Map.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));
    CB_2_5_LeftBlue_Map.put(
        "B_PICKUP2",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    CB_2_5_LeftBlue_Map.put(
        "CB3_STAGE",
        Commands.sequence(
            new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
            new PivotArmToNextDown(pivotSystem, 108.5, 170, true).ignoringDisable(true),
            new ArmExtension(telescopeSystem, 24515, true).ignoringDisable(true)));

    CB_2_5_RightRed_Map.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));
    CB_2_5_RightRed_Map.put(
        "B_PICKUP2",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    CB_2_5_RightRed_Map.put(
        "CB3_STAGE",
        Commands.sequence(
            new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
            new PivotArmToNextDown(pivotSystem, 110.5, 170, true).ignoringDisable(true),
            new ArmExtension(telescopeSystem, 24515, true).ignoringDisable(true)));
    LeftBlue_AB_2.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    LeftBlue_AB_2.put(
        "HOME",
        Commands.sequence(
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 37, true)));

    CubeOverrun_Map.put(
        "HOME",
        Commands.sequence(
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 30, true)));

    CubeOverrun_Map.put(
        "F_PICKUP",
        Commands.parallel(
            new PickupFront(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(0.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));

    RightBlue_2.put(
        "B_PICKUP",
        Commands.parallel(
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem).ignoringDisable(true),
            Commands.sequence(
                new WaitCommand(.25),
                new IntakeCubeInfinite(intakeSystem, true).ignoringDisable(true))));
    RightBlue_2.put(
        "CB3_STAGE",
        Commands.sequence(
            new ArmExtension(telescopeSystem, Constants.EXTEND_HOME_POS, true),
            new PivotArmToNextDown(pivotSystem, 108.5, 170, true).ignoringDisable(true),
            new ArmExtension(telescopeSystem, 24515, true).ignoringDisable(true)));
    List<PathPlannerTrajectory> p_FullPathRed =
        PathPlanner.loadPathGroup(
            "Red3",
            MAX_VELOCITY_METERS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    List<PathPlannerTrajectory> p_FullPathBlueCube =
        PathPlanner.loadPathGroup("Blue3CubeStart", 2.0, 2.0);
    List<PathPlannerTrajectory> p_FullPathBlueCone =
        PathPlanner.loadPathGroup("Blue3ConeStart", 1.0, 1.0);
    List<PathPlannerTrajectory> p_2PieceLeftBlue =
        PathPlanner.loadPathGroup("LeftBlue2Piece", 1.7, 1.7);
    List<PathPlannerTrajectory> p_2PieceRightBlue =
        PathPlanner.loadPathGroup("RightBlue2Piece", 2.0, 2.0);
    List<PathPlannerTrajectory> p_2PieceLeftRed =
        PathPlanner.loadPathGroup("LeftRed2Piece", 2.0, 2.0);
    List<PathPlannerTrajectory> p_2PieceRightRed =
        PathPlanner.loadPathGroup("RightRed2Piece", 2.0, 2.0);
    List<PathPlannerTrajectory> p_CubeOverrun =
        PathPlanner.loadPathGroup("p_CubeOverrun", 1.0, 1.0);
    PathPlannerTrajectory p_1_5PieceLeftBlue = PathPlanner.loadPath("LeftBlue1.5Piece", 1.7, 1.7);
    PathPlannerTrajectory p_LeftBlueAroundBalance =
        PathPlanner.loadPath("LeftBlueAroundBalance", 2.5, 2.5);
    PathPlannerTrajectory p_RightRedAroundBalance =
        PathPlanner.loadPath("RightRedAroundBalance", 2.5, 2.5);
    List<PathPlannerTrajectory> p_CB_2_LeftBlue =
        PathPlanner.loadPathGroup("p_CB_2_LeftBlue", 2.4, 2.4);
    List<PathPlannerTrajectory> p_CB_2_5_LeftBlue =
        PathPlanner.loadPathGroup("p_CB_2_5_LeftBlue", 2.4, 2.4);
    List<PathPlannerTrajectory> p_CB_2_RightRed =
        PathPlanner.loadPathGroup("p_CB_2_RightRed", 2.4, 2.4);
    List<PathPlannerTrajectory> p_CB_2_5_RightRed =
        PathPlanner.loadPathGroup("p_CB_2_5_RightRed", 2.4, 2.4);
    PathPlannerTrajectory p_OverChargeStation = PathPlanner.loadPath("OverChargeStation", 1.0, 1.0);
    List<PathPlannerTrajectory> p_LeftBlue_AB_2 =
        PathPlanner.loadPathGroup("LeftBlue_AB_2", 1.5, 1.5);
    List<PathPlannerTrajectory> p_2_RightBlue =
        PathPlanner.loadPathGroup("p_2_RightBlue", 1.0, 1.0);

    Command c_2_RightBlue =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.1),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_2_RightBlue.get(0), drivetrain, true),
                p_2_RightBlue.get(0).getMarkers(),
                RightBlue_2),
            new IntakeStopAuto(intakeSystem),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new FollowPathWithEvents(
                new FollowPath(p_2_RightBlue.get(1), drivetrain, false),
                p_2_RightBlue.get(1).getMarkers(),
                RightBlue_2),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.2),
            new IntakeOpenClose(intakeSystem, true),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 37, true),
            new PickupStationFeed(pivotSystem, 0));
    autoChooser.addOption("c_2_RightBlue", c_2_RightBlue);

    Command c_21_ConeCharge =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.1),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_OverChargeStation, drivetrain, true),
            new AutoBalanceV4(drivetrain, false, 1.0, 20));
    autoChooser.addDefaultOption("c_21_ConeCharge", c_21_ConeCharge);

    Command c_LeftBlue_AB_2 =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.1),
            new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_LeftBlue_AB_2.get(0), drivetrain, true),
                p_LeftBlue_AB_2.get(0).getMarkers(),
                LeftBlue_AB_2),
            new IntakeStopAuto(intakeSystem),
            new IntakeOpenClose(intakeSystem, true),
            new FollowPathWithEvents(
                new FollowPath(p_LeftBlue_AB_2.get(1), drivetrain, false),
                p_LeftBlue_AB_2.get(1).getMarkers(),
                LeftBlue_AB_2),
            new PickupStationFeed(pivotSystem, 0),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
            new AutoBalanceV4(drivetrain, true, 1.0, -20));

    // Command c_SyncAutoPath =
    //     Commands.sequence(
    //       new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
    //       new FollowPathWithEvents(
    //         new FollowPath(p_SyncAutoPath, drivetrain, true),
    //         p_SyncAutoPath.getMarkers(), SyncAutoPath_Map)
    //     );
    // autoChooser.addOption("SyncAutoPath", c_SyncAutoPath);
    PathPlannerTrajectory p_1Meter = GenerateTrajectoryFromPath("1Meter", 1.0, 1.0);
    PathPlannerTrajectory p_3Meter =
        GenerateTrajectoryFromPath(
            "3Meter",
            MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);
    PathPlannerTrajectory p_5Meter = GenerateTrajectoryFromPath("5Meter", 3.0, 3.0);
    PathPlannerTrajectory ScoreBackup = GenerateTrajectoryFromPath("ScoreThenBackup", 1.5, 1.5);

    Command c_FullPathRed =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathRed.get(0), drivetrain, true),
                p_FullPathRed.get(0).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathRed.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathRed.get(2), drivetrain, false),
                p_FullPathRed.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathRed.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_FullPathBlueCube =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCube.get(0), drivetrain, true),
                p_FullPathBlueCube.get(0).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathBlueCube.get(1), drivetrain, false),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCube.get(2), drivetrain, false),
                p_FullPathBlueCube.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathBlueCube.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_FullPathBlueCone =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCone.get(0), drivetrain, true),
                p_FullPathBlueCone.get(0).getMarkers(),
                FullPath_Map),
            new IntakeCubeAuto(intakeSystem, true),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 30, true),
            new FollowPath(p_FullPathBlueCone.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_FullPathBlueCone.get(2), drivetrain, false),
                p_FullPathBlueCone.get(2).getMarkers(),
                FullPath_Map),
            new IntakeInAuto(intakeSystem),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_FullPathBlueCone.get(3), drivetrain, false),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_2PieceLeftBlue =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_2PieceLeftBlue.get(0), drivetrain, true),
                p_2PieceLeftBlue.get(0).getMarkers(),
                FullPath_Map),
            new IntakeStopAuto(intakeSystem),
            new HomePos(telescopeSystem, pivotSystem),
            new FollowPath(p_2PieceLeftBlue.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_2PieceRightBlue =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_2PieceRightBlue.get(0), drivetrain, true),
            new IntakeCubeAuto(intakeSystem, true),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_2PieceRightBlue.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_2PieceLeftRed =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_2PieceLeftRed.get(0), drivetrain, true),
            new IntakeCubeAuto(intakeSystem, true),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_2PieceLeftRed.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem));

    Command c_2PieceRightRed =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new PickupBack(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_2PieceRightRed.get(0), drivetrain, true),
            new IntakeCubeAuto(intakeSystem, true),
            new ArmExtension(telescopeSystem, 50, true),
            new PivotMove(pivotSystem, 33, true),
            new FollowPath(p_2PieceRightRed.get(1), drivetrain, false),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem));

    // Command c_1_5PieceLeftBlue =
    //     Commands.sequence(
    //         new LockArmExtend(Robot.lockSystem, false),
    //         new WaitCommand(0.5),
    //         new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
    //         // new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
    //         new FollowPathWithEvents(new FollowPath(p_SyncAutoPath, drivetrain, true),
    // p_SyncAutoPath.getMarkers(), SyncAutoPath_Map),
    //         // new FollowPath(p_1_5PieceLeftBlue, drivetrain, true),
    //         Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
    //         new IntakeStopAuto(intakeSystem),
    //         new FollowPathWithEvents(new FollowPath(p_SyncAutoPath2, drivetrain, false),
    // p_SyncAutoPath2.getMarkers(), SyncAutoPath_Map2),
    //         new CubeScore(intakeSystem, telescopeSystem, pivotSystem) // ERROR: Causing arm to
    // throw back and slam into ground
    //     );

    Command c_CB_2_LeftBlue =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.15),
            new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_LeftBlue.get(0), drivetrain, true),
                p_CB_2_LeftBlue.get(0).getMarkers(),
                CB_2_LeftBlue_Map),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
            new IntakeStopAuto(intakeSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_LeftBlue.get(1), drivetrain, false),
                p_CB_2_LeftBlue.get(1).getMarkers(),
                CB_2_LeftBlue_Map),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.2),
            new IntakeOpenClose(intakeSystem, true),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 30, true),
            new PickupStationFeed(pivotSystem, 0));

    Command c_CB_2_RightRed =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.15),
            new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_RightRed.get(0), drivetrain, true),
                p_CB_2_RightRed.get(0).getMarkers(),
                CB_2_RightRed_Map),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
            new IntakeStopAuto(intakeSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_RightRed.get(1), drivetrain, false),
                p_CB_2_RightRed.get(1).getMarkers(),
                CB_2_RightRed_Map),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.2),
            new IntakeOpenClose(intakeSystem, true),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 30, true),
            new PickupStationFeed(pivotSystem, 0));
    Command c_CB_2_5_LeftBlue =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.15),
            new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_LeftBlue.get(0), drivetrain, true),
                p_CB_2_5_LeftBlue.get(0).getMarkers(),
                CB_2_5_LeftBlue_Map),
            new IntakeStopAuto(intakeSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_LeftBlue.get(1), drivetrain, false),
                p_CB_2_5_LeftBlue.get(1).getMarkers(),
                CB_2_5_LeftBlue_Map),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.2),
            new IntakeOpenClose(intakeSystem, true),
            // new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            // new PivotMove(pivotSystem, 30, true),
            // new PickupStationFeed(pivotSystem, 0),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_LeftBlue.get(2), drivetrain, false),
                p_CB_2_5_LeftBlue.get(2).getMarkers(),
                CB_2_5_LeftBlue_Map),
            new IntakeStopAuto(intakeSystem),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 37, true),
            new PickupStationFeed(pivotSystem, 0),
            new FollowPath(p_CB_2_5_LeftBlue.get(3), drivetrain, false),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain));

    Command c_CB_2_5_RightRed =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.15),
            new ConeStage3Auto(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_RightRed.get(0), drivetrain, true),
                p_CB_2_5_RightRed.get(0).getMarkers(),
                CB_2_5_RightRed_Map),
            new IntakeStopAuto(intakeSystem),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_RightRed.get(1), drivetrain, false),
                p_CB_2_5_RightRed.get(1).getMarkers(),
                CB_2_5_RightRed_Map),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.2),
            new IntakeOpenClose(intakeSystem, true),
            // new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            // new PivotMove(pivotSystem, 30, true),
            // new PickupStationFeed(pivotSystem, 0),
            new FollowPathWithEvents(
                new FollowPath(p_CB_2_5_RightRed.get(2), drivetrain, false),
                p_CB_2_5_LeftBlue.get(2).getMarkers(),
                CB_2_5_LeftBlue_Map),
            new IntakeStopAuto(intakeSystem),
            new ArmExtensionToNextIn(telescopeSystem, Constants.EXTEND_HOME_POS, 5000, true),
            new PivotMove(pivotSystem, 37, true),
            new PickupStationFeed(pivotSystem, 0),
            new FollowPath(p_CB_2_5_RightRed.get(3), drivetrain, false),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain));
    Command c_CubeOverrun =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.15),
            new PivotArmToNextUp(pivotSystem, 250, 200, true),
            new IntakeOpenClose(intakeSystem, false),
            new IntakeInOutAuto(intakeSystem),
            new WaitCommand(0.1),
            new IntakeOpenClose(intakeSystem, true),
            new FollowPathWithEvents(
                new FollowPath(p_CubeOverrun.get(0), drivetrain, true),
                p_CubeOverrun.get(0).getMarkers(),
                CubeOverrun_Map),
            new FollowPathWithEvents(
                new FollowPath(p_CubeOverrun.get(1), drivetrain, false),
                p_CubeOverrun.get(1).getMarkers(),
                CubeOverrun_Map),
            new AutoBalanceV4(drivetrain, true, 1.0, 20.0) // TODO: Check if proper
            );

    Command c_LeftBlueAroundBalance =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.25),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_LeftBlueAroundBalance, drivetrain, true),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
            new AutoBalanceV4(drivetrain, true, 1.0, -20));

    Command scoreBackup =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(ScoreBackup, drivetrain, true),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain));

    PathPlannerTrajectory score = GenerateTrajectoryFromPath("Score", 1.0, 1.0);

    PathPlannerTrajectory go = GenerateTrajectoryFromPath("RobotControlPath", 1.0, 1.0);
    Command gogo =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(go, drivetrain, true),
            new PickupFront(intakeSystem, telescopeSystem, pivotSystem),
            new IntakeInAuto(intakeSystem), // Auto Intake Refine to auton mode
            new HomePos(telescopeSystem, pivotSystem));

    Command scoreHighCone =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(score, drivetrain, true),
            new AutoBalanceV2(drivetrain, false, 3.0, -13.0, 33.0),
            new LockArmExtend(Robot.lockSystem, true));

    Command CN3_AutoBalance =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new AutoBalanceV4(drivetrain, true, 1.0, -20));

    PathPlannerTrajectory testPath =
        GenerateTrajectoryFromPath(
            "testPath",
            AUTO_MAX_SPEED_METERS_PER_SECOND,
            AUTO_MAX_ACCELERATION_METERS_PER_SECOND_SQUARED);

    Command c_RightRedAroundBalance =
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.25),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem),
            new FollowPath(p_RightRedAroundBalance, drivetrain, true),
            Commands.runOnce(drivetrain::zeroGyroscope, drivetrain),
            new AutoBalanceV4(drivetrain, true, 1.0, -20));

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

    // autoChooser.addOption("Balance", new AutoBalanceV4(drivetrain, true, 1.0, -20));
    // autoChooser.addOption("TestPath", autoTestPath);
    // autoChooser.addOption("ScoreHighAutoBalance", scoreHighCone);
    autoChooser.addOption("Blue_ScoreHighAndGrab", gogo);
    autoChooser.addOption("Red_MakeLink", c_FullPathRed);
    autoChooser.addOption("Blue_MakeLinkCubeStart", c_FullPathBlueCube);
    autoChooser.addOption("Blue_MakeLinkConeStart", c_FullPathBlueCone);
    autoChooser.addOption("2PieceLeftBlue", c_2PieceLeftBlue);
    autoChooser.addOption("c_CB_2_LeftBlue", c_CB_2_LeftBlue);
    autoChooser.addOption("c_CB_2_5_LeftBlue", c_CB_2_5_LeftBlue);
    autoChooser.addOption("c_CB_2_RightRed", c_CB_2_RightRed);
    autoChooser.addOption("c_CB_2_5_RightRed", c_CB_2_5_RightRed);
    autoChooser.addOption("2PieceRightBlue", c_2PieceRightBlue);
    autoChooser.addOption("2PieceLeftRed", c_2PieceLeftRed);
    autoChooser.addOption("2PieceRightRed", c_2PieceRightRed);
    autoChooser.addOption("CN3_AutoBalance", CN3_AutoBalance);
    autoChooser.addOption("ScoreAndBackup", scoreBackup);
    autoChooser.addOption("CubeOverrun", c_CubeOverrun);
    autoChooser.addOption("c_LeftBlue_AB_2", c_LeftBlue_AB_2);
    autoChooser.addOption(
        "ScoreConeHighOnly",
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCN3(intakeSystem, telescopeSystem, pivotSystem)));
    autoChooser.addOption(
        "ScoreCubeHighOnly",
        Commands.sequence(
            new LockArmExtend(Robot.lockSystem, false),
            new WaitCommand(0.5),
            new ScoreCB3(intakeSystem, telescopeSystem, pivotSystem)));
    autoChooser.addOption("LeftBlue_AroundBalance", c_LeftBlueAroundBalance);
    autoChooser.addOption("RightRedAroundBalance", c_RightRedAroundBalance);

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
