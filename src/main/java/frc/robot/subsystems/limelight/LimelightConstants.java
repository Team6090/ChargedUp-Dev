package frc.robot.subsystems.limelight;

public final class LimelightConstants {
  public static final double LIMELIGHT_HEIGHT_FROM_GROUND = .335;
  public static final double LIMELIGHT_ANGLE_OF_ELEVATION = 1.0;
  public static final double LIMELIGHT_OFFSET_DEPTH = 0.33;

  public static final double HEIGHT_DIFFERENCE_GRID =
      AprilTags.APRIL_TAG_GRID_HEIGHT_FROM_GROUND - LIMELIGHT_HEIGHT_FROM_GROUND;
  public static final double HEIGHT_DIFFERENCE_SUBSTATION =
      AprilTags.APRIL_TAG_SUBSTATION_HEIGHT_FROM_GROUND - LIMELIGHT_HEIGHT_FROM_GROUND;

  public static class AprilTags {
    public static final double APRIL_TAG_GRID_HEIGHT_FROM_GROUND = .46;
    public static final double APRIL_TAG_SUBSTATION_HEIGHT_FROM_GROUND = .67;
  }
}
