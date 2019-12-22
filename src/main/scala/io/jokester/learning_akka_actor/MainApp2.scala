package io.jokester.learning_akka_actor

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import example.myapp.helloworld.grpc.{Book, BookService, BookServiceHandler, GetBookRequest, QueryBooksRequest}

import scala.concurrent.Future

class MainApp2(implicit actorSystem: ActorSystem, m: Materializer) {
  private lazy val bookService: BookService = new BookService {
    override def getBook(in: GetBookRequest): Future[Book] =
      Future.failed(new NotImplementedError("todo"))
    //      Future.successful(Book(-1L, "title", "author"))

    override def queryBooks(in: QueryBooksRequest): Source[Book, NotUsed] =
      Source(Seq(Book(-2L, "title2", "author2"), Book(-3L, "title3", "author3")))
  }


  val bookServiceHandler: PartialFunction[HttpRequest, Future[HttpResponse]] = BookServiceHandler.partial(bookService)



}
