package com.navneetgupta.fileupload.manager

import akka.actor.Props
import akka.actor.ActorLogging
import akka.actor.Actor
import com.navneetgupta.fileupload.command.UploadFile
import java.io.FileOutputStream
import akka.util.ByteString
import java.util.UUID
import akka.stream.ActorMaterializer
import com.navneetgupta.fileupload.command.FileDetails
import com.navneetgupta.fileupload.common.BaseActor

object FileUploadManager {
  def props = Props[FileUploadManager]
  val Name = "file-upload-manager"
}
class FileUploadManager extends BaseActor {
  implicit val mater = ActorMaterializer()
  import context.dispatcher

  override def receive = {
    case UploadFile(basePath, fileData) =>
      val resp = fileData.parts.mapAsync(1) { bodyPart ⇒
        val randomeName = UUID.randomUUID().toString()
        val originalFileName = bodyPart.getFilename().isPresent() match {
          case true => basePath + randomeName + bodyPart.getFilename().get()
          case _    => basePath + randomeName
        }
        val fileOutput = new FileOutputStream(originalFileName)
        def writeFileOnLocal(array: Array[Byte], byteString: ByteString): Array[Byte] = {
          val byteArray: Array[Byte] = byteString.toArray
          fileOutput.write(byteArray)
          array ++ byteArray
        }
        bodyPart.entity.dataBytes.runFold(Array[Byte]())(writeFileOnLocal).map {
          case bytesArray => (bytesArray, originalFileName)
        }
      }.runFold(FileDetails(basePath, "", 0))((fd, tuple) => {
        FileDetails(fd.baseUrl, tuple._2, fd.fileSize + tuple._1.length)
      })
      pipeResponse(resp)
  }
}
