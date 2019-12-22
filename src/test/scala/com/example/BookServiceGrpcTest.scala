package com.example

import akka.NotUsed
import akka.actor.ActorSystem
import akka.grpc.GrpcServiceException
import akka.http.scaladsl.model.HttpEntity.{ChunkStreamPart, Chunked}
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse, Uri}
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import example.myapp.helloworld.grpc.{Book, BookService, BookServiceHandler, GetBookRequest, QueryBooksRequest}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Future

class BookServiceGrpcTest extends WordSpec with Matchers with ScalaFutures with BeforeAndAfterAll {

  implicit val m = Materializer
  implicit val actorSystem = ActorSystem("test")

  "bookServiceHandler" should {

    "write in fail" in {
      val service = new BookService {
        override def getBook(in: GetBookRequest): Future[Book] = Future.failed(new GrpcServiceException(io.grpc.Status.UNIMPLEMENTED))
        override def queryBooks(in: QueryBooksRequest): Source[Book, NotUsed] = ???
      }

      val handler = BookServiceHandler(service)

      val res = handler(HttpRequest(method = HttpMethods.POST, uri = Uri("/examplecom.library.BookService/GetBook"))).futureValue

      val chunks = getChunks(res)

      chunks shouldEqual Seq()
    }

  }

  def getChunks(resp: HttpResponse): Seq[ChunkStreamPart] =
    (resp.entity match {
      case Chunked(contentType, chunks) =>
        chunks.runFold(Seq.empty[ChunkStreamPart]) { case (seq, chunk) => seq :+ chunk }
      case _ => Future.successful(Seq.empty[ChunkStreamPart])
    }).futureValue

  override def afterAll(): Unit = {
    actorSystem.terminate()
  }

}
