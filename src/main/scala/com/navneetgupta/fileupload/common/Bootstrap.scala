package com.navneetgupta.fileupload.common

import akka.actor.ActorSystem

trait Bootstrap {
  def bootstrap(system: ActorSystem): List[BaseRouteDefination]
}
