/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package laika.render.epub

import laika.ast.Path.Root
import laika.ast._
import laika.io.model.{RenderedDocument, RenderedTree}

object NavigationBuilder {

  /** Provides the full path to the document relative to the EPUB container root
    * from the specified virtual path of the Laika document tree.
    */
  def fullPath (path: Path, forceXhtml: Boolean = false): String = {
    val finalPath = if (forceXhtml || path.suffix.contains("html")) path.withSuffix("epub.xhtml") else path
    val parent = finalPath.parent match {
      case Root => ""
      case _ => finalPath.parent.toString
    }
    s"content$parent/${finalPath.name}"
  }

  def forTree (tree: RenderedTree, depth: Option[Int]): Seq[NavigationItem] = {
    
    def adjustPath (item: NavigationItem): NavigationItem = item match {
      case h: NavigationHeader => h.copy(content = h.content.map(adjustPath))
      case l: NavigationLink => l.target match {
        case _: ExternalTarget => l
        case InternalTarget(_, rel, _) => 
          val adjustedPath = (Root / "content" / rel).withSuffix("epub.xhtml")
          l.copy(
            content = l.content.map(adjustPath),
            target = InternalTarget(adjustedPath, adjustedPath.relativeTo(Root))
          )
      }
    }
    
    tree.asNavigationItem(NavigationBuilderContext(maxLevels = depth.getOrElse(Int.MaxValue), currentLevel = 0))
      .content.map(adjustPath)
  }

}
