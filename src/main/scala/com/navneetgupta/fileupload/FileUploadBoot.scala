package com.navneetgupta.fileupload

import com.navneetgupta.fileupload.common.Bootstrap
import akka.actor.ActorSystem
import com.navneetgupta.fileupload.routes.FileUploadRoutes
import com.navneetgupta.fileupload.manager.FileUploadManager

class FileUploadBoot extends Bootstrap {
  override def bootstrap(system: ActorSystem) = {
    import system.dispatcher
    val fileUploadManager = system.actorOf(FileUploadManager.props, FileUploadManager.Name)
    List(new FileUploadRoutes(fileUploadManager))
  }
}
