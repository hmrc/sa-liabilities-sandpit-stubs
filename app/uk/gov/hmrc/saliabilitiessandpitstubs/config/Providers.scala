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

//noinspection ScalaFileName
package uk.gov.hmrc.saliabilitiessandpitstubs.config

import uk.gov.hmrc.saliabilitiessandpitstubs.controllers.action.{AuthorizationActionFilter, DefaultOpenAuthAction, DefaultTokenBasedAction}
import uk.gov.hmrc.saliabilitiessandpitstubs.json.JsValidator
import uk.gov.hmrc.saliabilitiessandpitstubs.models.BalanceDetail
import uk.gov.hmrc.saliabilitiessandpitstubs.time.{LiveSystemLocalDate, StubbedSystemLocalDate, SystemLocalDate}
import uk.gov.hmrc.saliabilitiessandpitstubs.validator.{BalanceDetailValidator, ModelBasedBalanceDetailValidator}

import javax.inject.{Inject, Provider}
import scala.concurrent.ExecutionContext
import scala.util.Random

class AuthActionProvider @Inject() (config: AppConfig, executionContext: ExecutionContext)
    extends Provider[AuthorizationActionFilter]:
  val get: AuthorizationActionFilter =
    (if config.bearerAuthorisationEnabled then classOf[DefaultTokenBasedAction] else classOf[DefaultOpenAuthAction])
      .getConstructor(classOf[ExecutionContext])
      .newInstance(executionContext)

class RandomProvider @Inject() (config: AppConfig) extends Provider[Random]:
  val get: Random = (config.randomSeed fold Random)(Random(_))

class LocalDateProvider @Inject() (config: AppConfig) extends Provider[SystemLocalDate]:
  override def get(): SystemLocalDate = if config.timeStrategy.isFake then StubbedSystemLocalDate(config.currentDate)
  else LiveSystemLocalDate

class BalanceDetailValidatorRequestProvider @Inject() (config: AppConfig) extends Provider[JsValidator[BalanceDetail]]:
  val get: JsValidator[BalanceDetail] =
    if config.balanceDetailValidationEnable then BalanceDetailValidator(config.balanceDetailValidatorFields.toSet)
    else ModelBasedBalanceDetailValidator
