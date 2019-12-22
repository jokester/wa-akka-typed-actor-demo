import java.io.File
import java.nio.file.{Files, Path, StandardCopyOption}

object Utils {
  def copyDir(srcDir: File, destDir: File): Seq[File] = {
    // println("srcDir", srcDir)
    // println("destDir", destDir)
    Files
      .walk(srcDir.toPath)
      .toArray
      .map(_.asInstanceOf[Path].toFile)
      .filter(_.isFile)
      .map(srcFile => {
        val destPath = destDir.toPath.resolve(srcDir.toPath.relativize(srcFile.toPath))
        Files.createDirectories(destPath.getParent)
        Files.copy(srcFile.toPath, destPath, StandardCopyOption.REPLACE_EXISTING)
        destPath.toFile
      })
      .toSeq
  }
}
