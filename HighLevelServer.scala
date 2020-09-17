package part3.highlevelserver

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import part2.lowlevelserver.HttpsContext
import part2.lowlevelserver.LowLevelHttps._

object HighLevelServer extends App {

  implicit val system=ActorSystem("HighLevel")
  implicit val materializer=ActorMaterializer
  import system.dispatcher

  //directive
  import akka.http.scaladsl.server.Directives._

  val simpleRoute:Route= {
    path("home") {
      complete(StatusCodes.OK)
    }
  }
val pathGetRoute:Route={
  path("endpoint"){
    get{
      complete(StatusCodes.OK)
    }
  }
}
  //chaining directive
  val myEndPointRoute:Route= {
    path("myEndPoint"){
      get{
        complete(StatusCodes.OK)
      }~
        post{
          complete(StatusCodes.Forbidden)
        }
    }~
      path("myStartingPoint"){
        complete(
          HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            """
              |<html>
              |<body>
              |welcome alla hubiby!!!
              |</body>
              |</html>
              |""".stripMargin
          )
        )
      }
  }
    Http().newServerAt("localhost",8080).enableHttps(HttpsContext.httpsConnectionContext).bind(myEndPointRoute)


}
