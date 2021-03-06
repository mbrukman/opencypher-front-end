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
package org.opencypher.v9_0.ast.semantics

import org.opencypher.v9_0.expressions.{DummyExpression, Expression}
import org.opencypher.v9_0.util.symbols._
import org.scalatest.Assertion

abstract class InfixExpressionTestBase(ctr: (Expression, Expression) => Expression) extends SemanticFunSuite {

  protected def testValidTypes(lhsTypes: TypeSpec, rhsTypes: TypeSpec, useCypher9ComparisonSemantics: Boolean = false)(expected: TypeSpec): Assertion = {
    val (result, expression) = evaluateWithTypes(lhsTypes, rhsTypes, useCypher9ComparisonSemantics)
    result.errors shouldBe empty
    types(expression)(result.state) should equal(expected)
  }

  protected def testInvalidApplication(lhsTypes: TypeSpec, rhsTypes: TypeSpec, useCypher9ComparisonSemantics: Boolean = false)(message: String): Assertion = {
    val (result, _) = evaluateWithTypes(lhsTypes, rhsTypes, useCypher9ComparisonSemantics)
    result.errors should not be empty
    result.errors.head.msg should equal(message)
  }

  protected def evaluateWithTypes(lhsTypes: TypeSpec, rhsTypes: TypeSpec, useCypher9ComparisonSemantics: Boolean): (SemanticCheckResult, Expression) = {
    val lhs = DummyExpression(lhsTypes)
    val rhs = DummyExpression(rhsTypes)

    val expression = ctr(lhs, rhs)

    val initialState = if (useCypher9ComparisonSemantics)
      SemanticState.clean.withFeature(SemanticFeature.Cypher9Comparability)
    else
      SemanticState.clean

    val state = SemanticExpressionCheck.simple(Seq(lhs, rhs))(initialState).state
    (SemanticExpressionCheck.simple(expression)(state), expression)
  }
}
