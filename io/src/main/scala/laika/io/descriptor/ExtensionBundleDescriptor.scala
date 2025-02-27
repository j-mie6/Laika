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

package laika.io.descriptor

import laika.bundle.ExtensionBundle

/** Provides a short description of an extension bundle for tooling or logging.
  *
  * @author Jens Halm
  */
class ExtensionBundleDescriptor(bundle: ExtensionBundle) {

  def formatted: String = {
    s"${bundle.description} (supplied by ${bundle.origin.toString.toLowerCase})"
  }

}
