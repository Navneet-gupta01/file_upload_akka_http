package com.navneetgupta.fileupload.command

import akka.http.scaladsl.model.Multipart

case class UploadFile(filePath: String, fileData: Multipart.FormData)
case class FileDetails(baseUrl: String, fileName: String, fileSize: Int)
