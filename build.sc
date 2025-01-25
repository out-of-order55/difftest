/***************************************************************************************
* Copyright (c) 2020-2024 Institute of Computing Technology, Chinese Academy of Sciences
*
* DiffTest is licensed under Mulan PSL v2.
* You can use this software according to the terms and conditions of the Mulan PSL v2.
* You may obtain a copy of Mulan PSL v2 at:
*          http://license.coscl.org.cn/MulanPSL2
*
* THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
* MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
*
* See the Mulan PSL v2 for more details.
***************************************************************************************/

import mill._
import mill.api.PathRef
import scalalib._
import publish._
import $file.common


object design extends Cross[DiffTestModule](ivys.chiselCrossVersions.keys.toSeq)

trait DiffTestModule extends CommonDiffTest {

  override def millSourcePath = os.Path(sys.env("MILL_WORKSPACE_ROOT"))

  override def scalacOptions = super.scalacOptions() ++
    Seq("-Xfatal-warnings", "-deprecation:false", "-unchecked", "-Xlint")

}

object difftest extends Cross[Difftest](ivys.chiselCrossVersions.keys.toSeq)
trait Difftest extends CommonDiffTest { outer =>

  override def millSourcePath = os.Path(sys.env("MILL_WORKSPACE_ROOT"))

  object test extends SbtTests with TestModule.ScalaTest {
    override def millSourcePath = outer.millSourcePath
    override def sources = T.sources {
      super.sources() ++ Seq(PathRef(millSourcePath / "src" / "generator" / s"$crossValue"))
    }
  }

}
