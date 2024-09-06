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

package uk.gov.hmrc.saliabilitiessandpitstubs

import com.github.javafaker.Faker
import uk.gov.hmrc.saliabilitiessandpitstubs.config.AppConfig
import uk.gov.hmrc.saliabilitiessandpitstubs.time.{FakerExtensions, LocalDateExtensions}

import javax.inject.Inject
import scala.util.Random

package object generator:
  case class DefaultBalanceDetailGenerator @Inject() (random: Random)
      extends BalanceDetailRandomize(using LocalDateExtensions)(random)

  case class DefaultBalanceDetailFaker @Inject() ()(using faker: Faker) extends BalanceDetailFaker, FakerExtensions

  case class DefaultBalanceDetailGeneratorResolver @Inject() ()(using
    faker: BalanceDetailFaker,
    randomize: BalanceDetailRandomize,
    config: AppConfig
  ) extends BalanceDetailGeneratorResolver
      with BalanceDetailGeneratorBackendHeaderCarrierProvider(using config.defaultGenerationHeader)

  case class DefaultBalanceDetailInitialGeneratorResolver @Inject() ()(using
    faker: BalanceDetailFaker,
    randomize: BalanceDetailRandomize,
    config: AppConfig
  ) extends BalanceDetailInitialGeneratorResolver
