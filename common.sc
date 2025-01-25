import mill._
import mill.api.PathRef
import scalalib._
import publish._
object ivys {
  val scala = "2.13.14"
  val chiselCrossVersions = Map(
    "chisel3" -> (ivy"edu.berkeley.cs::chisel3:3.6.1", ivy"edu.berkeley.cs:::chisel3-plugin:3.6.1"),
    "chisel" -> (ivy"org.chipsalliance::chisel:6.6.0", ivy"org.chipsalliance:::chisel-plugin:6.6.0"),
  )
}

trait CommonDiffTest extends ScalaModule with SbtModule with Cross.Module[String] {

  override def scalaVersion = ivys.scala

  override def scalacPluginIvyDeps = Agg(ivys.chiselCrossVersions(crossValue)._2)

  override def scalacOptions = Seq("-Ymacro-annotations") ++
    Seq("-feature", "-language:reflectiveCalls")

  override def ivyDeps = Agg(ivys.chiselCrossVersions(crossValue)._1)
}