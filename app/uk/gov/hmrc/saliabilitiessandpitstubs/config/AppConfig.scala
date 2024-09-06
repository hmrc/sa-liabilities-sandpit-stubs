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

package uk.gov.hmrc.saliabilitiessandpitstubs.config

import play.api.Configuration
import uk.gov.hmrc.saliabilitiessandpitstubs.generator.GenerationStrategy

import javax.inject.Inject

class AppConfig @Inject() (config: Configuration):
  val appName: String                           = config.get[String]("appName")
  val bearerAuthorisationEnabled: Boolean       = config.get[Boolean]("feature-toggles.new-auth-check-enabled")
  val randomSeed: Option[Int]                   = config.get[Option[Int]]("generator.random.seed")
  val defaultGenerator: GenerationStrategy      = config.get[GenerationStrategy]("generator.default")
  val defaultGenerationHeader: String           = config.get[String]("generator.request.header")
  val balanceDetailValidatorFields: Seq[String] = config.get[Seq[String]]("validation.balance.fields")
  val balanceDetailValidationEnable: Boolean    = config.get[Boolean]("validation.balance.enable")
