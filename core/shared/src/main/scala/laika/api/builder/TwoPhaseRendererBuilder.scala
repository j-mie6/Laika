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

package laika.api.builder

import laika.factory.TwoPhaseRenderFormat

/** Builder API for Renderer instances.
  *
  * Allows to add ExtensionBundles, to override the renderer for specific elements
  * and other options.
  *
  * @tparam FMT the formatter API to use which varies depending on the renderer
  * @tparam PP the type of the post processor
  *
  * @author Jens Halm
  */
class TwoPhaseRendererBuilder[FMT, PP] private[laika] (
    val twoPhaseFormat: TwoPhaseRenderFormat[FMT, PP],
    val config: OperationConfig
) extends RendererBuilderOps[FMT] {

  protected[this] val renderFormat = twoPhaseFormat.interimFormat

  type ThisType = TwoPhaseRendererBuilder[FMT, PP]

  def withConfig(newConfig: OperationConfig): ThisType =
    new TwoPhaseRendererBuilder[FMT, PP](twoPhaseFormat, newConfig)

}
