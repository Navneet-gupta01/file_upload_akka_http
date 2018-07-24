package com.navneetgupta.fileupload.routes

import akka.actor.ActorRef
import scala.concurrent.ExecutionContext
import com.navneetgupta.fileupload.common.BaseRouteDefination
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.Multipart
import com.navneetgupta.fileupload.common.ApiResponse
import com.navneetgupta.fileupload.command.FileDetails
import com.navneetgupta.fileupload.command.UploadFile
import java.io.FileOutputStream
import akka.util.ByteString
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes
import java.util.Optional
import com.navneetgupta.fileupload.protocols.FileUploadJsonProtocol
import com.typesafe.config.ConfigFactory

class FileUploadRoutes(fileUploadActor: ActorRef)(implicit val ec: ExecutionContext) extends BaseRouteDefination with FileUploadJsonProtocol {

  import akka.pattern._
  import akka.http.scaladsl.server.Directives._

  override def routes(implicit system: ActorSystem, ec: ExecutionContext, mater: Materializer): Route = {
    pathPrefix("fileupload") {
      pathEndOrSingleSlash {
        post {
          entity(as[Multipart.FormData]) { fileData =>
            val baseFilePath = ConfigFactory.load().getString("file-upload.base-location")
            serviceAndComplete[FileDetails](UploadFile(baseFilePath, fileData), fileUploadActor)
          }
        }
      }
    }
  }
}
