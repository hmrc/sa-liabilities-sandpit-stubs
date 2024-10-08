/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.saliabilitiessandpitstubs.generator

import play.api.mvc.{AnyContent, Request}
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.GenerationStrategy.{Faker, Randomize, loadStrategy}
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail

trait BalanceDetailGeneratorResolver(using faker: BalanceDetailFaker, randomize: BalanceDetailRandomize):
  self: BalanceDetailGeneratorBackendHeaderCarrierProvider =>

  private val strategies: Map[GenerationStrategy, BalanceDetailGenerator] = Map(
    Faker     -> faker,
    Randomize -> randomize
  )

  def generate(implicit request: Request[AnyContent]): BalanceDetail =
    request.generationStrategy
      .flatMap(strategies.get)
      .getOrElse(randomize)
      .generate
