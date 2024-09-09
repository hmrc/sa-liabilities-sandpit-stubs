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

import com.typesafe.config.Config
import play.api.{ConfigLoader, Configuration}

import scala.language.implicitConversions

case class NetworkConditionConfig(
  failureProbability: Double,
  randomDelayProbability: Double,
  throttlingProbability: Double,
  timeoutProbability: Double,
  standardDeviation: Double,
  mean: Double
)

object NetworkConditionConfig:

  given ConfigLoader[NetworkConditionConfig] with
    def load(config: Config, prefix: String): NetworkConditionConfig =
      val serviceConfig                  = Configuration(config).get[Configuration](prefix)
      val failureProbability: Double     = serviceConfig.get[String]("failure").toDouble
      val randomDelayProbability: Double = serviceConfig.get[String]("randomDelay").toDouble
      val throttlingProbability: Double  = serviceConfig.get[String]("throttling").toDouble
      val timeoutProbability: Double     = serviceConfig.get[String]("timeout").toDouble
      val standardDeviation: Double      = serviceConfig.get[String]("standardDeviation").toDouble
      val mean: Double                   = serviceConfig.get[String]("mean").toDouble

      NetworkConditionConfig(
        failureProbability,
        randomDelayProbability,
        throttlingProbability,
        timeoutProbability,
        standardDeviation,
        mean
      )
