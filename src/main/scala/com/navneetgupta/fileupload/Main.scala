package com.navneetgupta.fileupload

import com.navneetgupta.fileupload.common.Server

object Main extends App {
  new Server(new FileUploadBoot(), "file-upload")
}
