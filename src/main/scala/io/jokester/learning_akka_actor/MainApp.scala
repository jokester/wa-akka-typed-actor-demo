package io.jokester.learning_akka_actor

import akka.NotUsed
import akka.actor.ActorSystem
import akka.grpc.GrpcServiceException
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{HttpApp, Route}
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import com.typesafe.scalalogging.LazyLogging
import example.myapp.helloworld.grpc._

import scala.concurrent.{ExecutionContext, Future}

class MainApp(implicit val sys: ActorSystem)
    extends HttpApp with LazyLogging {

  private val bookService: BookService = new BookService {
    override def getBook(in: GetBookRequest): Future[Book] = Future.failed(new GrpcServiceException(io.grpc.Status.UNIMPLEMENTED))

    override def queryBooks(in: QueryBooksRequest): Source[Book, NotUsed] =
      Source(Seq(Book(-2L, "title2", "author2"), Book(-3L, "title3", "author3")))
  }

  private implicit lazy val materializer: Materializer = Materializer.createMaterializer(sys)
  private implicit lazy val ec: ExecutionContext = sys.dispatcher

  lazy val bookServiceHandler: HttpRequest => Future[HttpResponse] = BookServiceHandler(bookService)

  def bookServiceRoute: Route = {
    extractRequest { req =>
      logger.debug("got req: {}", req)
      onSuccess(bookServiceHandler(req)) { chunkedRes =>
        complete(chunkedRes)
      }
    }
  }

  override def routes: Route = {
    bookServiceRoute
  }
}
