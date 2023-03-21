package frc.robot.subsystems.auxiliary;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.pseudoresonance.pixy2api.*;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.*;
import java.util.ArrayList;

public class PixySystem {

  private static final int blockSignatureOne = 1;
  private static final int blockSignatureTwo = 2;

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

  public void GetCones() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG1, 25);
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
      SmartDashboard.putNumber("Cone X", largestBlock.getX());
      SmartDashboard.putNumber("Cone Y", largestBlock.getY());
    }
  }

  public  static int GetCubeCount() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG2, 1);
    return blockCount;
  }

  public static int GetConeCount() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG1, 1);
    return blockCount;
  }

  public static int GetObject() {
    if(GetConeCount()>GetConeCount()){
      return 1; //Cone
    }else if(GetConeCount()<GetCubeCount()){
      return 2; //Cube
    }else{
      return -1; // Null 
    }
  }

  public void GetCubes() {
    int blockCount = pixy2ccc.getBlocks(true, Pixy2CCC.CCC_SIG2, 25);
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
      SmartDashboard.putNumber("Cube X", largestBlock.getX());
      SmartDashboard.putNumber("Cube Y", largestBlock.getY());
    }
  }
}
