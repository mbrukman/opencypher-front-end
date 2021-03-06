/*
 * Copyright © 2002-2019 Neo4j Sweden AB (http://neo4j.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencypher.v9_0.expressions.functions

import org.opencypher.v9_0.expressions.{FunctionTypeSignature, TypeSignatures}
import org.opencypher.v9_0.util.symbols._

case object Split extends Function with TypeSignatures {
  def name = "split"

  override val signatures = Vector(
    FunctionTypeSignature(functionName = name, names = Vector("original", "splitDelimiter"), argumentTypes = Vector(CTString, CTString), outputType = CTList(CTString),
      description = "Returns a list of strings resulting from the splitting of the original string around matches of the given delimiter."),
    FunctionTypeSignature(functionName = name, names = Vector("original", "splitDelimiters"), argumentTypes = Vector(CTString, CTList(CTString)), outputType = CTList(CTString),
      description = "Returns a list of strings resulting from the splitting of the original string around matches of any of the given delimiters.")
  )
}
