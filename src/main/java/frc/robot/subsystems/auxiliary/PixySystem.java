package frc.robot.subsystems.auxiliary;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.*;
import java.util.ArrayList;

public class PixySystem {

  private static final int blockSignatureOne = 1;
  private static final int blockSignatureTwo = 2;

  public static int coneX;
  public static int coneY;
  public static int coneHeight;
  public static int coneWidth;
  public static int cubeX;
  public static int cubeY;
  public static int cubeHeight;
  public static int cubeWidth;

  private Pixy2 pixy;
  public static Pixy2CCC pixy2ccc;

  public PixySystem() {
    this(new SPILink());
    pixy2ccc = pixy.getCCC();
  }

  public PixySystem(Link link) {
    pixy = Pixy2.createInstance(link);
    pixy.init();
    pixy2ccc = pixy.getCCC();
  }

  public static void GetCones() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG1, 5);
    if (blockCount <= 0) {
      System.err.print("No Block Count");
      SmartDashboard.putNumber("Cone Count", blockCount);
      return;
    } else {
      SmartDashboard.putNumber("Cone Count", blockCount);
    }
    ArrayList<Block> blocks = pixy2ccc.getBlockCache();
    Block largestBlock = null;
    if (blocks == null) {
      System.err.println("No Blocks");
      return;
    }
    for (Block block : blocks) {
      if (block.getSignature() == blockSignatureOne) {
        if (largestBlock == null) {
          largestBlock = block;
        } else if (block.getWidth() > largestBlock.getWidth()) {
          largestBlock = block;
        }
      }
    }
    if (largestBlock != null) {
      coneX = largestBlock.getX();
      coneY = largestBlock.getY();
      coneHeight = largestBlock.getHeight();
      coneWidth = largestBlock.getWidth();
    }
  }

  public static int GetCubeCount() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG2, 1);
    SmartDashboard.putNumber("Cube Count", blockCount);
    return blockCount;
  }

  public static int GetConeCount() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG1, 1);
    SmartDashboard.putNumber("Cone Count", blockCount);
    return blockCount;
  }

  public static int GetObject() {
    if (GetConeCount() > GetConeCount()) {
      return 1; // Cone
    } else if (GetConeCount() < GetCubeCount()) {
      return 2; // Cube
    } else {
      return -1; // Null
    }
  }

  public static void GetCubes() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG2, 5);
    if (blockCount <= 0) {
      System.err.print("No Block Count");
      SmartDashboard.putNumber("Cube Count", blockCount);
      return;
    } else {
      SmartDashboard.putNumber("Cube Count", blockCount);
    }
    ArrayList<Block> blocks = pixy2ccc.getBlockCache();
    Block largestBlock = null;
    if (blocks == null) {
      System.err.println("No Blocks");
      return;
    }
    for (Block block : blocks) {
      if (block.getSignature() == blockSignatureTwo) {
        if (largestBlock == null) {
          largestBlock = block;
        } else if (block.getWidth() > largestBlock.getWidth()) {
          largestBlock = block;
        }
      }
    }
    if (largestBlock != null) {
      cubeX = largestBlock.getX();
      cubeY = largestBlock.getY();
      cubeHeight = largestBlock.getHeight();
      cubeWidth = largestBlock.getWidth();
    }
  }
}