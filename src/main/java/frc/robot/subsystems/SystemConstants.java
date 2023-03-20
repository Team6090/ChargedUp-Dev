package frc.robot.subsystems;

/** System Constants hold all constants for Super Systems and Subsystems
 * 
 *  @see SuperSystems, Subsystems
 * 
 *  @category class
 * 
 *  @version 1.0.0
 * 
 *  @since 1.0.0
 * 
 *  @author AFR
 */
public final class SystemConstants {

  /** Auxiliary SuperSystem Constants
   * 
   *  @category SuperSystem Constants
   * 
   *  @version 1.0.0
   * 
   *  @since 1.0.0
   * 
   *  @author AFR
   */
  public static final class AuxiliaryConstants {

    /** Enables shuffleboard tab for Auxiliary SuperSystem
     * 
     *  @category boolean
     * 
     *  @see shuffleboard_tab
     * 
     *  @version 1.0.0
     * 
     *  @since 1.0.0
     * 
     *  @author AFR
     */
    public static final boolean AUXILIARY_SHUFFLEBOARD_TAB_ENABLED = false;

    /** PivotSubsystem Constants
     * 
     *  @category Subsystem Constants Class
     * 
     *  @see PivotSubsystem
     * 
     *  @version 1.0.0
     * 
     *  @since 1.0.0
     * 
     *  @author AFR
     */
    public static final class PivotConstants {

      /** Enables debug settings for PivotSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean PIVOT_DEBUG_MODE_ENABLED = false; 

      /** Enables periodic logging for PivotSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean PIVOT_PERIODIC_LOGGING_ENABLED = false; 
    }

    /** PivotSubsystem Constants
     * 
     *  @category Subsystem Constants Class
     * 
     *  @see IntakeSubsystem
     * 
     *  @version 1.0.0
     * 
     *  @since 1.0.0
     * 
     *  @author AFR
     */
    public static final class IntakeConstants {

      /** Enables debug settings for IntakeSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean INTAKE_DEBUG_MODE_ENABLED = false;
      
      /** Enables periodic logging for IntakeSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean INTAKE_PERIODIC_LOGGING_ENABLED = false;
    }

    /** TelescopeSubsystem Constants
     * 
     *  @category Subsystem Constants Class
     * 
     *  @see TelescopeSubsystem
     * 
     *  @version 1.0.0
     * 
     *  @since 1.0.0
     * 
     *  @author AFR
     */
    public static final class TelescopeConstants {

      /** Enables debug settings for TelescopeSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public final boolean TELESCOPE_DEBUG_MODE_ENABLED = false;
      
      /** Enables periodic logging for TelescopeSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public final boolean TELESCOPE_PERIODIC_LOGGING_ENABLED = false;

      /** Telescope Motor CAN ID */
      public final int TELESCOPE_MOTOR_ID = 50;
      
      /** Telescope Encoder CAN ID */
      public final int TELESCOPE_ENCODER_ID = 52;

    }
  }
 
  /** Drivetrain Constants
   * 
   *  @category SuperSystem Constants
   * 
   *  @version 1.0.0
   * 
   *  @since 1.0.0
   * 
   *  @author AFR
   */
  public static final class DrivetrainConstants {

      /** Enables shuffleboard tab for Drivetrain SuperSystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean DRIVETRAIN_SHUFFLEBOARD_TAB_ENABLED = false;
    
      /** Enables debug settings for DrivetrainSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean DRIVETRAIN_DEBUG_MODE_ENABLED = false;
      
      /** Enables periodic logging for DrivetrainSubsystem
      * 
      *  @category boolean
      * 
      *  @version 1.0.0
      * 
      *  @since 1.0.0
      * 
      *  @author AFR
      */
      public static final boolean DRIVETRAIN_PERIODIC_LOGGING_ENABLED = false;

  }
}
